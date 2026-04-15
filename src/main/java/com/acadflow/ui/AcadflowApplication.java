package com.acadflow.ui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import com.acadflow.ui.util.SessionManager;
import com.acadflow.ui.util.NavigationManager;

import java.io.IOException;
import java.util.Objects;

/**
 * Main JavaFX Application Entry Point
 * Handles window initialization and scene management
 */
public class AcadflowApplication extends Application {

    private static Stage primaryStage;
    private NavigationManager navigationManager;

    @Override
    public void start(Stage stage) throws Exception {
        primaryStage = stage;
        navigationManager = new NavigationManager(stage);
        
        // Initialize session
        SessionManager.getInstance();
        
        // Load main window
        loadMainWindow();
        
        stage.setTitle("Academic Workflow Management System");
        stage.setWidth(1400);
        stage.setHeight(900);
        stage.setMinWidth(900);
        stage.setMinHeight(600);
        stage.show();
    }

    private void loadMainWindow() throws IOException {
        FXMLLoader loader = new FXMLLoader(
            Objects.requireNonNull(getClass().getResource("/fxml/MainWindow.fxml"))
        );
        Parent root = loader.load();
        
        Scene scene = new Scene(root);
        String css = Objects.requireNonNull(
            getClass().getResource("/css/styles.css")
        ).toExternalForm();
        scene.getStylesheets().add(css);
        
        primaryStage.setScene(scene);
    }

    public static Stage getPrimaryStage() {
        return primaryStage;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
