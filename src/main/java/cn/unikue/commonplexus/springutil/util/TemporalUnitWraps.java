/*
 * Copyright (c) 2016 Unikue Ltd. All rights reserved.
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

package cn.unikue.commonplexus.springutil.util;

import java.util.Locale;
import java.util.ResourceBundle;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.i18n.LocaleContextHolder;


/**
 * Utilities for temporal unit conversion and formatting
 *
 * @author David Hsing
 */
@SuppressWarnings("unused")
public abstract class TemporalUnitWraps {
    private static final String BUNDLE_NAME = "lang.stockpile.TemporalUnit";
    private static final long SECONDS_PER_MINUTE = 60L;
    private static final long SECONDS_PER_HOUR = 3600L;
    private static final long SECONDS_PER_DAY = 86400L;
    private static final long SECONDS_PER_MONTH = 2592000L;    // 30 days
    private static final long SECONDS_PER_YEAR = 31536000L;    // 365 days

    /**
     * Format seconds to human-readable duration string
     * e.g., "1 Year 2 Months 3 Days 4 Hours 5 Minutes 6 Seconds"
     *
     * @param totalSeconds total seconds
     * @return human-readable duration string
     */
    public static String toHumanReadable(long totalSeconds) {
        return toHumanReadable(totalSeconds, LocaleContextHolder.getLocale());
    }

    /**
     * Format seconds to human-readable duration string with specified locale
     * e.g., "1 Year 2 Months 3 Days 4 Hours 5 Minutes 6 Seconds"
     *
     * @param totalSeconds total seconds
     * @param locale the locale for formatting
     * @return human-readable duration string
     */
    public static String toHumanReadable(long totalSeconds, Locale locale) {
        if (totalSeconds <= 0) {
            return getLocaleMessage("forever", locale);
        }
        StringBuilder result = new StringBuilder();
        long years = totalSeconds / SECONDS_PER_YEAR;
        long remaining = totalSeconds % SECONDS_PER_YEAR;
        long months = remaining / SECONDS_PER_MONTH;
        remaining = remaining % SECONDS_PER_MONTH;
        long days = remaining / SECONDS_PER_DAY;
        remaining = remaining % SECONDS_PER_DAY;
        long hours = remaining / SECONDS_PER_HOUR;
        remaining = remaining % SECONDS_PER_HOUR;
        long minutes = remaining / SECONDS_PER_MINUTE;
        long seconds = remaining % SECONDS_PER_MINUTE;
        appendLocaleUnit(result, years, "year", locale);
        appendLocaleUnit(result, months, "month", locale);
        appendLocaleUnit(result, days, "day", locale);
        appendLocaleUnit(result, hours, "hour", locale);
        appendLocaleUnit(result, minutes, "minute", locale);
        appendLocaleUnit(result, seconds, "second", locale);
        return result.toString().trim();
    }

    private static void appendLocaleUnit(StringBuilder builder, long value, String unitKey, Locale locale) {
        if (value > 0) {
            String unitName = getLocaleMessage(value == 1 ? unitKey : unitKey + "s", locale);
            builder.append(value).append(" ").append(unitName).append(" ");
        }
    }

    private static String getLocaleMessage(String key, Locale locale) {
        if (StringUtils.isBlank(key) || locale == null) {
            return key;
        }
        try {
            ResourceBundle bundle = ResourceBundle.getBundle(BUNDLE_NAME, locale);
            String messageKey = "TemporalUnit." + key;    // $NON-NLS-1$
            String message = bundle.getString(messageKey);
            return StringUtils.isNotBlank(message) ? message : key;
        } catch (Exception ignored) {
            return key;
        }
    }
}
