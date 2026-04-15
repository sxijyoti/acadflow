package com.acadflow.ui.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import com.acadflow.ui.dto.UserDTO;
import com.acadflow.ui.services.SampleDataProvider;
import com.acadflow.ui.util.AlertUtil;

/**
 * Profile Controller - user profile management
 */
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

    @FXML
    public void initialize() {
        loadProfileData();
        setupButtonListeners();
    }

    private void loadProfileData() {
        UserDTO user = SampleDataProvider.getSampleUser();
        
        nameField.setText(user.getName());
        emailField.setText(user.getEmail());
        phoneField.setText(user.getPhone());
        departmentField.setText(user.getDepartment());
        semesterField.setText(user.getSemester());
        roleLabel.setText("Role: " + user.getRole());

        setFieldsEditable(false);
        saveButton.setDisable(true);
        cancelButton.setDisable(true);
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

        // In real implementation, call API
        AlertUtil.showSuccess("Profile Updated", "Your profile has been updated successfully");
        
        setFieldsEditable(false);
        saveButton.setDisable(true);
        cancelButton.setDisable(true);
        editButton.setDisable(false);
    }

    private void setFieldsEditable(boolean editable) {
        nameField.setEditable(editable);
        emailField.setEditable(editable);
        phoneField.setEditable(editable);
        departmentField.setEditable(editable);
        semesterField.setEditable(editable);
    }
}
