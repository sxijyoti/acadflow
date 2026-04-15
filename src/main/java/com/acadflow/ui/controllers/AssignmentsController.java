package com.acadflow.ui.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.layout.VBox;

import com.acadflow.ui.dto.AssignmentDTO;
import com.acadflow.ui.services.SampleDataProvider;
import com.acadflow.ui.util.AlertUtil;

import java.time.LocalDate;

/**
 * Assignments Controller
 */
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

    @FXML
    public void initialize() {
        setupTable();
        setupStatusFilter();
        loadAssignments();
    }
    
    private void setupStatusFilter() {
        statusFilter.setItems(FXCollections.observableArrayList(
            "All", "Pending", "Submitted", "Late", "Graded"
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
                    javafx.scene.layout.HBox hbox = new javafx.scene.layout.HBox(5);
                    hbox.getChildren().addAll(submitBtn, viewBtn);
                    setGraphic(hbox);
                }
            }
        });
    }

    private void loadAssignments() {
        allAssignments = FXCollections.observableArrayList(
            SampleDataProvider.getSampleAssignments()
        );
        assignmentsTable.setItems(allAssignments);
    }

    private void handleSubmit(AssignmentDTO assignment) {
        AlertUtil.showInfo("Submit Assignment", "Submission feature would open file dialog in full implementation");
        // TODO: Open file picker and submit to API
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
