package com.acadflow.ui.util;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

/**
 * Utility class for showing alert dialogs
 */
public class AlertUtil {

    public static void showInfo(String title, String message) {
        showAlert(title, message, AlertType.INFORMATION);
    }

    public static void showError(String title, String message) {
        showAlert(title, message, AlertType.ERROR);
    }

    public static void showWarning(String title, String message) {
        showAlert(title, message, AlertType.WARNING);
    }

    public static void showSuccess(String title, String message) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText("Success");
        alert.setContentText(message);
        alert.showAndWait();
    }

    private static void showAlert(String title, String message, AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
