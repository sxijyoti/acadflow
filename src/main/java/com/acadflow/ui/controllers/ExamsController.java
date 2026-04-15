package com.acadflow.ui.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.layout.VBox;

import com.acadflow.ui.dto.ExamDTO;
import com.acadflow.ui.services.SampleDataProvider;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Exams Controller
 */
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

    @FXML
    public void initialize() {
        setupTable();
        setupSortFilter();
        loadExams();
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
        allExams = FXCollections.observableArrayList(
            SampleDataProvider.getSampleExams()
        );
        examsTable.setItems(allExams);
    }
}
