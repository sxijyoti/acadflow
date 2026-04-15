package com.acadflow.ui.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.layout.VBox;

import com.acadflow.ui.dto.HolidayDTO;
import com.acadflow.ui.services.SampleDataProvider;

import java.time.LocalDate;

/**
 * Holidays Controller
 */
public class HolidaysController {

    @FXML private VBox holidaysContainer;
    @FXML private TableView<HolidayDTO> holidaysTable;
    @FXML private TableColumn<HolidayDTO, String> nameColumn;
    @FXML private TableColumn<HolidayDTO, LocalDate> dateColumn;
    @FXML private TableColumn<HolidayDTO, String> categoryColumn;
    @FXML private TableColumn<HolidayDTO, String> descriptionColumn;

    @FXML
    public void initialize() {
        setupTable();
        loadHolidays();
    }

    private void setupTable() {
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        categoryColumn.setCellValueFactory(new PropertyValueFactory<>("category"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
    }

    private void loadHolidays() {
        ObservableList<HolidayDTO> holidays = FXCollections.observableArrayList(
            SampleDataProvider.getSampleHolidays()
        );
        holidaysTable.setItems(holidays);
    }
}
