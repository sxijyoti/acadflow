package com.acadflow.ui.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.stage.FileChooser;
import javafx.application.Platform;

import com.acadflow.ui.dto.AssignmentDTO;
import com.acadflow.ui.util.AlertUtil;
import com.acadflow.ui.util.SessionManager;
import com.acadflow.module2.entity.Assignment;
import com.acadflow.module2.dto.SubmissionResponseDTO;
import com.acadflow.module2.service.AssignmentService;
import com.acadflow.module2.service.SubmissionService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.context.annotation.Scope;

import java.io.File;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component("uiAssignmentsController")
@Scope("prototype")
public class AssignmentsController {

    @FXML private VBox assignmentsContainer;
    @FXML private ComboBox<String> statusFilter;
    @FXML private TableView<AssignmentDTO> assignmentsTable;
    @FXML private TableColumn<AssignmentDTO, String> titleColumn;
    @FXML private TableColumn<AssignmentDTO, String> subjectColumn;
    @FXML private TableColumn<AssignmentDTO, LocalDate> deadlineColumn;
    @FXML private TableColumn<AssignmentDTO, String> statusColumn;
    @FXML private TableColumn<AssignmentDTO, Void> actionColumn;
    private ObservableList<AssignmentDTO> allAssignments;

    @Autowired
    private AssignmentService assignmentService;

    @Autowired
    private SubmissionService submissionService;

    @FXML
    public void initialize() {
        setupTable();
        setupStatusFilter();
        loadAssignments();
    }
    
    private void setupStatusFilter() {
        statusFilter.setItems(FXCollections.observableArrayList(
            "All", "PENDING", "SUBMITTED", "LATE", "GRADED"
        ));
        statusFilter.setValue("All");
        statusFilter.setOnAction(e -> filterAssignments());
    }
    
    private void filterAssignments() {
        String selectedStatus = statusFilter.getValue();
        if ("All".equals(selectedStatus)) {
            assignmentsTable.setItems(allAssignments);
        } else {
            ObservableList<AssignmentDTO> filtered = FXCollections.observableArrayList(
                allAssignments.stream()
                    .filter(a -> selectedStatus.equals(a.getStatus()))
                    .toList()
            );
            assignmentsTable.setItems(filtered);
        }
    }

    private void setupTable() {
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        subjectColumn.setCellValueFactory(new PropertyValueFactory<>("subjectCode"));
        deadlineColumn.setCellValueFactory(new PropertyValueFactory<>("dueDate"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));

        actionColumn.setCellFactory(param -> new javafx.scene.control.TableCell<AssignmentDTO, Void>() {
            private final Button submitBtn = new Button("Submit");
            private final Button viewBtn = new Button("View");
            {
                submitBtn.setStyle("-fx-padding: 5 10; -fx-background-color: #3498db; -fx-text-fill: white; -fx-cursor: hand;");
                viewBtn.setStyle("-fx-padding: 5 10; -fx-background-color: #95a5a6; -fx-text-fill: white; -fx-margin-left: 5; -fx-cursor: hand;");
                
                submitBtn.setOnAction(event -> {
                    AssignmentDTO assignment = getTableView().getItems().get(getIndex());
                    handleSubmit(assignment);
                });
                
                viewBtn.setOnAction(event -> {
                    AssignmentDTO assignment = getTableView().getItems().get(getIndex());
                    handleView(assignment);
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    AssignmentDTO assignmentItem = getTableView().getItems().get(getIndex());
                    if ("PENDING".equals(assignmentItem.getStatus())) {
                        submitBtn.setDisable(false);
                    } else {
                        submitBtn.setDisable(true);
                    }

                    javafx.scene.layout.HBox hbox = new javafx.scene.layout.HBox(5);
                    hbox.getChildren().addAll(submitBtn, viewBtn);
                    setGraphic(hbox);
                }
            }
        });
    }

    private void loadAssignments() {
        new Thread(() -> {
            try {
                Long userId = SessionManager.getInstance().getUserId();
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
                    // Assignment deadline is LocalDateTime, UI expects LocalDate
                    dto.setDueDate(a.getDeadline() != null ? a.getDeadline().toLocalDate() : LocalDate.of(2099, 12, 31));
                    
                    if (submissionMap.containsKey(a.getId())) {
                        dto.setStatus(submissionMap.get(a.getId()).status);
                    } else {
                        dto.setStatus("PENDING");
                    }
                    return dto;
                }).collect(Collectors.toList());

                Platform.runLater(() -> {
                    allAssignments = FXCollections.observableArrayList(dtos);
                    assignmentsTable.setItems(allAssignments);
                    filterAssignments();
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }

    private void handleSubmit(AssignmentDTO assignment) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Assignment File");
        fileChooser.getExtensionFilters().addAll(
            new FileChooser.ExtensionFilter("All Files", "*.*"),
            new FileChooser.ExtensionFilter("PDF Documents", "*.pdf"),
            new FileChooser.ExtensionFilter("Word Documents", "*.docx")
        );
        
        File selectedFile = fileChooser.showOpenDialog(assignmentsContainer.getScene().getWindow());
        
        if (selectedFile != null) {
            new Thread(() -> {
                try {
                    Long userId = SessionManager.getInstance().getUserId();
                    submissionService.create(assignment.getId(), userId);
                    Platform.runLater(() -> {
                        AlertUtil.showSuccess("Success", "File uploaded: " + selectedFile.getName() + " and submitted successfully!");
                        loadAssignments();
                    });
                } catch (Exception e) {
                    Platform.runLater(() -> AlertUtil.showError("Submission Failed", e.getMessage()));
                    e.printStackTrace();
                }
            }).start();
        }
    }

    private void handleView(AssignmentDTO assignment) {
        AlertUtil.showInfo("Assignment Details", 
            "Title: " + assignment.getTitle() + "\n" +
            "Subject: " + assignment.getSubjectName() + "\n" +
            "Due Date: " + assignment.getDueDate() + "\n" +
            "Status: " + assignment.getStatus() + "\n" +
            "Description: " + assignment.getDescription());
    }
}
