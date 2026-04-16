package com.acadflow.module1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import com.acadflow.ui.AcadflowApplication;
import javafx.application.Application;

@SpringBootApplication(scanBasePackages = "com.acadflow")
@EnableJpaRepositories(basePackages = "com.acadflow")
@EntityScan(basePackages = "com.acadflow")
public class Module1Application {
    public static void main(String[] args) {
        // Start Spring context
        ApplicationContext springContext = SpringApplication.run(Module1Application.class, args);
        
        // Pass Spring context to JavaFX application
        AcadflowApplication.setSpringContext(springContext);
        
        // Launch JavaFX application
        Application.launch(AcadflowApplication.class, args);
    }
}