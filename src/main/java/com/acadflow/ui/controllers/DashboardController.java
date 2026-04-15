package com.acadflow.ui.controllers;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import com.acadflow.ui.dto.SubjectDTO;
import com.acadflow.ui.dto.AssignmentDTO;
import com.acadflow.ui.services.SampleDataProvider;

import java.time.LocalDate;
import java.util.List;

/**
 * Dashboard Controller - displays home overview
 */
public class DashboardController {

    @FXML private VBox dashboardContainer;
    @FXML private VBox overviewCardsContainer;
    @FXML private VBox upcomingContainer;
    @FXML private VBox quickLinksContainer;

    @FXML
    public void initialize() {
        loadDashboardData();
    }

    private void loadDashboardData() {
        // Load overview cards
        createOverviewCards();
        
        // Load upcoming events
        createUpcomingEvents();
        
        // Load quick links
        createQuickLinks();
    }

    private void createOverviewCards() {
        List<SubjectDTO> subjects = SampleDataProvider.getSampleEnrolledSubjects();
        List<AssignmentDTO> assignments = SampleDataProvider.getSampleAssignments();
        
        long pendingAssignments = assignments.stream()
                .filter(a -> "PENDING".equals(a.getStatus()))
                .count();

        HBox cardsBox = new HBox(15);
        cardsBox.setStyle("-fx-padding: 20; -fx-fill-width: true;");

        // Enrolled subjects card
        cardsBox.getChildren().add(createCard("Enrolled Subjects", 
            String.valueOf(subjects.size()), "#3498db"));

        // Pending assignments card
        cardsBox.getChildren().add(createCard("Pending Assignments", 
            String.valueOf(pendingAssignments), "#e74c3c"));

        // Upcoming exams card
        cardsBox.getChildren().add(createCard("Upcoming Exams", "3", "#f39c12"));

        overviewCardsContainer.getChildren().add(cardsBox);
    }

    private VBox createCard(String title, String value, String bgColor) {
        VBox card = new VBox(10);
        card.setPrefWidth(150);
        card.setPrefHeight(120);
        card.setStyle("-fx-border-radius: 8; -fx-background-color: " + bgColor + "; " +
                     "-fx-text-fill: white; -fx-padding: 15;");
        
        Label titleLabel = new Label(title);
        titleLabel.setStyle("-fx-font-size: 12; -fx-text-fill: white;");
        
        Label valueLabel = new Label(value);
        valueLabel.setStyle("-fx-font-size: 28; -fx-font-weight: bold; -fx-text-fill: white;");
        
        card.getChildren().addAll(titleLabel, valueLabel);
        return card;
    }

    private void createUpcomingEvents() {
        List<AssignmentDTO> pendingAssignments = SampleDataProvider.getSampleAssignments()
                .stream()
                .filter(a -> "PENDING".equals(a.getStatus()))
                .toList();

        VBox upcomingBox = new VBox(8);
        upcomingBox.setStyle("-fx-padding: 15; -fx-border-radius: 8; -fx-border-color: #ddd;");
        
        Label heading = new Label("Upcoming Deadlines");
        heading.setStyle("-fx-font-size: 14; -fx-font-weight: bold;");
        upcomingBox.getChildren().add(heading);

        for (AssignmentDTO assignment : pendingAssignments) {
            HBox eventBox = new HBox(15);
            eventBox.setStyle("-fx-padding: 8; -fx-border-color: #eee; -fx-border-width: 0 0 1 0;");
            
            Label assignmentLabel = new Label("📝 " + assignment.getTitle());
            Label dateLabel = new Label(assignment.getDueDate().toString());
            dateLabel.setStyle("-fx-text-fill: #999; -fx-font-size: 11;");
            
            VBox textBox = new VBox(2);
            textBox.getChildren().addAll(assignmentLabel, dateLabel);
            HBox.setHgrow(textBox, javafx.scene.layout.Priority.ALWAYS);
            
            eventBox.getChildren().add(textBox);
            upcomingBox.getChildren().add(eventBox);
        }

        upcomingContainer.getChildren().clear();
        upcomingContainer.getChildren().add(upcomingBox);
    }

    private void createQuickLinks() {
        HBox linksBox = new HBox(10);
        linksBox.setStyle("-fx-padding: 15;");
        
        String[] linkLabels = {"📅 Calendar", "📚 Subjects", "📝 Assignments", "📖 Resources"};
        String[] linkStyles = {"-fx-padding: 8 15; -fx-border-radius: 4; -fx-background-color: #3498db; -fx-text-fill: white; -fx-cursor: hand;",
                              "-fx-padding: 8 15; -fx-border-radius: 4; -fx-background-color: #2ecc71; -fx-text-fill: white; -fx-cursor: hand;",
                              "-fx-padding: 8 15; -fx-border-radius: 4; -fx-background-color: #e74c3c; -fx-text-fill: white; -fx-cursor: hand;",
                              "-fx-padding: 8 15; -fx-border-radius: 4; -fx-background-color: #9b59b6; -fx-text-fill: white; -fx-cursor: hand;"};
        
        for (int i = 0; i < linkLabels.length; i++) {
            Label link = new Label(linkLabels[i]);
            link.setStyle(linkStyles[i]);
            linksBox.getChildren().add(link);
        }

        quickLinksContainer.getChildren().clear();
        quickLinksContainer.getChildren().add(linksBox);
    }
}
