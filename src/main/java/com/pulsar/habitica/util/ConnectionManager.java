package com.pulsar.habitica.util;


import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public final class ConnectionManager {

    private static final String URL_KEY = "db.url";
    private static final String USER_KEY = "db.user";
    private static final String PASSWORD_KEY = "db.password";
    private static final String DRIVER_PATH_KEY = "driver.path";
    private static final String DRIVER_ERROR_KEY = "error.driver.loading";
    private static final String CONNECTION_ERROR_KEY = "error.connection.loading";
    private static final String CLOSING_CONNECTION_ERROR_KEY = "error.connection.closing";
    private static final String TAKE_CONNECTION_ERROR_KEY = "error.connection.take";
    private static final String POOL_SIZE_KEY = "pool.size";
    private static BlockingQueue<Connection> pool;
    private static List<Connection> sourceConnections;
    private static final int DEFAULT_POOL_SIZE = 20;
    
    private ConnectionManager() {}

    static {
        loadDriver();
        loadPool();
        Runtime.getRuntime().addShutdownHook(new Thread(ConnectionManager::closePool));
    }

    private static void loadDriver() {
        try {
            Class.forName(PropertiesUtil.get(DRIVER_PATH_KEY));
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(PropertiesUtil.get(DRIVER_ERROR_KEY), e);
        }
    }

    private static void loadPool() {
        String poolSize = PropertiesUtil.get(POOL_SIZE_KEY);
        int size = (poolSize != null && poolSize.matches("\\d"))
                ? Integer.parseInt(poolSize)
                : DEFAULT_POOL_SIZE;
        pool = new ArrayBlockingQueue<>(size);
        sourceConnections = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            var connection = openConnection();
            var proxyConnection = (Connection) Proxy.newProxyInstance(ConnectionManager.class.getClassLoader(),
                    new Class[]{Connection.class},
                    (proxy, method, args) -> method.getName().equals("close")
                            ? pool.add((Connection) proxy)
                            : method.invoke(connection, args));
            pool.add(proxyConnection);
            sourceConnections.add(connection);
        }
    }

    private static Connection openConnection() {
        try {
            return DriverManager.getConnection(PropertiesUtil.get(URL_KEY),
                    PropertiesUtil.get(USER_KEY),
                    PropertiesUtil.get(PASSWORD_KEY));
        } catch (SQLException e) {
            throw new RuntimeException(PropertiesUtil.get(CONNECTION_ERROR_KEY), e);
        }
    }

    public static Connection get() {
        try {
            return pool.take();
        } catch (InterruptedException e) {
            throw new RuntimeException(TAKE_CONNECTION_ERROR_KEY, e);
        }
    }

    public static void closePool() {
        for (var connection : sourceConnections) {
            try {
                connection.close();
            } catch (SQLException e) {
                throw new RuntimeException(PropertiesUtil.get(CLOSING_CONNECTION_ERROR_KEY), e);
            }
        }
    }
}