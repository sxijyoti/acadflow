package com.acadflow.ui.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.layout.VBox;
import javafx.application.Platform;

import com.acadflow.ui.dto.HolidayDTO;
import com.acadflow.module3.service.HolidayService;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Holidays Controller
 */
import org.springframework.stereotype.Component;
import org.springframework.context.annotation.Scope;
import org.springframework.beans.factory.annotation.Autowired;

@Component("uiHolidaysController")
@Scope("prototype")
public class HolidaysController {

    @FXML private VBox holidaysContainer;
    @FXML private TableView<HolidayDTO> holidaysTable;
    @FXML private TableColumn<HolidayDTO, String> nameColumn;
    @FXML private TableColumn<HolidayDTO, LocalDate> dateColumn;
    @FXML private TableColumn<HolidayDTO, String> categoryColumn;
    @FXML private TableColumn<HolidayDTO, String> descriptionColumn;

    @Autowired private HolidayService holidayService;

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
        new Thread(() -> {
            try {
                var holidays = holidayService.getAllHolidays();
                List<HolidayDTO> fetched = holidays.stream().map(h -> {
                    HolidayDTO dto = new HolidayDTO(h.getId(), h.getName(), h.getDate(), h.getType());
                    dto.setCategory(h.getType());
                    return dto;
                }).collect(Collectors.toList());
                Platform.runLater(() -> {
                    ObservableList<HolidayDTO> items = FXCollections.observableArrayList(fetched);
                    holidaysTable.setItems(items);
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }
}
