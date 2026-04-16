package com.acadflow.ui.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.layout.VBox;

import com.acadflow.ui.dto.ResourceDTO;
import com.acadflow.ui.services.SampleDataProvider;
import com.acadflow.ui.util.AlertUtil;

import java.util.List;

/**
 * Resources/Syllabus Controller
 */
import org.springframework.stereotype.Component;
import org.springframework.context.annotation.Scope;

@Component("uiResourcesController")
@Scope("prototype")
public class ResourcesController {

    @FXML private VBox resourcesContainer;
    @FXML private ComboBox<String> subjectCombo;
    @FXML private TableView<ResourceDTO> resourcesTable;
    @FXML private TableColumn<ResourceDTO, String> nameColumn;
    @FXML private TableColumn<ResourceDTO, String> typeColumn;
    @FXML private TableColumn<ResourceDTO, String> descriptionColumn;
    @FXML private TableColumn<ResourceDTO, Void> actionColumn;

    @FXML
    public void initialize() {
        setupSubjectCombo();
        setupResourceTable();
        loadResources("CS101");
    }

    private void setupSubjectCombo() {
        ObservableList<String> subjects = FXCollections.observableArrayList(
            "CS101 - Data Structures",
            "CS102 - Algorithms",
            "CS201 - Database Systems",
            "CS202 - Web Development",
            "MATH101 - Calculus"
        );
        subjectCombo.setItems(subjects);
        subjectCombo.setValue("CS101 - Data Structures");
        subjectCombo.setOnAction(e -> {
            String selected = subjectCombo.getValue();
            String subjectCode = selected.split(" - ")[0];
            loadResources(subjectCode);
        });
    }

    private void setupResourceTable() {
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("resourceName"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("resourceType"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));

        actionColumn.setCellFactory(param -> new javafx.scene.control.TableCell<ResourceDTO, Void>() {
            private final Button downloadBtn = new Button("Download");
            private final Button openBtn = new Button("Open");
            {
                downloadBtn.setStyle("-fx-padding: 5 10; -fx-background-color: #27ae60; -fx-text-fill: white;");
                openBtn.setStyle("-fx-padding: 5 10; -fx-background-color: #3498db; -fx-text-fill: white; -fx-margin-left: 5;");
                
                downloadBtn.setOnAction(event -> {
                    ResourceDTO resource = getTableView().getItems().get(getIndex());
                    AlertUtil.showInfo("Download", "Downloaded: " + resource.getResourceName());
                });
                
                openBtn.setOnAction(event -> {
                    ResourceDTO resource = getTableView().getItems().get(getIndex());
                    AlertUtil.showInfo("Open", "Opening: " + resource.getURL());
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    javafx.scene.layout.HBox hbox = new javafx.scene.layout.HBox(5);
                    hbox.getChildren().addAll(downloadBtn, openBtn);
                    setGraphic(hbox);
                }
            }
        });
    }

    private void loadResources(String subjectCode) {
        List<ResourceDTO> allResources = SampleDataProvider.getSampleResources();
        ObservableList<ResourceDTO> filtered = FXCollections.observableArrayList(
            allResources.stream().filter(r -> r.getSubjectCode().equals(subjectCode)).toList()
        );
        resourcesTable.setItems(filtered);
    }
}
