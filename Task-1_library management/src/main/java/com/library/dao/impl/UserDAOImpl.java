package com.library.dao.impl;

import com.library.dao.UserDAO;
import com.library.model.User;
import com.library.util.DatabaseConnection;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * User DAO implementation using JDBC
 */
public class UserDAOImpl implements UserDAO {

    private final DatabaseConnection dbConnection;

    public UserDAOImpl() {
        this.dbConnection = DatabaseConnection.getInstance();
    }

    @Override
    public int addUser(User user) throws SQLException {
        String sql = "INSERT INTO users (username, email, full_name, phone_number, address, " +
                "user_type, max_books_allowed) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = dbConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getEmail());
            stmt.setString(3, user.getFullName());
            stmt.setString(4, user.getPhoneNumber());
            stmt.setString(5, user.getAddress());
            stmt.setString(6, user.getUserType().name());
            stmt.setInt(7, user.getMaxBooksAllowed());

            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        int userId = generatedKeys.getInt(1);
                        user.setUserId(userId);
                        conn.commit();
                        return userId;
                    }
                }
            }

            conn.rollback();
            throw new SQLException("Failed to add user, no ID generated");

        } catch (SQLException e) {
            throw new SQLException("Error adding user: " + e.getMessage(), e);
        }
    }

    @Override
    public boolean updateUser(User user) throws SQLException {
        String sql = "UPDATE users SET username = ?, email = ?, full_name = ?, " +
                "phone_number = ?, address = ?, user_type = ?, max_books_allowed = ? " +
                "WHERE user_id = ?";

        try (Connection conn = dbConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getEmail());
            stmt.setString(3, user.getFullName());
            stmt.setString(4, user.getPhoneNumber());
            stmt.setString(5, user.getAddress());
            stmt.setString(6, user.getUserType().name());
            stmt.setInt(7, user.getMaxBooksAllowed());
            stmt.setInt(8, user.getUserId());

            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                conn.commit();
                return true;
            }

            conn.rollback();
            return false;

        } catch (SQLException e) {
            throw new SQLException("Error updating user: " + e.getMessage(), e);
        }
    }

    @Override
    public boolean deleteUser(int userId) throws SQLException {
        // Soft delete - mark as inactive
        String sql = "UPDATE users SET is_active = FALSE WHERE user_id = ?";

        try (Connection conn = dbConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, userId);
            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                conn.commit();
                return true;
            }

            conn.rollback();
            return false;

        } catch (SQLException e) {
            throw new SQLException("Error deleting user: " + e.getMessage(), e);
        }
    }

    @Override
    public User findUserById(int userId) throws SQLException {
        String sql = "SELECT * FROM users WHERE user_id = ?";

        try (Connection conn = dbConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, userId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToUser(rs);
                }
            }

        } catch (SQLException e) {
            throw new SQLException("Error finding user by ID: " + e.getMessage(), e);
        }

        return null;
    }

    @Override
    public User findUserByUsername(String username) throws SQLException {
        String sql = "SELECT * FROM users WHERE username = ?";

        try (Connection conn = dbConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, username);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToUser(rs);
                }
            }

        } catch (SQLException e) {
            throw new SQLException("Error finding user by username: " + e.getMessage(), e);
        }

        return null;
    }

    @Override
    public User findUserByEmail(String email) throws SQLException {
        String sql = "SELECT * FROM users WHERE email = ?";

        try (Connection conn = dbConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, email);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToUser(rs);
                }
            }

        } catch (SQLException e) {
            throw new SQLException("Error finding user by email: " + e.getMessage(), e);
        }

        return null;
    }

    @Override
    public List<User> getAllActiveUsers() throws SQLException {
        String sql = "SELECT * FROM users WHERE is_active = TRUE ORDER BY full_name";

        try (Connection conn = dbConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery()) {

            List<User> users = new ArrayList<>();
            while (rs.next()) {
                users.add(mapResultSetToUser(rs));
            }
            return users;

        } catch (SQLException e) {
            throw new SQLException("Error getting all active users: " + e.getMessage(), e);
        }
    }

    @Override
    public List<User> getAllUsers() throws SQLException {
        String sql = "SELECT * FROM users ORDER BY full_name";

        try (Connection conn = dbConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery()) {

            List<User> users = new ArrayList<>();
            while (rs.next()) {
                users.add(mapResultSetToUser(rs));
            }
            return users;

        } catch (SQLException e) {
            throw new SQLException("Error getting all users: " + e.getMessage(), e);
        }
    }

    @Override
    public List<User> searchUsersByName(String name) throws SQLException {
        String sql = "SELECT * FROM users WHERE full_name LIKE ? AND is_active = TRUE ORDER BY full_name";

        try (Connection conn = dbConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, "%" + name + "%");
            try (ResultSet rs = stmt.executeQuery()) {
                List<User> users = new ArrayList<>();
                while (rs.next()) {
                    users.add(mapResultSetToUser(rs));
                }
                return users;
            }

        } catch (SQLException e) {
            throw new SQLException("Error searching users by name: " + e.getMessage(), e);
        }
    }

    @Override
    public List<User> getUsersByType(User.UserType userType) throws SQLException {
        String sql = "SELECT * FROM users WHERE user_type = ? AND is_active = TRUE ORDER BY full_name";

        try (Connection conn = dbConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, userType.name());
            try (ResultSet rs = stmt.executeQuery()) {
                List<User> users = new ArrayList<>();
                while (rs.next()) {
                    users.add(mapResultSetToUser(rs));
                }
                return users;
            }

        } catch (SQLException e) {
            throw new SQLException("Error getting users by type: " + e.getMessage(), e);
        }
    }

    @Override
    public boolean usernameExists(String username) throws SQLException {
        String sql = "SELECT COUNT(*) FROM users WHERE username = ?";

        try (Connection conn = dbConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, username);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }

        } catch (SQLException e) {
            throw new SQLException("Error checking username existence: " + e.getMessage(), e);
        }

        return false;
    }

    @Override
    public boolean emailExists(String email) throws SQLException {
        String sql = "SELECT COUNT(*) FROM users WHERE email = ?";

        try (Connection conn = dbConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, email);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }

        } catch (SQLException e) {
            throw new SQLException("Error checking email existence: " + e.getMessage(), e);
        }

        return false;
    }

    @Override
    public boolean setUserActive(int userId, boolean active) throws SQLException {
        String sql = "UPDATE users SET is_active = ? WHERE user_id = ?";

        try (Connection conn = dbConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setBoolean(1, active);
            stmt.setInt(2, userId);

            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                conn.commit();
                return true;
            }

            conn.rollback();
            return false;

        } catch (SQLException e) {
            throw new SQLException("Error setting user active status: " + e.getMessage(), e);
        }
    }

    /**
     * Helper method to map ResultSet to User object
     */
    private User mapResultSetToUser(ResultSet rs) throws SQLException {
        User user = new User();
        user.setUserId(rs.getInt("user_id"));
        user.setUsername(rs.getString("username"));
        user.setEmail(rs.getString("email"));
        user.setFullName(rs.getString("full_name"));
        user.setPhoneNumber(rs.getString("phone_number"));
        user.setAddress(rs.getString("address"));
        user.setUserType(User.UserType.valueOf(rs.getString("user_type")));

        Timestamp timestamp = rs.getTimestamp("registration_date");
        if (timestamp != null) {
            user.setRegistrationDate(timestamp.toLocalDateTime());
        }

        user.setActive(rs.getBoolean("is_active"));
        user.setMaxBooksAllowed(rs.getInt("max_books_allowed"));

        return user;
    }
}