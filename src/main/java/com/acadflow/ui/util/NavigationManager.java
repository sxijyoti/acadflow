package com.acadflow.ui.util;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

/**
 * Manages navigation between different screens in the application
 * Uses MVC pattern with FXML files and Controllers
 */
public class NavigationManager {
    
    private Stage stage;
    private Parent mainWindowRoot;

    public NavigationManager(Stage stage) {
        this.stage = stage;
    }

    /**
     * Navigate to a specific screen
     * @param fxmlPath Path to FXML file (relative to resources/fxml/)
     * @return The loaded controller
     */
    public Object navigateTo(String fxmlPath) throws IOException {
        FXMLLoader loader = new FXMLLoader(
            Objects.requireNonNull(getClass().getResource("/fxml/" + fxmlPath))
        );
        Parent root = loader.load();
        
        Scene scene = new Scene(root);
        String css = Objects.requireNonNull(
            getClass().getResource("/css/styles.css")
        ).toExternalForm();
        scene.getStylesheets().add(css);
        
        stage.setScene(scene);
        return loader.getController();
    }

    /**
     * Replace content area in main window
     * Used when switching screens in the main content area
     */
    public Object loadScreenIntoMainContent(String fxmlPath, Parent mainContentArea) throws IOException {
        FXMLLoader loader = new FXMLLoader(
            Objects.requireNonNull(getClass().getResource("/fxml/" + fxmlPath))
        );
        Parent screenContent = loader.load();
        
        // Replace children based on parent type
        if (mainContentArea instanceof javafx.scene.layout.VBox) {
            ((javafx.scene.layout.VBox) mainContentArea).getChildren().clear();
            ((javafx.scene.layout.VBox) mainContentArea).getChildren().add(screenContent);
        } else if (mainContentArea instanceof javafx.scene.layout.HBox) {
            ((javafx.scene.layout.HBox) mainContentArea).getChildren().clear();
            ((javafx.scene.layout.HBox) mainContentArea).getChildren().add(screenContent);
        } else if (mainContentArea instanceof javafx.scene.layout.BorderPane) {
            ((javafx.scene.layout.BorderPane) mainContentArea).setCenter(screenContent);
        }
        
        return loader.getController();
    }

    public Stage getStage() {
        return stage;
    }
}
