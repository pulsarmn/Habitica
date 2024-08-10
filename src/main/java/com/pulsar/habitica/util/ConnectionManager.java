package com.pulsar.habitica.util;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class ConnectionManager {

    private static final String URL_KEY = "db.url";
    private static final String USER_KEY = "db.user";
    private static final String PASSWORD_KEY = "db.password";
    private static final String DRIVER_PATH_KEY = "driver.path";
    private static final String DRIVER_ERROR_KEY = "error.driver.loading";
    private static final String CONNECTION_ERROR_KEY = "error.connection.loading";
    
    private ConnectionManager() {}

    static {
        loadDriver();
    }

    private static void loadDriver() {
        try {
            Class.forName(PropertiesUtil.get(DRIVER_PATH_KEY));
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(PropertiesUtil.get(DRIVER_ERROR_KEY), e);
        }
    }

    public static Connection openConnection() {
        try {
            return DriverManager.getConnection(PropertiesUtil.get(URL_KEY),
                    PropertiesUtil.get(USER_KEY),
                    PropertiesUtil.get(PASSWORD_KEY));
        } catch (SQLException e) {
            throw new RuntimeException(PropertiesUtil.get(CONNECTION_ERROR_KEY), e);
        }
    }
}