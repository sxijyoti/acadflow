package com.acadflow.ui.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

import com.acadflow.ui.util.SessionManager;
import com.acadflow.ui.util.AlertUtil;

import java.io.IOException;
import java.util.Objects;

/**
 * Main Window Controller - manages sidebar and top bar navigation
 */
public class MainWindowController {

    @FXML private BorderPane mainBorderPane;
    @FXML private VBox sidebar;
    @FXML private Label userNameLabel;
    @FXML private Label userRoleLabel;
    @FXML private VBox contentArea;
    
    @FXML private Button dashboardBtn;
    @FXML private Button calendarBtn;
    @FXML private Button subjectsBtn;
    @FXML private Button assignmentsBtn;
    @FXML private Button resourcesBtn;
    @FXML private Button timetableBtn;
    @FXML private Button examsBtn;
    @FXML private Button profileBtn;
    @FXML private Button holidaysBtn;
    @FXML private Button logoutBtn;

    @FXML
    public void initialize() {
        setupUserInfo();
        setupNavigationButtons();
        loadDashboard();
    }

    private void setupUserInfo() {
        SessionManager session = SessionManager.getInstance();
        userNameLabel.setText(session.getUserName() != null ? session.getUserName() : "Guest");
        userRoleLabel.setText(session.getUserRole() != null ? session.getUserRole() : "STUDENT");
    }

    private void setupNavigationButtons() {
        dashboardBtn.setOnAction(e -> loadDashboard());
        calendarBtn.setOnAction(e -> loadCalendar());
        subjectsBtn.setOnAction(e -> loadSubjects());
        assignmentsBtn.setOnAction(e -> loadAssignments());
        resourcesBtn.setOnAction(e -> loadResources());
        timetableBtn.setOnAction(e -> loadTimetable());
        examsBtn.setOnAction(e -> loadExams());
        profileBtn.setOnAction(e -> loadProfile());
        holidaysBtn.setOnAction(e -> loadHolidays());
        logoutBtn.setOnAction(e -> handleLogout());
    }

    private void loadScreen(String fxmlFileName) {
        try {
            FXMLLoader loader = new FXMLLoader(
                Objects.requireNonNull(getClass().getResource("/fxml/" + fxmlFileName))
            );
            Parent screenContent = loader.load();
            contentArea.getChildren().clear();
            contentArea.getChildren().add(screenContent);
            VBox.setVgrow(screenContent, javafx.scene.layout.Priority.ALWAYS);
        } catch (IOException e) {
            AlertUtil.showError("Error", "Failed to load screen: " + e.getMessage());
        }
    }

    private void loadDashboard() {
        loadScreen("Dashboard.fxml");
    }

    private void loadCalendar() {
        loadScreen("Calendar.fxml");
    }

    private void loadSubjects() {
        loadScreen("SubjectEnrollment.fxml");
    }

    private void loadAssignments() {
        loadScreen("Assignments.fxml");
    }

    private void loadResources() {
        loadScreen("Resources.fxml");
    }

    private void loadTimetable() {
        loadScreen("Timetable.fxml");
    }

    private void loadExams() {
        loadScreen("Exams.fxml");
    }

    private void loadProfile() {
        loadScreen("Profile.fxml");
    }

    private void loadHolidays() {
        loadScreen("Holidays.fxml");
    }

    private void handleLogout() {
        SessionManager.getInstance().logout();
        updateUserInfo();
        disableAllButtons();
        loadDashboard();
        AlertUtil.showInfo("Logout", "You have been logged out successfully");
    }
    
    private void updateUserInfo() {
        userNameLabel.setText("Guest");
        userRoleLabel.setText("STUDENT");
    }
    
    private void disableAllButtons() {
        dashboardBtn.setDisable(false);
        calendarBtn.setDisable(false);
        subjectsBtn.setDisable(false);
        assignmentsBtn.setDisable(false);
        resourcesBtn.setDisable(false);
        timetableBtn.setDisable(false);
        examsBtn.setDisable(false);
        profileBtn.setDisable(false);
        holidaysBtn.setDisable(false);
    }
}
