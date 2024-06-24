/*
 * Copyright (c) 2003-2004 The Apache Software Foundation
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.yookue.commonplexus.springutil.cglib;


import java.beans.PropertyDescriptor;
import java.lang.reflect.Modifier;
import java.security.ProtectionDomain;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import org.springframework.asm.ClassVisitor;
import org.springframework.asm.Type;
import org.springframework.cglib.core.AbstractClassGenerator;
import org.springframework.cglib.core.ClassEmitter;
import org.springframework.cglib.core.CodeEmitter;
import org.springframework.cglib.core.Constants;
import org.springframework.cglib.core.EmitUtils;
import org.springframework.cglib.core.KeyFactory;
import org.springframework.cglib.core.Local;
import org.springframework.cglib.core.MethodInfo;
import org.springframework.cglib.core.ReflectUtils;
import org.springframework.cglib.core.Signature;
import org.springframework.cglib.core.TypeUtils;
import org.springframework.util.StringUtils;
import lombok.Setter;


/**
 * Enhanced of {@link org.springframework.cglib.beans.BeanCopier}
 *
 * @author Chris Nokleberg
 * @author David Hsing
 * @reference "https://juejin.cn/post/7018550830466859015"
 * @reference "https://blog.csdn.net/qq1805696978/article/details/123880615"
 * @reference "https://github.com/spring-projects/spring-framework/tree/main/spring-core/src/main/java/org/springframework/cglib/beans"
 * @see org.springframework.cglib.beans.BeanCopier
 */
@SuppressWarnings({"unused", "JavadocDeclaration", "JavadocLinkAsPlainText"})
public abstract class EnhancedBeanCopier {
    private static final BeanCopierKey KEY_FACTORY = (BeanCopierKey) KeyFactory.create(BeanCopierKey.class);
    private static final Type CONVERTER = TypeUtils.parseType(BeanCopierConverter.class.getCanonicalName());
    private static final Type BEAN_COPIER = TypeUtils.parseType(EnhancedBeanCopier.class.getCanonicalName());
    private static final Signature COPY = new Signature("copy", Type.VOID_TYPE, new Type[]{Constants.TYPE_OBJECT, Constants.TYPE_OBJECT, CONVERTER});    // $NON-NLS-1$
    private static final Signature CONVERT = TypeUtils.parseSignature("Object convert(Object, Class, Object, String, Object)");    // $NON-NLS-1$

    // Added by David Hsing on 2022-07-08
    private static final Map<String, EnhancedBeanCopier> CACHE_COPIER = new ConcurrentHashMap<>();

    public static EnhancedBeanCopier create(@Nonnull Class<?> source, @Nonnull Class<?> target, boolean useConverter) {
        // Modified on 2022-07-08
        String index = StringUtils.arrayToCommaDelimitedString(new Object[]{source.getName(), target.getName(), useConverter});
        return CACHE_COPIER.computeIfAbsent(index, key -> {
            Generator gen = new Generator();
            gen.setSource(source);
            gen.setTarget(target);
            gen.setUseConverter(useConverter);
            return gen.create();
        });
    }

    public abstract void copy(@Nonnull Object from, @Nonnull Object to, @Nullable BeanCopierConverter converter);


    interface BeanCopierKey {
        Object newInstance(@Nonnull String source, @Nonnull String target, boolean useConverter);
    }


    public static class Generator extends AbstractClassGenerator<EnhancedBeanCopier> {
        private static final Source SOURCE = new Source(EnhancedBeanCopier.class.getName());
        private Class<?> source;
        private Class<?> target;

        @Setter
        private boolean useConverter;

        public Generator() {
            super(SOURCE);
        }

        private static boolean compatible(@Nonnull PropertyDescriptor getter, @Nonnull PropertyDescriptor setter) {
            // TODO: allow automatic widening conversions?
            return setter.getPropertyType().isAssignableFrom(getter.getPropertyType());
        }

        public void setSource(@Nonnull Class<?> source) {
            if (!Modifier.isPublic(source.getModifiers())) {
                setNamePrefix(source.getName());
            }
            this.source = source;
        }

        public void setTarget(@Nonnull Class<?> target) {
            if (!Modifier.isPublic(target.getModifiers())) {
                setNamePrefix(target.getName());
            }
            this.target = target;
            // SPRING PATCH BEGIN
            super.setContextClass(target);
            // SPRING PATCH END
        }

        protected ClassLoader getDefaultClassLoader() {
            return source.getClassLoader();
        }

        protected ProtectionDomain getProtectionDomain() {
            return ReflectUtils.getProtectionDomain(source);
        }

        public EnhancedBeanCopier create() {
            Object key = KEY_FACTORY.newInstance(source.getName(), target.getName(), useConverter);
            return (EnhancedBeanCopier) super.create(key);
        }

        public void generateClass(ClassVisitor visitor) {
            Type sourceType = Type.getType(source);
            Type targetType = Type.getType(target);
            ClassEmitter classEmitter = new ClassEmitter(visitor);
            classEmitter.begin_class(Constants.V1_8,
                Constants.ACC_PUBLIC,
                getClassName(),
                BEAN_COPIER,
                null,
                Constants.SOURCE_FILE);

            EmitUtils.null_constructor(classEmitter);
            CodeEmitter codeEmitter = classEmitter.begin_method(Constants.ACC_PUBLIC, COPY, null);
            PropertyDescriptor[] sourceGetters = ReflectUtils.getBeanGetters(source);
            PropertyDescriptor[] targetSetters = ReflectUtils.getBeanSetters(target);

            // Modified by David Hsing on 2022-07-08
            Map<String, PropertyDescriptor> sourceGetterNames = new HashMap<>(sourceGetters.length);
            for (PropertyDescriptor descriptor : sourceGetters) {
                sourceGetterNames.put(descriptor.getName(), descriptor);
            }

            // Added by David Hsing on 2022-07-08
            PropertyDescriptor[] targetGetters = ReflectUtils.getBeanGetters(target);
            Map<String, PropertyDescriptor> targetGetterNames = new HashMap<>(targetGetters.length);
            for (PropertyDescriptor descriptor : targetGetters) {
                targetGetterNames.put(descriptor.getName(), descriptor);
            }

            Local sourceLocal = codeEmitter.make_local();
            Local targetLocal = codeEmitter.make_local();
            if (useConverter) {
                codeEmitter.load_arg(0);
                codeEmitter.checkcast(sourceType);
                codeEmitter.store_local(sourceLocal);
                codeEmitter.load_arg(1);
                codeEmitter.checkcast(targetType);
                codeEmitter.store_local(targetLocal);
            } else {
                codeEmitter.load_arg(0);
                codeEmitter.checkcast(sourceType);
                codeEmitter.load_arg(1);
                codeEmitter.checkcast(targetType);
            }
            for (PropertyDescriptor targetSetter : targetSetters) {
                PropertyDescriptor sourceGetter = sourceGetterNames.get(targetSetter.getName());

                // Added by David Hsing on 2022-07-08
                PropertyDescriptor targetGetter = targetGetterNames.get(targetSetter.getName());

                if (sourceGetter != null) {
                    MethodInfo sourceRead = ReflectUtils.getMethodInfo(sourceGetter.getReadMethod());
                    MethodInfo targetWrite = ReflectUtils.getMethodInfo(targetSetter.getWriteMethod());

                    // Added by David Hsing on 2022-07-08
                    MethodInfo targetRead = ReflectUtils.getMethodInfo(targetGetter.getReadMethod());

                    if (useConverter) {
                        Type setterType = targetWrite.getSignature().getArgumentTypes()[0];
                        codeEmitter.load_local(targetLocal);
                        codeEmitter.load_arg(2);
                        codeEmitter.load_local(sourceLocal);
                        codeEmitter.invoke(sourceRead);
                        codeEmitter.box(sourceRead.getSignature().getReturnType());
                        EmitUtils.load_class(codeEmitter, setterType);
                        codeEmitter.push(targetWrite.getSignature().getName());

                        // Added by David Hsing on 2022-07-08
                        // Parameter - targetFiledName
                        codeEmitter.push(targetSetter.getName());
                        // Parameter - targetFiledValue
                        codeEmitter.load_local(targetLocal);
                        codeEmitter.invoke(targetRead);
                        codeEmitter.box(targetRead.getSignature().getReturnType());

                        codeEmitter.invoke_interface(CONVERTER, CONVERT);
                        codeEmitter.unbox_or_zero(setterType);
                        codeEmitter.invoke(targetWrite);
                    } else if (compatible(sourceGetter, targetSetter)) {
                        codeEmitter.dup2();
                        codeEmitter.invoke(sourceRead);
                        codeEmitter.invoke(targetWrite);
                    }
                }
            }
            codeEmitter.return_value();
            codeEmitter.end_method();
            classEmitter.end_class();
        }

        protected Object firstInstance(@Nonnull Class type) {
            return ReflectUtils.newInstance(type);
        }

        protected Object nextInstance(Object instance) {
            return instance;
        }
    }
}
