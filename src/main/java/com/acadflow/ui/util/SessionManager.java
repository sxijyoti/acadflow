package com.acadflow.ui.util;

/**
 * Singleton pattern for managing user session
 * Stores current user information and role
 */
public class SessionManager {
    private static SessionManager instance;
    
    private Long userId;
    private String userName;
    private String userEmail;
    private String userRole; // STUDENT, INSTRUCTOR, ADMIN
    private String authToken;

    private SessionManager() {
    }

    public static synchronized SessionManager getInstance() {
        if (instance == null) {
            instance = new SessionManager();
        }
        return instance;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserRole() {
        return userRole;
    }

    public void setUserRole(String userRole) {
        this.userRole = userRole;
    }

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    public void logout() {
        userId = null;
        userName = null;
        userEmail = null;
        userRole = null;
        authToken = null;
    }

    public boolean isLoggedIn() {
        return userId != null && authToken != null;
    }
}
