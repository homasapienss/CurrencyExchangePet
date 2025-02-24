package edu.currency.exchange.homasapienss.utils;

import java.sql.*;

public final class ConnectionManager {
    private static String URL_KEY = "db.url";
    private static String USERNAME_KEY = "db.username";
    private static String PASSWORD_KEY = "db.password";
    private static Connection CONNECTION;

    static {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        try {
            CONNECTION = DriverManager.getConnection(
                    PropertiesUtil.getProperty(URL_KEY),
                    PropertiesUtil.getProperty(USERNAME_KEY),
                    PropertiesUtil.getProperty(PASSWORD_KEY));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private ConnectionManager() {

    }

//    public static Connection getConnection() {
//        return CONNECTION;
//    }

    public static PreparedStatement prepareStatement(String query){
        try {
            return CONNECTION.prepareStatement(query);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
