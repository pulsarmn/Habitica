package com.pulsar.habitica.util;

import java.util.Locale;

public final class LocaleUtil {

    private static final String LOCALE_PATTERN = "\\w\\w_\\w\\w";

    private LocaleUtil() {}

    public static boolean isLocale(String userLocale) {
        if (userLocale.matches(LOCALE_PATTERN)) {
            return Locale.availableLocales()
                    .anyMatch(locale -> locale.toString().equals(userLocale));
        }
        return false;
    }

    public static String getLanguage(String locale) {
        if (isLocale(locale)) {
            return locale.split("_")[0];
        }
        return Locale.getDefault().getLanguage();
    }

    public static String getCountry(String locale) {
        if (isLocale(locale)) {
            return locale.split("_")[1];
        }
        return Locale.getDefault().getCountry();
    }
}
