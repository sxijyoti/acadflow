package com.acadflow.ui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import com.acadflow.ui.util.SessionManager;
import com.acadflow.ui.util.NavigationManager;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;

import java.io.IOException;
import java.util.Objects;

/**
 * Main JavaFX Application Entry Point
 * Handles window initialization and scene management
 */
public class AcadflowApplication extends Application {

    private static Stage primaryStage;
    private NavigationManager navigationManager;
    private static ApplicationContext springContext;

    @Override
    public void start(Stage stage) throws Exception {
        primaryStage = stage;
        navigationManager = new NavigationManager(stage);
        
        // Initialize session
        SessionManager.getInstance();
        
        // Load login window (instead of main window)
        loadLoginWindow();
        
        stage.setTitle("Academic Workflow Management System");
        stage.setWidth(800);
        stage.setHeight(700);
        stage.centerOnScreen();
        stage.setResizable(false);
        stage.show();
    }

    private void loadLoginWindow() throws IOException {
        FXMLLoader loader = new FXMLLoader(
            Objects.requireNonNull(getClass().getResource("/fxml/Login.fxml"))
        );
        
        // Set controller factory to use Spring context for dependency injection
        if (springContext != null) {
            loader.setControllerFactory(springContext::getBean);
        } else {
            // Fallback if spring context is not available
            loader.setControllerFactory(c -> {
                try {
                    return c.getConstructor().newInstance();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            });
        }
        
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

    public static ApplicationContext getSpringContext() { return springContext; }

    public static void setSpringContext(ApplicationContext context) {
        springContext = context;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
