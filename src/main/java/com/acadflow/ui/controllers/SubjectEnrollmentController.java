package com.acadflow.ui.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.layout.VBox;

import com.acadflow.ui.dto.SubjectDTO;
import com.acadflow.ui.services.SampleDataProvider;
import com.acadflow.ui.util.AlertUtil;

import java.util.List;

/**
 * Subject Enrollment Controller
 */
public class SubjectEnrollmentController {

    @FXML private VBox enrollmentContainer;
    @FXML private TableView<SubjectDTO> availableSubjectsTable;
    @FXML private TableColumn<SubjectDTO, String> codeColumn1;
    @FXML private TableColumn<SubjectDTO, String> nameColumn1;
    @FXML private TableColumn<SubjectDTO, String> instructorColumn1;
    @FXML private TableColumn<SubjectDTO, Integer> creditsColumn1;
    @FXML private TableColumn<SubjectDTO, Void> enrollColumn;

    @FXML private TableView<SubjectDTO> enrolledSubjectsTable;
    @FXML private TableColumn<SubjectDTO, String> codeColumn2;
    @FXML private TableColumn<SubjectDTO, String> nameColumn2;
    @FXML private TableColumn<SubjectDTO, Integer> totalClassesColumn;
    @FXML private TableColumn<SubjectDTO, Integer> attendedColumn;
    @FXML private TableColumn<SubjectDTO, Void> dropColumn;

    @FXML
    public void initialize() {
        setupAvailableSubjectsTable();
        setupEnrolledSubjectsTable();
        loadData();
    }

    private void setupAvailableSubjectsTable() {
        codeColumn1.setCellValueFactory(new PropertyValueFactory<>("code"));
        nameColumn1.setCellValueFactory(new PropertyValueFactory<>("name"));
        instructorColumn1.setCellValueFactory(new PropertyValueFactory<>("instructor"));
        creditsColumn1.setCellValueFactory(new PropertyValueFactory<>("credits"));

        enrollColumn.setCellFactory(param -> new javafx.scene.control.TableCell<SubjectDTO, Void>() {
            private final Button btn = new Button("Enroll");
            {
                btn.setStyle("-fx-padding: 5 15; -fx-background-color: #27ae60; -fx-text-fill: white;");
                btn.setOnAction(event -> {
                    SubjectDTO subject = getTableView().getItems().get(getIndex());
                    handleEnroll(subject);
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                setGraphic(empty ? null : btn);
            }
        });
    }

    private void setupEnrolledSubjectsTable() {
        codeColumn2.setCellValueFactory(new PropertyValueFactory<>("code"));
        nameColumn2.setCellValueFactory(new PropertyValueFactory<>("name"));
        totalClassesColumn.setCellValueFactory(new PropertyValueFactory<>("totalClasses"));
        attendedColumn.setCellValueFactory(new PropertyValueFactory<>("attendedClasses"));

        dropColumn.setCellFactory(param -> new javafx.scene.control.TableCell<SubjectDTO, Void>() {
            private final Button btn = new Button("Drop");
            {
                btn.setStyle("-fx-padding: 5 15; -fx-background-color: #e74c3c; -fx-text-fill: white;");
                btn.setOnAction(event -> {
                    SubjectDTO subject = getTableView().getItems().get(getIndex());
                    handleDrop(subject);
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                setGraphic(empty ? null : btn);
            }
        });
    }

    private void loadData() {
        List<SubjectDTO> allSubjects = SampleDataProvider.getSampleSubjects();
        List<SubjectDTO> enrolledSubjects = SampleDataProvider.getSampleEnrolledSubjects();

        ObservableList<SubjectDTO> availableSubjects = FXCollections.observableArrayList(
            allSubjects.stream().filter(s -> !enrolledSubjects.contains(s)).toList()
        );

        ObservableList<SubjectDTO> enrolled = FXCollections.observableArrayList(enrolledSubjects);

        availableSubjectsTable.setItems(availableSubjects);
        enrolledSubjectsTable.setItems(enrolled);
    }

    private void handleEnroll(SubjectDTO subject) {
        AlertUtil.showSuccess("Enrolled", "Successfully enrolled in " + subject.getName());
        // In real implementation, call API
        ObservableList<SubjectDTO> available = availableSubjectsTable.getItems();
        ObservableList<SubjectDTO> enrolled = enrolledSubjectsTable.getItems();
        
        available.remove(subject);
        enrolled.add(subject);
    }

    private void handleDrop(SubjectDTO subject) {
        AlertUtil.showSuccess("Dropped", "Successfully dropped " + subject.getName());
        // In real implementation, call API
        ObservableList<SubjectDTO> available = availableSubjectsTable.getItems();
        ObservableList<SubjectDTO> enrolled = enrolledSubjectsTable.getItems();
        
        enrolled.remove(subject);
        available.add(subject);
    }
}
