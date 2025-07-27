package edu.currency.exchange.homasapienss.utils;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.*;

public final class ConnectionManager {
    private static String URL_KEY = "db.url";
    private static String USERNAME_KEY = "db.username";
    private static String PASSWORD_KEY = "db.password";

    private static final HikariDataSource dataSource;

    static {
        try {
            Class.forName("org.postgresql.Driver");
            HikariConfig config = new HikariConfig();
            config.setJdbcUrl(PropertiesUtil.getProperty(URL_KEY));
            config.setUsername(PropertiesUtil.getProperty(USERNAME_KEY));
            config.setPassword(PropertiesUtil.getProperty(PASSWORD_KEY));

            // Опциональные настройки
            config.setMaximumPoolSize(10);
            config.setMinimumIdle(2);
            config.setIdleTimeout(30000); // 30 сек
            config.setConnectionTimeout(10000); // 10 сек
            config.setMaxLifetime(1800000); // 30 минут

            dataSource = new HikariDataSource(config);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Failed initialize DATA BASE");
        }
    }

    private ConnectionManager() {

    }

    public static Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }
}
