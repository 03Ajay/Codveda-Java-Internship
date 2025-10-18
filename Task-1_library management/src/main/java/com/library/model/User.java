package com.library.model;

import java.time.LocalDateTime;

/**
 * User model class representing library users
 */
public class User {
    private int userId;
    private String username;
    private String email;
    private String fullName;
    private String phoneNumber;
    private String address;
    private UserType userType;
    private LocalDateTime registrationDate;
    private boolean isActive;
    private int maxBooksAllowed;

    public enum UserType {
        STUDENT, FACULTY, STAFF
    }

    // Default constructor
    public User() {
        this.registrationDate = LocalDateTime.now();
        this.isActive = true;
        this.userType = UserType.STUDENT;
        this.maxBooksAllowed = 5;
    }

    // Constructor with essential fields
    public User(String username, String email, String fullName, UserType userType) {
        this();
        this.username = username;
        this.email = email;
        this.fullName = fullName;
        this.userType = userType;
        setMaxBooksBasedOnType(userType);
    }

    // Constructor with all fields
    public User(int userId, String username, String email, String fullName,
            String phoneNumber, String address, UserType userType,
            LocalDateTime registrationDate, boolean isActive, int maxBooksAllowed) {
        this.userId = userId;
        this.username = username;
        this.email = email;
        this.fullName = fullName;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.userType = userType;
        this.registrationDate = registrationDate;
        this.isActive = isActive;
        this.maxBooksAllowed = maxBooksAllowed;
    }

    // Getters and Setters
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
        setMaxBooksBasedOnType(userType);
    }

    public LocalDateTime getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(LocalDateTime registrationDate) {
        this.registrationDate = registrationDate;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public int getMaxBooksAllowed() {
        return maxBooksAllowed;
    }

    public void setMaxBooksAllowed(int maxBooksAllowed) {
        this.maxBooksAllowed = maxBooksAllowed;
    }

    // Helper method to set max books based on user type
    private void setMaxBooksBasedOnType(UserType userType) {
        switch (userType) {
            case STUDENT:
                this.maxBooksAllowed = 5;
                break;
            case FACULTY:
                this.maxBooksAllowed = 10;
                break;
            case STAFF:
                this.maxBooksAllowed = 20;
                break;
            default:
                this.maxBooksAllowed = 5;
        }
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", fullName='" + fullName + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", userType=" + userType +
                ", isActive=" + isActive +
                ", maxBooksAllowed=" + maxBooksAllowed +
                '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        User user = (User) obj;
        return userId == user.userId;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(userId);
    }
}