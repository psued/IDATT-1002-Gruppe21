package no.ntnu.idatt1002.app.gui;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import no.ntnu.idatt1002.app.data.Project;
import no.ntnu.idatt1002.app.data.User;
import no.ntnu.idatt1002.app.registry.ProjectRegistry;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicReference;

public class AllProjectsController {

  private User user = new User();

  private ProjectRegistry projectRegistry = user.getProjectRegistry();

  @FXML private TableView<Project> table;

  @FXML private TableColumn<Project, String> name;

  @FXML private TableColumn<Project, Date> dateStart;


  @FXML private TableColumn<Project, String> plannedDone;

  @FXML private TableColumn<Project, String> category;

  @FXML private TableColumn<Project, Integer> expense;

  @FXML private TableColumn<Project, Integer> income;



  @FXML private Button editButton;

  @FXML private Button newProjectButton;

  public void initialize() {
    name.setCellValueFactory(new PropertyValueFactory<>("name"));
    dateStart.setCellValueFactory(new PropertyValueFactory<>("creationDate"));
    plannedDone.setCellValueFactory(new PropertyValueFactory<>("crationDate"));
    category.setCellValueFactory(new PropertyValueFactory<>("category"));
    expense.setCellValueFactory(new PropertyValueFactory<>(""));
    income.setCellValueFactory(new PropertyValueFactory<>(""));
    setupTable();

    table.getItems().addAll(projectRegistry.getProjects());
    table.refresh();
  }


  public Project getSelectedProject() {
    AtomicReference<Project> selectedProject = new AtomicReference<>();

    table.setOnMouseClicked(mouseEvent -> {
      selectedProject.set(table.getSelectionModel().getSelectedItem());
    });
    return selectedProject.get();
  }


  public void setupTable(){
    Project project1 = new Project("Test", "Test", "Test");
    Project project2 = new Project("Test2", "Test2", "Test2");
    Project project3 = new Project("Test3", "Test3", "Test3");
    table.getItems().addAll(project1, project2, project3);
  }


}
