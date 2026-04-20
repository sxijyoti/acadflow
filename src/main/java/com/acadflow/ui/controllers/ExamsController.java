package com.acadflow.ui.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.layout.VBox;
import javafx.application.Platform;

import com.acadflow.ui.dto.ExamDTO;
import com.acadflow.module4.service.ExamService;
import com.acadflow.module4.dto.ExamResponseDTO;
import com.acadflow.module1.service.EnrollmentService;
import com.acadflow.module1.entity.Enrollment;
import com.acadflow.ui.util.SessionManager;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.ArrayList;

/**
 * Exams Controller
 */
import org.springframework.stereotype.Component;
import org.springframework.context.annotation.Scope;
import org.springframework.beans.factory.annotation.Autowired;

@Component("uiExamsController")
@Scope("prototype")
public class ExamsController {

    @FXML private VBox examsContainer;
    @FXML private ComboBox<String> sortFilter;
    @FXML private TableView<ExamDTO> examsTable;
    @FXML private TableColumn<ExamDTO, String> subjectColumn;
    @FXML private TableColumn<ExamDTO, LocalDate> dateColumn;
    @FXML private TableColumn<ExamDTO, LocalTime> timeColumn;
    @FXML private TableColumn<ExamDTO, String> typeColumn;
    @FXML private TableColumn<ExamDTO, String> locationColumn;
    private ObservableList<ExamDTO> allExams;

    @Autowired private ExamService examService;
    @Autowired private EnrollmentService enrollmentService;

    @FXML
    public void initialize() {
        setupTable();
        setupSortFilter();
        loadExams();
        
        if ("ADMIN".equals(SessionManager.getInstance().getUserRole())) {
            javafx.scene.control.Button addBtn = new javafx.scene.control.Button("Add Exam");
            addBtn.setStyle("-fx-padding: 5 15; -fx-background-color: #2980b9; -fx-text-fill: white;");
            addBtn.setOnAction(e -> {
                javafx.scene.control.TextInputDialog dialog = new javafx.scene.control.TextInputDialog();
                dialog.setTitle("Add Exam");
                dialog.setHeaderText("Enter Subject ID and Exam Type (e.g. 1,MIDTERM)");
                dialog.showAndWait().ifPresent(result -> {
                    String[] parts = result.split(",");
                    if (parts.length >= 2) {
                        try {
                            com.acadflow.module4.dto.ExamCreateDTO dto = new com.acadflow.module4.dto.ExamCreateDTO();
                            dto.setSubjectId(Long.parseLong(parts[0].trim()));
                            dto.setType(parts[1].trim());
                            dto.setDate(java.time.LocalDateTime.now().plusDays(7));
                            dto.setLocation("Main Hall");
                            examService.createExam(dto);
                            com.acadflow.ui.util.AlertUtil.showSuccess("Success", "Exam added successfully!");
                            loadExams();
                        } catch(Exception ex) {
                            com.acadflow.ui.util.AlertUtil.showError("Error", ex.getMessage());
                        }
                    }
                });
            });
            examsContainer.getChildren().add(0, addBtn);
        }
    }

    
    private void setupSortFilter() {
        sortFilter.setItems(FXCollections.observableArrayList(
            "Date", "Subject", "Type"
        ));
        sortFilter.setValue("Date");
        sortFilter.setOnAction(e -> sortExams());
    }
    
    private void sortExams() {
        String sortBy = sortFilter.getValue();
        ObservableList<ExamDTO> sorted = FXCollections.observableArrayList(allExams);
        
        if ("Subject".equals(sortBy)) {
            sorted.sort((e1, e2) -> e1.getSubjectName().compareTo(e2.getSubjectName()));
        } else if ("Type".equals(sortBy)) {
            sorted.sort((e1, e2) -> e1.getType().compareTo(e2.getType()));
        } else {
            sorted.sort((e1, e2) -> e1.getExamDate().compareTo(e2.getExamDate()));
        }
        
        examsTable.setItems(sorted);
    }

    private void setupTable() {
        subjectColumn.setCellValueFactory(new PropertyValueFactory<>("subjectName"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("examDate"));
        timeColumn.setCellValueFactory(new PropertyValueFactory<>("startTime"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        locationColumn.setCellValueFactory(new PropertyValueFactory<>("location"));
    }

    private void loadExams() {
        allExams = FXCollections.observableArrayList();
        examsTable.setItems(allExams);

        new Thread(() -> {
            try {
                Long userId = SessionManager.getInstance().getUserId();
                List<Enrollment> enrollments = enrollmentService.getEnrolledSubjects(userId);
                List<ExamDTO> fetchedExams = new ArrayList<>();
                for (Enrollment enroll : enrollments) {
                    var exams = examService.getExamsForSubject(enroll.getSubject().getId());
                    if (exams != null) {
                        for (ExamResponseDTO ext : exams) {
                            ExamDTO edto = new ExamDTO(
                                ext.getId(),
                                enroll.getSubject().getCode(),
                                enroll.getSubject().getName(),
                                ext.getDate().toLocalDate(),
                                ext.getType(),
                                ext.getLocation()
                            );
                            edto.setStartTime(ext.getDate().toLocalTime());
                            fetchedExams.add(edto);
                        }
                    }
                }
                Platform.runLater(() -> {
                    allExams.setAll(fetchedExams);
                    sortExams();
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }
}
