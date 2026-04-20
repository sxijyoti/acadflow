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
        
        if ("ADMIN".equals(com.acadflow.ui.util.SessionManager.getInstance().getUserRole())) {
            javafx.scene.control.Button addBtn = new javafx.scene.control.Button("Add Holiday");
            addBtn.setStyle("-fx-padding: 5 15; -fx-background-color: #2980b9; -fx-text-fill: white;");
            addBtn.setOnAction(e -> {
                javafx.scene.control.TextInputDialog dialog = new javafx.scene.control.TextInputDialog();
                dialog.setTitle("Add Holiday");
                dialog.setHeaderText("Enter Holiday Name");
                dialog.showAndWait().ifPresent(result -> {
                    com.acadflow.module3.entity.Holiday h = new com.acadflow.module3.entity.Holiday();
                    h.setName(result.trim());
                    h.setDate(LocalDate.now().plusDays(1));
                    h.setType("PUBLIC");
                    holidayService.createHoliday(h);
                    com.acadflow.ui.util.AlertUtil.showSuccess("Success", "Holiday added successfully!");
                    loadHolidays();
                });
            });
            holidaysContainer.getChildren().add(0, addBtn);
        }
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
