package com.acadflow.ui.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.application.Platform;

import com.acadflow.ui.dto.UserDTO;
import com.acadflow.ui.util.SessionManager;
import com.acadflow.ui.util.AlertUtil;
import com.acadflow.module1.entity.User;
import com.acadflow.module1.service.UserService;

import org.springframework.stereotype.Component;
import org.springframework.context.annotation.Scope;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

/**
 * Profile Controller - user profile management
 */
@Component("uiProfileController")
@Scope("prototype")
public class ProfileController {

    @FXML private VBox profileContainer;
    @FXML private TextField nameField;
    @FXML private TextField emailField;
    @FXML private TextField phoneField;
    @FXML private TextField departmentField;
    @FXML private TextField semesterField;
    @FXML private Label roleLabel;
    @FXML private Button saveButton;
    @FXML private Button editButton;
    @FXML private Button cancelButton;

    @Autowired
    private UserService userService;

    private User currentUser;

    @FXML
    public void initialize() {
        loadProfileData();
        setupButtonListeners();
    }


    private void loadProfileData() {
        new Thread(() -> {
            try {
                Long userId = SessionManager.getInstance().getUserId();
                Optional<User> userOpt = userService.getUserById(userId);
                if (userOpt.isPresent()) {
                    currentUser = userOpt.get();
                    Platform.runLater(() -> {
                        nameField.setText(currentUser.getName() != null ? currentUser.getName() : "");
                        emailField.setText(currentUser.getEmail() != null ? currentUser.getEmail() : "");
                        phoneField.setText(""); // No phone in user entity
                        departmentField.setText(currentUser.getDepartment() != null ? currentUser.getDepartment() : "");
                        semesterField.setText(currentUser.getSemester() != null ? String.valueOf(currentUser.getSemester()) : "");
                        roleLabel.setText("Role: " + currentUser.getRole().name());

                        setFieldsEditable(false);
                        saveButton.setDisable(true);
                        cancelButton.setDisable(true);
                        editButton.setDisable(false);
                        
                        // Notify Dashboard if needed (handled externally or by refresh)
                    });
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }

    private void setupButtonListeners() {
        editButton.setOnAction(e -> {
            setFieldsEditable(true);
            saveButton.setDisable(false);
            cancelButton.setDisable(false);
            editButton.setDisable(true);
        });

        saveButton.setOnAction(e -> {
            saveProfile();
        });

        cancelButton.setOnAction(e -> {
            loadProfileData();
        });
    }

    private void saveProfile() {
        // Validate fields
        if (nameField.getText().isEmpty() || emailField.getText().isEmpty()) {
            AlertUtil.showWarning("Validation", "Name and Email are required");
            return;
        }
        
        new Thread(() -> {
            try {
                // Split name to simple first and last for demo
                String[] names = nameField.getText().split(" ", 2);
                currentUser.setFirstName(names[0]);
                if (names.length > 1) {
                    currentUser.setLastName(names[1]);
                } else {
                    currentUser.setLastName("");
                }
                
                currentUser.setEmail(emailField.getText());
                currentUser.setDepartment(departmentField.getText());
                if (!semesterField.getText().isEmpty()) {
                    try {
                        currentUser.setSemester(Integer.parseInt(semesterField.getText()));
                    } catch (NumberFormatException e) {
                        Platform.runLater(() -> AlertUtil.showWarning("Validation", "Semester must be a number"));
                        return;
                    }
                }
                
                userService.saveUser(currentUser);
                
                Platform.runLater(() -> {
                    AlertUtil.showSuccess("Profile Updated", "Your profile has been updated successfully");
                    setFieldsEditable(false);
                    saveButton.setDisable(true);
                    cancelButton.setDisable(true);
                    editButton.setDisable(false);
                    loadProfileData(); // Reload to refresh the view to be safe
                });
            } catch (Exception ex) {
                Platform.runLater(() -> AlertUtil.showError("Error", "Failed to update profile: " + ex.getMessage()));
            }
        }).start();
    }

    private void setFieldsEditable(boolean editable) {
        nameField.setEditable(editable);
        emailField.setEditable(editable);
        phoneField.setEditable(editable);
        departmentField.setEditable(editable);
        semesterField.setEditable(editable);
    }
}
