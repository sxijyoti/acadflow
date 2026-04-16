package com.acadflow.ui;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;

import com.acadflow.module1.Module1Application;
import com.acadflow.ui.controllers.MainWindowController;
import javafx.fxml.FXMLLoader;

import java.util.Objects;

public class TestNav extends Application {
    private static ConfigurableApplicationContext springContext;

    public static void main(String[] args) {
        springContext = SpringApplication.run(Module1Application.class, args);
        Application.launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        com.acadflow.ui.AcadflowApplication.setSpringContext(springContext); // Need to set this!
        
        FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(getClass().getResource("/fxml/MainWindow.fxml")));
        loader.setControllerFactory(c -> {
            try {
                if (springContext != null) {
                    try { return springContext.getBean(c); } catch (Exception ignored) { }
                }
                return c.getConstructor().newInstance();
            } catch (Exception e) {
                return null;
            }
        });
        
        Scene scene = new Scene(loader.load(), 800, 600);
        primaryStage.setScene(scene);
        primaryStage.show();

        new Thread(() -> {
            try {
                Thread.sleep(1000);
                System.out.println("Clicking subjects...");
                Platform.runLater(() -> ((Button) scene.lookup("#subjectsBtn")).fire());
                
                Thread.sleep(1000);
                System.out.println("Clicking calendar...");
                Platform.runLater(() -> ((Button) scene.lookup("#calendarBtn")).fire());

                Thread.sleep(1000);
                System.out.println("Clicking dashboard...");
                Platform.runLater(() -> ((Button) scene.lookup("#dashboardBtn")).fire());
                
                Thread.sleep(1000);
                Platform.runLater(() -> Platform.exit());
                System.exit(0);
            } catch (Exception e) {
                e.printStackTrace();
                System.exit(1);
            }
        }).start();
    }
}
