package com.acadflow.ui.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.layout.VBox;
import javafx.application.Platform;

import com.acadflow.ui.dto.SubjectDTO;
import com.acadflow.ui.util.AlertUtil;
import com.acadflow.ui.util.SessionManager;
import com.acadflow.module1.entity.Subject;
import com.acadflow.module1.entity.Enrollment;
import com.acadflow.module1.entity.EnrollmentStatus;
import com.acadflow.module1.service.EnrollmentService;
import com.acadflow.module1.repository.SubjectRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.context.annotation.Scope;

import java.util.List;
import java.util.stream.Collectors;
import java.util.Objects;

@Component("uiSubjectEnrollmentController")
@Scope("prototype")
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

    @Autowired
    private EnrollmentService enrollmentService;

    @Autowired
    private SubjectRepository subjectRepository;

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

    private SubjectDTO mapToDTO(Subject s) {
        String instructorName = (s.getInstructor() != null) 
            ? s.getInstructor().getFirstName() + " " + s.getInstructor().getLastName() 
            : "TBA";
        return new SubjectDTO(s.getId(), s.getCode(), s.getName(), "", instructorName, s.getCredits() == null ? 0 : s.getCredits());
    }

    private void loadData() {
        new Thread(() -> {
            try {
                Long userId = SessionManager.getInstance().getUserId();
                List<Subject> allSubjects = subjectRepository.findAll();
                List<Enrollment> enrollments = enrollmentService.getEnrolledSubjects(userId);

                // Filter enrollments to only show those active (ENROLLED)
                List<Enrollment> activeEnrollments = enrollments.stream()
                        .filter(e -> e.getStatus() == EnrollmentStatus.ENROLLED)
                        .collect(Collectors.toList());

                List<Long> enrolledSubjectIds = activeEnrollments.stream()
                        .map(e -> e.getSubject().getId())
                        .collect(Collectors.toList());

                List<SubjectDTO> availableDTOs = allSubjects.stream()
                        .filter(s -> !enrolledSubjectIds.contains(s.getId()))
                        .map(this::mapToDTO)
                        .collect(Collectors.toList());

                List<SubjectDTO> enrolledDTOs = activeEnrollments.stream()
                        .map(e -> {
                            SubjectDTO dto = mapToDTO(e.getSubject());
                            // Since we don't have attendance module yet, default to 0
                            dto.setTotalClasses(0);
                            dto.setAttendedClasses(0);
                            return dto;
                        })
                        .collect(Collectors.toList());

                Platform.runLater(() -> {
                    availableSubjectsTable.setItems(FXCollections.observableArrayList(availableDTOs));
                    enrolledSubjectsTable.setItems(FXCollections.observableArrayList(enrolledDTOs));
                });
            } catch (Exception e) {
                e.printStackTrace();
                Platform.runLater(() -> AlertUtil.showError("Error", "Could not load subjects"));
            }
        }).start();
    }

    private void handleEnroll(SubjectDTO subject) {
        new Thread(() -> {
            try {
                Long userId = SessionManager.getInstance().getUserId();
                enrollmentService.enroll(userId, subject.getId());

                Platform.runLater(() -> {
                    AlertUtil.showSuccess("Enrolled", "Successfully enrolled in " + subject.getName());
                    loadData();
                });
            } catch (Exception e) {
                e.printStackTrace();
                Platform.runLater(() -> AlertUtil.showError("Error", "Could not enroll: " + e.getMessage()));
            }
        }).start();
    }

    private void handleDrop(SubjectDTO subject) {
        new Thread(() -> {
            try {
                Long userId = SessionManager.getInstance().getUserId();
                enrollmentService.drop(userId, subject.getId());

                Platform.runLater(() -> {
                    AlertUtil.showSuccess("Dropped", "Successfully dropped " + subject.getName());
                    loadData();
                });
            } catch (Exception e) {
                e.printStackTrace();
                Platform.runLater(() -> AlertUtil.showError("Error", "Could not drop: " + e.getMessage()));
            }
        }).start();
    }
}
