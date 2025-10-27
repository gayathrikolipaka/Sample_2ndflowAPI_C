
package com.api.utils;

import java.io.InputStream;
import java.util.Properties;

public class ConfigReader {
    private static Properties prop;

    static {
        try {
            InputStream input = ConfigReader.class.getResourceAsStream("/config/config.properties");
            prop = new Properties();
            prop.load(input);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String getProperty(String key) {
        return prop.getProperty(key);
    }
}
