package com.library.util;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Database Connection Utility Class
 * Handles database connections using JDBC
 * Implements Singleton pattern for connection management
 */
public class DatabaseConnection {
    private static DatabaseConnection instance;
    private static Properties properties;
    private static String url;
    private static String username;
    private static String password;
    private static String driver;

    // Private constructor to prevent instantiation
    private DatabaseConnection() {
        loadProperties();
    }

    /**
     * Get singleton instance of DatabaseConnection
     */
    public static synchronized DatabaseConnection getInstance() {
        if (instance == null) {
            instance = new DatabaseConnection();
        }
        return instance;
    }

    /**
     * Load database properties from configuration file
     */
    private void loadProperties() {
        properties = new Properties();
        try (InputStream input = getClass().getClassLoader()
                .getResourceAsStream("database.properties")) {

            if (input == null) {
                throw new RuntimeException("Unable to find database.properties file");
            }

            properties.load(input);

            driver = properties.getProperty("db.driver");
            url = properties.getProperty("db.url");
            username = properties.getProperty("db.username");
            password = properties.getProperty("db.password");

            // Load JDBC driver
            Class.forName(driver);

        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException("Failed to load database configuration", e);
        }
    }

    /**
     * Get database connection
     * 
     * @return Connection object
     * @throws SQLException if connection fails
     */
    public Connection getConnection() throws SQLException {
        try {
            Connection connection = DriverManager.getConnection(url, username, password);

            // Set connection properties
            connection.setAutoCommit(false); // Enable transaction management

            return connection;

        } catch (SQLException e) {
            System.err.println("Failed to create database connection: " + e.getMessage());
            throw e;
        }
    }

    /**
     * Test database connection
     * 
     * @return true if connection is successful
     */
    public boolean testConnection() {
        try (Connection connection = getConnection()) {
            return connection != null && !connection.isClosed();
        } catch (SQLException e) {
            System.err.println("Database connection test failed: " + e.getMessage());
            return false;
        }
    }

    /**
     * Close connection safely
     * 
     * @param connection Connection to close
     */
    public static void closeConnection(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                System.err.println("Error closing connection: " + e.getMessage());
            }
        }
    }

    /**
     * Commit transaction
     * 
     * @param connection Connection to commit
     */
    public static void commitTransaction(Connection connection) {
        if (connection != null) {
            try {
                connection.commit();
            } catch (SQLException e) {
                System.err.println("Error committing transaction: " + e.getMessage());
            }
        }
    }

    /**
     * Rollback transaction
     * 
     * @param connection Connection to rollback
     */
    public static void rollbackTransaction(Connection connection) {
        if (connection != null) {
            try {
                connection.rollback();
            } catch (SQLException e) {
                System.err.println("Error rolling back transaction: " + e.getMessage());
            }
        }
    }

    /**
     * Get property value
     * 
     * @param key Property key
     * @return Property value
     */
    public String getProperty(String key) {
        return properties.getProperty(key);
    }

    /**
     * Get fine per day amount
     * 
     * @return Fine amount per day
     */
    public double getFinePerDay() {
        String fineStr = properties.getProperty("fine.per.day", "2.00");
        return Double.parseDouble(fineStr);
    }

    /**
     * Get grace period for fines
     * 
     * @return Grace period in days
     */
    public int getGracePeriodDays() {
        String graceStr = properties.getProperty("fine.grace.period.days", "7");
        return Integer.parseInt(graceStr);
    }
}