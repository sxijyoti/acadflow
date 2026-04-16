package com.acadflow.ui.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import com.acadflow.ui.dto.TimetableSlotDTO;
import com.acadflow.ui.services.SampleDataProvider;

import java.time.LocalTime;
import java.util.List;

/**
 * Timetable Controller - displays weekly grid schedule
 */
import org.springframework.stereotype.Component;
import org.springframework.context.annotation.Scope;
import org.springframework.beans.factory.annotation.Autowired;

import com.acadflow.module4.service.TimetableService;
import com.acadflow.module4.dto.TimetableCreateDTO;
import com.acadflow.module4.entity.DayOfWeek;
import com.acadflow.ui.util.SessionManager;
import com.acadflow.ui.util.AlertUtil;

@Component("uiTimetableController")
@Scope("prototype")
public class TimetableController {

    @FXML private VBox timetableContainer;
    @FXML private GridPane timetableGrid;

    @Autowired private TimetableService timetableService;

    @FXML
    public void initialize() {
        renderTimetable();

        if ("ADMIN".equals(SessionManager.getInstance().getUserRole())) {
            javafx.scene.control.Button addBtn = new javafx.scene.control.Button("Add Timetable Slot");
            addBtn.setStyle("-fx-padding: 5 15; -fx-background-color: #2980b9; -fx-text-fill: white;");
            addBtn.setOnAction(e -> {
                javafx.scene.control.TextInputDialog dialog = new javafx.scene.control.TextInputDialog();
                dialog.setTitle("Add Timetable Slot");
                dialog.setHeaderText("Enter Subject ID and Day (e.g. 1,MONDAY)");
                dialog.showAndWait().ifPresent(result -> {
                    String[] parts = result.split(",");
                    if (parts.length >= 2) {
                        try {
                            TimetableCreateDTO dto = new TimetableCreateDTO();
                            dto.setSubjectId(Long.parseLong(parts[0].trim()));
                            dto.setDay(DayOfWeek.valueOf(parts[1].trim().toUpperCase()));
                            dto.setStartTime(LocalTime.of(9, 0));
                            dto.setEndTime(LocalTime.of(10, 30));
                            dto.setLocation("Room 101");
                            timetableService.createTimetableSlot(dto);
                            AlertUtil.showSuccess("Success", "Timetable slot added successfully!");
                            // Can't refresh sample data grid, but we notify user
                            renderTimetable();
                        } catch(Exception ex) {
                            AlertUtil.showError("Error", ex.getMessage());
                        }
                    }
                });
            });
            timetableContainer.getChildren().add(0, addBtn);
        }
    }

    private void renderTimetable() {
        List<TimetableSlotDTO> slots = SampleDataProvider.getSampleTimetable();
        
        String[] days = {"MON", "TUE", "WED", "THU", "FRI"};
        LocalTime[] timeSlots = {
            LocalTime.of(9, 0),
            LocalTime.of(10, 30),
            LocalTime.of(14, 0)
        };

        // Add day headers
        for (int i = 0; i < days.length; i++) {
            Label dayLabel = new Label(days[i]);
            dayLabel.setStyle("-fx-font-weight: bold; -fx-alignment: CENTER; -fx-padding: 10;");
            timetableGrid.add(dayLabel, i + 1, 0);
        }

        // Add time slots in first column
        for (int i = 0; i < timeSlots.length; i++) {
            Label timeLabel = new Label(timeSlots[i].toString());
            timeLabel.setStyle("-fx-font-weight: bold; -fx-alignment: CENTER; -fx-padding: 10;");
            timetableGrid.add(timeLabel, 0, i + 1);
        }

        // Fill in classes
        for (TimetableSlotDTO slot : slots) {
            int dayIndex = getDayIndex(slot.getDayOfWeek());
            int timeIndex = getTimeIndex(slot.getStartTime());

            if (dayIndex >= 0 && timeIndex >= 0) {
                VBox cellContent = createClassCell(slot);
                timetableGrid.add(cellContent, dayIndex + 1, timeIndex + 1);
            }
        }
    }

    private VBox createClassCell(TimetableSlotDTO slot) {
        VBox cell = new VBox(4);
        cell.setStyle("-fx-border-color: #3498db; -fx-border-width: 2; -fx-padding: 8; " +
                     "-fx-background-color: #e8f4f8; -fx-border-radius: 4;");
        cell.setPrefWidth(120);
        cell.setPrefHeight(60);

        Label subjectLabel = new Label(slot.getSubjectCode());
        subjectLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 12;");
        
        Label nameLabel = new Label(slot.getSubjectName());
        nameLabel.setStyle("-fx-font-size: 10; -fx-text-fill: #666;");
        
        Label timeLabel = new Label(slot.getStartTime() + "-" + slot.getEndTime());
        timeLabel.setStyle("-fx-font-size: 9; -fx-text-fill: #999;");

        cell.getChildren().addAll(subjectLabel, nameLabel, timeLabel);
        return cell;
    }

    private int getDayIndex(String day) {
        String[] days = {"MONDAY", "TUESDAY", "WEDNESDAY", "THURSDAY", "FRIDAY"};
        for (int i = 0; i < days.length; i++) {
            if (days[i].equals(day)) return i;
        }
        return -1;
    }

    private int getTimeIndex(LocalTime time) {
        if (time.equals(LocalTime.of(9, 0))) return 0;
        if (time.equals(LocalTime.of(10, 30))) return 1;
        if (time.equals(LocalTime.of(14, 0))) return 2;
        return -1;
    }
}
