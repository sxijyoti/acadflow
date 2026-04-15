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
public class TimetableController {

    @FXML private VBox timetableContainer;
    @FXML private GridPane timetableGrid;

    @FXML
    public void initialize() {
        renderTimetable();
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
