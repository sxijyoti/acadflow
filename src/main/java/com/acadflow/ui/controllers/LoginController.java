package com.acadflow.ui.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextField;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.application.Platform;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.acadflow.module1.entity.User;
import com.acadflow.module1.service.UserService;
import com.acadflow.ui.util.SessionManager;
import com.acadflow.ui.util.AlertUtil;

import java.util.Optional;
import java.util.Objects;

/**
 * Login Controller - handles user authentication
 */
@Component
public class LoginController {
    
    @FXML public TextField emailField;
    @FXML public PasswordField passwordField;
    @FXML public Button loginButton;
    @FXML public Label errorLabel;
    @FXML public ProgressIndicator loadingIndicator;
    
    @Autowired
    private UserService userService;
    
    private static LoginController instance;
    
    @FXML
    public void initialize() {
        // Allow Enter key to trigger login
        emailField.setOnKeyPressed(e -> {
            if (e.getCode().getName().equals("Enter")) {
                handleLogin();
            }
        });
        
        passwordField.setOnKeyPressed(e -> {
            if (e.getCode().getName().equals("Enter")) {
                handleLogin();
            }
        });
        
        loginButton.setOnAction(e -> handleLogin());
        
        // Clear error message when user starts typing
        emailField.textProperty().addListener((obs, oldVal, newVal) -> clearErrorMessage());
        passwordField.textProperty().addListener((obs, oldVal, newVal) -> clearErrorMessage());
    }
    
    @FXML
    private void handleLogin() {
        String email = emailField.getText().trim();
        String password = passwordField.getText();
        
        System.out.println("[DEBUG] Login attempt with email: " + email);
        
        // Validation
        if (email.isEmpty()) {
            showErrorMessage("Please enter your email address");
            return;
        }
        
        if (!email.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            showErrorMessage("Please enter a valid email address");
            return;
        }
        
        if (password.isEmpty()) {
            showErrorMessage("Please enter your password");
            return;
        }
        
        // Show loading indicator
        setLoadingState(true);
        
        // Perform authentication in background thread
        new Thread(() -> {
            try {
                System.out.println("[DEBUG] UserService: " + userService);
                System.out.println("[DEBUG] Attempting to authenticate with email: " + email + ", password length: " + password.length());
                
                Optional<User> user = userService.authenticate(email, password);
                
                System.out.println("[DEBUG] Authentication result: " + user.isPresent());
                
                Platform.runLater(() -> {
                    if (user.isPresent()) {
                        System.out.println("[DEBUG] Login successful for: " + user.get().getEmail());
                        // Authentication successful - store user info in session
                        SessionManager session = SessionManager.getInstance();
                        session.setUserId(user.get().getId());
                        session.setUserName(user.get().getName());
                        session.setUserEmail(user.get().getEmail());
                        session.setUserRole(user.get().getRole().toString());
                        session.setAuthToken("token_" + System.currentTimeMillis());
                        
                        // Navigate to main application
                        navigateToMainApplication();
                    } else {
                        // Authentication failed
                        System.out.println("[DEBUG] Authentication failed for: " + email);
                        setLoadingState(false);
                        showErrorMessage("Invalid email or password. Please try again.");
                        passwordField.clear();
                    }
                });
            } catch (Exception ex) {
                System.out.println("[DEBUG] Exception during login: " + ex.getMessage());
                ex.printStackTrace();
                Platform.runLater(() -> {
                    setLoadingState(false);
                    showErrorMessage("An error occurred during login: " + ex.getMessage());
                });
            }
        }).start();
    }
    
    private void navigateToMainApplication() {
        try {
            // Get the current stage and load main window
            Stage stage = (Stage) emailField.getScene().getWindow();
            
            FXMLLoader loader = new FXMLLoader(
                Objects.requireNonNull(getClass().getResource("/fxml/MainWindow.fxml"))
            );
            loader.setControllerFactory(c -> {
                // This allows Spring to manage the controller
                try {
                    return c.getConstructor().newInstance();
                } catch (Exception e) {
                    return null;
                }
            });
            
            Parent root = loader.load();
            Scene scene = new Scene(root);
            String css = Objects.requireNonNull(
                getClass().getResource("/css/styles.css")
            ).toExternalForm();
            scene.getStylesheets().add(css);
            
            stage.setScene(scene);
            stage.setTitle("Academic Workflow Management System");
            stage.setWidth(1400);
            stage.setHeight(900);
            stage.setMinWidth(900);
            stage.setMinHeight(600);
            stage.centerOnScreen();
            
            setLoadingState(false);
        } catch (Exception e) {
            setLoadingState(false);
            showErrorMessage("Failed to load main application");
            e.printStackTrace();
        }
    }
    
    private void showErrorMessage(String message) {
        errorLabel.setText(message);
        errorLabel.setVisible(true);
        errorLabel.setManaged(true);
    }
    
    private void clearErrorMessage() {
        errorLabel.setVisible(false);
        errorLabel.setManaged(false);
    }
    
    private void setLoadingState(boolean loading) {
        Platform.runLater(() -> {
            loginButton.setDisable(loading);
            emailField.setDisable(loading);
            passwordField.setDisable(loading);
            loadingIndicator.setVisible(loading);
            loadingIndicator.setManaged(loading);
        });
    }
}
