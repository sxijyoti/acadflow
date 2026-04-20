package com.acadflow.ui.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.application.Platform;

import com.acadflow.ui.dto.EventDTO;
import com.acadflow.ui.util.AlertUtil;
import com.acadflow.ui.util.SessionManager;
import com.acadflow.module1.entity.Enrollment;
import com.acadflow.module1.service.EnrollmentService;
import com.acadflow.module2.service.AssignmentService;
import com.acadflow.module2.entity.Assignment;
import com.acadflow.module3.entity.Holiday;
import com.acadflow.module3.service.HolidayService;
import com.acadflow.module4.dto.ExamResponseDTO;
import com.acadflow.module4.service.ExamService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.context.annotation.Scope;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component("uiCalendarController")
@Scope("prototype")
public class CalendarController {

    @FXML private VBox calendarContainer;
    @FXML private Label monthYearLabel;
    @FXML private GridPane calendarGrid;
    @FXML private ComboBox<String> filterCombo;
    @FXML private Button previousBtn;
    @FXML private Button nextBtn;
    @FXML private VBox eventsList;

    @Autowired
    private AssignmentService assignmentService;

    @Autowired
    private EnrollmentService enrollmentService;

    @Autowired
    private ExamService examService;

    @Autowired
    private HolidayService holidayService;

    private YearMonth currentMonth;
    private List<EventDTO> allEvents;
    private Map<String, String> eventColorMap;

    @FXML
    public void initialize() {
        allEvents = new ArrayList<>();
        currentMonth = YearMonth.now();
        setupEventColorMap();
        setupFilterCombo();
        setupNavigationButtons();
        loadEvents();
    }

    private void loadEvents() {
        new Thread(() -> {
            try {
                Long userId = SessionManager.getInstance().getUserId();
                List<EventDTO> dynamicEvents = new ArrayList<>();
                
                // 1. Fetch Assignments
                List<Assignment> assignments = assignmentService.getAssignmentsForStudent(userId);
                for (Assignment a : assignments) {
                    if (a.getDeadline() != null) {
                        EventDTO ev = new EventDTO();
                        ev.setTitle(a.getTitle() + " (" + a.getSubject().getCode() + ")");
                        ev.setDate(a.getDeadline().toLocalDate().toString());
                        ev.setEventType("ASSIGNMENT");
                        ev.setColor(eventColorMap.get("ASSIGNMENT"));
                        dynamicEvents.add(ev);
                    }
                }
                
                // 2. Fetch Exams for enrolled subjects
                List<Enrollment> enrollments = enrollmentService.getEnrolledSubjects(userId);
                for (Enrollment enroll : enrollments) {
                    Long subjectId = enroll.getSubject().getId();
                    List<ExamResponseDTO> exams = examService.getExamsForSubject(subjectId);
                    for (ExamResponseDTO exam : exams) {
                        if (exam.getDate() != null) {
                            EventDTO ev = new EventDTO();
                            ev.setTitle(exam.getType() + " Exam (" + enroll.getSubject().getCode() + ")");
                            ev.setDate(exam.getDate().toLocalDate().toString());
                            ev.setEventType("EXAM");
                            ev.setColor(eventColorMap.get("EXAM"));
                            dynamicEvents.add(ev);
                        }
                    }
                }
                
                // 3. Fetch Holidays
                List<Holiday> holidays = holidayService.getAllHolidays();
                for (Holiday holiday : holidays) {
                    if (holiday.getDate() != null) {
                        EventDTO ev = new EventDTO();
                        ev.setTitle(holiday.getName());
                        ev.setDate(holiday.getDate().toString());
                        ev.setEventType("HOLIDAY");
                        ev.setColor(eventColorMap.get("HOLIDAY"));
                        dynamicEvents.add(ev);
                    }
                }
                
                Platform.runLater(() -> {
                    allEvents.clear();
                    allEvents.addAll(dynamicEvents);
                    renderCalendar();
                    showAllCurrentMonthEvents();
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
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
            showAllCurrentMonthEvents();
        });
        nextBtn.setOnAction(e -> {
            currentMonth = currentMonth.plusMonths(1);
            renderCalendar();
            showAllCurrentMonthEvents();
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
                addEventToSidebar(event);
            }
        }
    }

    private void showAllCurrentMonthEvents() {
        eventsList.getChildren().clear();
        
        Label dateLabel = new Label("Events in " + currentMonth.format(java.time.format.DateTimeFormatter.ofPattern("MMMM yyyy")));
        dateLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 14; -fx-padding: 0 0 10 0;");
        eventsList.getChildren().add(dateLabel);

        List<EventDTO> monthEvents = allEvents.stream()
                .filter(ev -> LocalDate.parse(ev.getDate()).getYear() == currentMonth.getYear() &&
                              LocalDate.parse(ev.getDate()).getMonthValue() == currentMonth.getMonthValue())
                .sorted((e1, e2) -> LocalDate.parse(e1.getDate()).compareTo(LocalDate.parse(e2.getDate())))
                .toList();

        if (monthEvents.isEmpty()) {
            Label noEventsLabel = new Label("No events this month");
            noEventsLabel.setStyle("-fx-text-fill: #999; -fx-padding: 10;");
            eventsList.getChildren().add(noEventsLabel);
        } else {
            for (EventDTO event : monthEvents) {
                addEventToSidebar(event);
            }
        }
    }

    private void addEventToSidebar(EventDTO event) {
        HBox eventBox = new HBox(10);
        eventBox.setStyle("-fx-padding: 8; -fx-border-color: #eee; -fx-border-width: 0 0 1 0;");
        
        Label colorIndicator = new Label("●");
        colorIndicator.setStyle("-fx-text-fill: " + event.getColor() + "; -fx-font-size: 16;");
        
        VBox eventInfo = new VBox(2);
        Label eventTitle = new Label(event.getTitle());
        eventTitle.setStyle("-fx-font-weight: bold;");
        Label eventTypeLabel = new Label(event.getEventType() + " - " + event.getDate());
        eventTypeLabel.setStyle("-fx-font-size: 11; -fx-text-fill: #666;");
        eventInfo.getChildren().addAll(eventTitle, eventTypeLabel);
        
        HBox.setHgrow(eventInfo, javafx.scene.layout.Priority.ALWAYS);
        eventBox.getChildren().addAll(colorIndicator, eventInfo);
        eventsList.getChildren().add(eventBox);
    }
}
