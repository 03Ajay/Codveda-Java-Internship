package com.library.dao;

import com.library.model.User;
import java.sql.SQLException;
import java.util.List;

/**
 * User Data Access Object interface
 * Defines CRUD operations for User entity
 */
public interface UserDAO {

    /**
     * Add a new user to the database
     * 
     * @param user User object to add
     * @return Generated user ID
     * @throws SQLException if database operation fails
     */
    int addUser(User user) throws SQLException;

    /**
     * Update an existing user
     * 
     * @param user User object with updated information
     * @return true if update successful
     * @throws SQLException if database operation fails
     */
    boolean updateUser(User user) throws SQLException;

    /**
     * Delete a user by ID
     * 
     * @param userId User ID to delete
     * @return true if deletion successful
     * @throws SQLException if database operation fails
     */
    boolean deleteUser(int userId) throws SQLException;

    /**
     * Find user by ID
     * 
     * @param userId User ID to search
     * @return User object or null if not found
     * @throws SQLException if database operation fails
     */
    User findUserById(int userId) throws SQLException;

    /**
     * Find user by username
     * 
     * @param username Username to search
     * @return User object or null if not found
     * @throws SQLException if database operation fails
     */
    User findUserByUsername(String username) throws SQLException;

    /**
     * Find user by email
     * 
     * @param email Email to search
     * @return User object or null if not found
     * @throws SQLException if database operation fails
     */
    User findUserByEmail(String email) throws SQLException;

    /**
     * Get all active users
     * 
     * @return List of active users
     * @throws SQLException if database operation fails
     */
    List<User> getAllActiveUsers() throws SQLException;

    /**
     * Get all users
     * 
     * @return List of all users
     * @throws SQLException if database operation fails
     */
    List<User> getAllUsers() throws SQLException;

    /**
     * Search users by name (partial match)
     * 
     * @param name Name to search (can be partial)
     * @return List of matching users
     * @throws SQLException if database operation fails
     */
    List<User> searchUsersByName(String name) throws SQLException;

    /**
     * Get users by type
     * 
     * @param userType Type of user to filter
     * @return List of users of specified type
     * @throws SQLException if database operation fails
     */
    List<User> getUsersByType(User.UserType userType) throws SQLException;

    /**
     * Check if username exists
     * 
     * @param username Username to check
     * @return true if username exists
     * @throws SQLException if database operation fails
     */
    boolean usernameExists(String username) throws SQLException;

    /**
     * Check if email exists
     * 
     * @param email Email to check
     * @return true if email exists
     * @throws SQLException if database operation fails
     */
    boolean emailExists(String email) throws SQLException;

    /**
     * Activate or deactivate user
     * 
     * @param userId User ID
     * @param active New active status
     * @return true if update successful
     * @throws SQLException if database operation fails
     */
    boolean setUserActive(int userId, boolean active) throws SQLException;
}