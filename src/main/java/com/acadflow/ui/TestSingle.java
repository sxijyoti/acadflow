package com.acadflow.ui;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;

import java.util.Objects;
import java.util.concurrent.CountDownLatch;

public class TestSingle extends Application {

    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(getClass().getResource("/fxml/Assignments.fxml")));
        
        Scene scene = new Scene(loader.load(), 800, 600);
        primaryStage.setScene(scene);
        primaryStage.show();

        new Thread(() -> {
            try {
                Thread.sleep(2000);
                System.out.println("Finished wait. Exiting with 0");
                System.exit(0);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }
}
