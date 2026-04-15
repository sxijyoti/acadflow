package com.acadflow.ui.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.geometry.Pos;

import com.acadflow.ui.dto.EventDTO;
import com.acadflow.ui.services.SampleDataProvider;
import com.acadflow.ui.util.AlertUtil;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Calendar Controller - displays monthly calendar with event highlights
 */
public class CalendarController {

    @FXML private VBox calendarContainer;
    @FXML private Label monthYearLabel;
    @FXML private GridPane calendarGrid;
    @FXML private ComboBox<String> filterCombo;
    @FXML private Button previousBtn;
    @FXML private Button nextBtn;
    @FXML private VBox eventsList;

    private YearMonth currentMonth;
    private List<EventDTO> allEvents;
    private Map<String, String> eventColorMap;

    @FXML
    public void initialize() {
        allEvents = SampleDataProvider.getSampleCalendarEvents();
        currentMonth = YearMonth.now();
        setupEventColorMap();
        setupFilterCombo();
        setupNavigationButtons();
        renderCalendar();
    }

    private void setupEventColorMap() {
        eventColorMap = new HashMap<>();
        eventColorMap.put("ASSIGNMENT", "#FFD700");
        eventColorMap.put("EXAM", "#FF6B6B");
        eventColorMap.put("HOLIDAY", "#90EE90");
        eventColorMap.put("CLASS", "#87CEEB");
    }

    private void setupFilterCombo() {
        filterCombo.getItems().addAll("All", "Assignments", "Exams", "Holidays");
        filterCombo.setValue("All");
        filterCombo.setOnAction(e -> renderCalendar());
    }

    private void setupNavigationButtons() {
        previousBtn.setOnAction(e -> {
            currentMonth = currentMonth.minusMonths(1);
            renderCalendar();
        });
        nextBtn.setOnAction(e -> {
            currentMonth = currentMonth.plusMonths(1);
            renderCalendar();
        });
    }

    private void renderCalendar() {
        monthYearLabel.setText(currentMonth.format(java.time.format.DateTimeFormatter.ofPattern("MMMM yyyy")));
        
        calendarGrid.getChildren().clear();
        
        // Add day headers
        String[] dayHeaders = {"Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"};
        for (int i = 0; i < 7; i++) {
            Label dayHeader = new Label(dayHeaders[i]);
            dayHeader.setStyle("-fx-font-weight: bold; -fx-alignment: CENTER; -fx-padding: 10;");
            calendarGrid.add(dayHeader, i, 0);
        }

        // Fill calendar with dates
        LocalDate firstDayOfMonth = currentMonth.atDay(1);
        LocalDate lastDayOfMonth = currentMonth.atEndOfMonth();
        int firstDayOfWeek = firstDayOfMonth.getDayOfWeek().getValue() % 7;
        int totalDays = (int) ChronoUnit.DAYS.between(firstDayOfMonth, lastDayOfMonth) + 1;

        int row = 1;
        int col = firstDayOfWeek;

        for (int day = 1; day <= totalDays; day++) {
            LocalDate date = LocalDate.of(currentMonth.getYear(), currentMonth.getMonth(), day);
            VBox dayCell = createDayCell(date);
            calendarGrid.add(dayCell, col, row);

            col++;
            if (col > 6) {
                col = 0;
                row++;
            }
        }
    }

    private VBox createDayCell(LocalDate date) {
        VBox cell = new VBox(2);
        cell.setPrefWidth(80);
        cell.setPrefHeight(80);
        cell.setStyle("-fx-border-color: #ddd; -fx-padding: 5; -fx-border-width: 1;");
        cell.setOnMouseClicked(e -> showDayEvents(date));

        Label dayLabel = new Label(String.valueOf(date.getDayOfMonth()));
        dayLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 14;");
        cell.getChildren().add(dayLabel);

        // Add event indicators
        String filter = filterCombo.getValue();
        List<EventDTO> dayEvents = allEvents.stream()
                .filter(ev -> ev.getDate().equals(date.toString()))
                .filter(ev -> "All".equals(filter) || 
                       filter.equals("Assignments") && "ASSIGNMENT".equals(ev.getEventType()) ||
                       filter.equals("Exams") && "EXAM".equals(ev.getEventType()) ||
                       filter.equals("Holidays") && "HOLIDAY".equals(ev.getEventType()))
                .toList();

        for (EventDTO event : dayEvents) {
            Label eventIndicator = new Label("●");
            eventIndicator.setStyle("-fx-text-fill: " + event.getColor() + "; -fx-font-size: 10;");
            cell.getChildren().add(eventIndicator);
        }

        return cell;
    }

    private void showDayEvents(LocalDate date) {
        eventsList.getChildren().clear();
        
        Label dateLabel = new Label(date.format(java.time.format.DateTimeFormatter.ofPattern("EEEE, MMMM dd, yyyy")));
        dateLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 14;");
        eventsList.getChildren().add(dateLabel);

        List<EventDTO> dayEvents = allEvents.stream()
                .filter(ev -> ev.getDate().equals(date.toString()))
                .toList();

        if (dayEvents.isEmpty()) {
            Label noEventsLabel = new Label("No events");
            noEventsLabel.setStyle("-fx-text-fill: #999; -fx-padding: 10;");
            eventsList.getChildren().add(noEventsLabel);
        } else {
            for (EventDTO event : dayEvents) {
                HBox eventBox = new HBox(10);
                eventBox.setStyle("-fx-padding: 8; -fx-border-color: #eee; -fx-border-width: 0 0 1 0;");
                
                Label colorIndicator = new Label("●");
                colorIndicator.setStyle("-fx-text-fill: " + event.getColor() + "; -fx-font-size: 16;");
                
                VBox eventInfo = new VBox(2);
                Label eventTitle = new Label(event.getTitle());
                eventTitle.setStyle("-fx-font-weight: bold;");
                Label eventTypeLabel = new Label(event.getEventType());
                eventTypeLabel.setStyle("-fx-font-size: 11; -fx-text-fill: #666;");
                eventInfo.getChildren().addAll(eventTitle, eventTypeLabel);
                
                HBox.setHgrow(eventInfo, javafx.scene.layout.Priority.ALWAYS);
                eventBox.getChildren().addAll(colorIndicator, eventInfo);
                eventsList.getChildren().add(eventBox);
            }
        }
    }
}
