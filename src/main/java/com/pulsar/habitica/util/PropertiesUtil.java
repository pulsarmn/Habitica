package com.pulsar.habitica.util;

import java.io.IOException;
import java.util.Properties;

public final class PropertiesUtil {

    private static final Properties PROPERTIES = new Properties();

    private PropertiesUtil() {}

    static {
        load();
    }

    public static String get(String key) {
        return PROPERTIES.getProperty(key);
    }

    private static void load() {
        try {
            PROPERTIES.load(PropertiesUtil.class.getClassLoader().getResourceAsStream("application.properties"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
