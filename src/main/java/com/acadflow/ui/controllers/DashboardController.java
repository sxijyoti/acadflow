package com.acadflow.ui.controllers;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.application.Platform;

import com.acadflow.ui.dto.SubjectDTO;
import com.acadflow.ui.dto.AssignmentDTO;
import com.acadflow.module1.service.EnrollmentService;
import com.acadflow.module2.service.AssignmentService;
import com.acadflow.module2.service.SubmissionService;
import com.acadflow.module4.service.ExamService;
import com.acadflow.module4.dto.ExamResponseDTO;
import com.acadflow.module1.entity.Enrollment;
import com.acadflow.module2.entity.Assignment;
import com.acadflow.module2.dto.SubmissionResponseDTO;
import com.acadflow.ui.util.SessionManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.context.annotation.Scope;

import java.time.LocalDate;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.stream.Collectors;

@Component("uiDashboardController")
@Scope("prototype")
public class DashboardController {

    @FXML private VBox dashboardContainer;
    @FXML private Label welcomeLabel;
    @FXML private VBox overviewCardsContainer;
    @FXML private VBox upcomingContainer;
    @FXML private VBox quickLinksContainer;

    @Autowired private EnrollmentService enrollmentService;
    @Autowired private AssignmentService assignmentService;
    @Autowired private SubmissionService submissionService;
    @Autowired private ExamService examService;
    @Autowired private com.acadflow.module1.service.UserService userService;

    @FXML
    public void initialize() {
        loadDashboardData();
    }

    private void loadDashboardData() {
        new Thread(() -> {
            try {
                Long userId = SessionManager.getInstance().getUserId();
                
                var userOpt = userService.getUserById(userId);
                final String userName = userOpt.isPresent() && userOpt.get().getFirstName() != null 
                                          ? userOpt.get().getFirstName() 
                                          : (userOpt.isPresent() && userOpt.get().getName() != null 
                                            ? userOpt.get().getName() 
                                            : "User");
                
                List<Enrollment> enrollments = enrollmentService.getEnrolledSubjects(userId);
                int enrolledCount = enrollments.size();
                
                List<Assignment> assignments = assignmentService.getAssignmentsForStudent(userId);
                List<SubmissionResponseDTO> submissions = submissionService.getByUser(userId);

                Map<Long, SubmissionResponseDTO> submissionMap = submissions.stream()
                        .collect(Collectors.toMap(s -> s.assignmentId, s -> s));

                List<AssignmentDTO> dtos = assignments.stream().map(a -> {
                    AssignmentDTO dto = new AssignmentDTO();
                    dto.setId(a.getId());
                    dto.setTitle(a.getTitle());
                    dto.setDescription(a.getDescription());
                    dto.setSubjectCode(a.getSubject().getCode());
                    dto.setSubjectName(a.getSubject().getName());
                    dto.setDueDate(a.getDeadline() != null ? a.getDeadline().toLocalDate() : LocalDate.of(2099, 12, 31));
                    
                    if (submissionMap.containsKey(a.getId())) {
                        dto.setStatus(submissionMap.get(a.getId()).status);
                    } else if (dto.getDueDate().isBefore(LocalDate.now())) {
                        dto.setStatus("LATE");
                    } else {
                        dto.setStatus("PENDING");
                    }
                    return dto;
                }).toList();
                
                int upExams = 0;
                for (Enrollment e : enrollments) {
                    List<ExamResponseDTO> exams = examService.getExamsForSubject(e.getSubject().getId());
                    if (exams != null) {
                        for (ExamResponseDTO ex : exams) {
                            if (ex.getDate() != null && !ex.getDate().toLocalDate().isBefore(LocalDate.now())) {
                                upExams++;
                            }
                        }
                    }
                }
                final int upcomingExamsCount = upExams;
                
                Platform.runLater(() -> {
                    if (welcomeLabel != null) {
                        welcomeLabel.setText("Hello " + userName + "!");
                    }
                    overviewCardsContainer.getChildren().clear();
                    createOverviewCards(enrolledCount, dtos, upcomingExamsCount);
                    createUpcomingEvents(dtos);
                    createQuickLinks();
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }

    private void createOverviewCards(int enrolledCount, List<AssignmentDTO> assignments, int upcomingExamsCount) {
        long pendingAssignments = assignments.stream()
                .filter(a -> "PENDING".equals(a.getStatus()))
                .count();

        HBox cardsBox = new HBox(15);
        cardsBox.setStyle("-fx-padding: 20; -fx-fill-width: true;");

        // Enrolled subjects card
        cardsBox.getChildren().add(createCard("Enrolled Subjects", 
            String.valueOf(enrolledCount), "#3498db"));

        // Pending assignments card
        cardsBox.getChildren().add(createCard("Pending Assignments", 
            String.valueOf(pendingAssignments), "#e74c3c"));

        // Upcoming exams card
        cardsBox.getChildren().add(createCard("Upcoming Exams", String.valueOf(upcomingExamsCount), "#f39c12"));

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

    private void createUpcomingEvents(List<AssignmentDTO> assignments) {
        List<AssignmentDTO> pendingAssignments = assignments.stream()
                .filter(a -> "PENDING".equals(a.getStatus()))
                .toList();

        VBox upcomingBox = new VBox(8);
        upcomingBox.setStyle("-fx-padding: 15; -fx-border-radius: 8; -fx-border-color: #ddd;");
        
        Label heading = new Label("Upcoming Deadlines");
        heading.setStyle("-fx-font-size: 14; -fx-font-weight: bold;");
        upcomingBox.getChildren().add(heading);

        if (pendingAssignments.isEmpty()) {
            Label empty = new Label("No pending deadlines!");
            empty.setStyle("-fx-text-fill: #999; -fx-font-style: italic;");
            upcomingBox.getChildren().add(empty);
        }

        for (AssignmentDTO assignment : pendingAssignments) {
            HBox eventBox = new HBox(15);
            eventBox.setStyle("-fx-padding: 8; -fx-border-color: #eee; -fx-border-width: 0 0 1 0;");
            
            Label assignmentLabel = new Label("" + assignment.getTitle());
            Label dateLabel = new Label(assignment.getDueDate().toString());
            dateLabel.setStyle("-fx-text-fill: #e74c3c; -fx-font-size: 11;");
            
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
        
        String[] linkLabels = {"Calendar", "Subjects", "Assignments", "Resources"};
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
