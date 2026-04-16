package com.acadflow.ui;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Stage;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;

import com.acadflow.module1.Module1Application;

import java.util.Objects;
import java.io.IOException;

public class TestAllScreens extends Application {

    private static ConfigurableApplicationContext springContext;

    public static void main(String[] args) {
        springContext = SpringApplication.run(Module1Application.class, args);
        Application.launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        String[] screens = {"Dashboard.fxml", "Calendar.fxml", "SubjectEnrollment.fxml", "Assignments.fxml", "Resources.fxml", "Timetable.fxml", "Holidays.fxml", "Exams.fxml", "Profile.fxml"};
        boolean overallSuccess = true;
        for (String screen : screens) {
            try {
                FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(getClass().getResource("/fxml/" + screen)));
                loader.setControllerFactory(c -> {
                    try {
                        if (springContext != null) {
                            try { return springContext.getBean(c); } catch (Exception ignored) { }
                        }
                        return c.getConstructor().newInstance();
                    } catch (Exception e) {
                        System.err.println("Controller factory failed for: " + c.getName());
                        e.printStackTrace();
                        return null;
                    }
                });
                loader.load();
                System.out.println("SUCCESS: " + screen);
            } catch (Exception e) {
                System.out.println("FAILED: " + screen);
                e.printStackTrace();
                overallSuccess = false;
            }
        }
        
        if (overallSuccess) {
            System.out.println("ALL SCREENS LOADED FINE.");
            System.exit(0);
        } else {
            System.out.println("SOME SCREENS FAILED.");
            System.exit(1);
        }
    }
}
