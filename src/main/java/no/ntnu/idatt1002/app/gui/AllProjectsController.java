package no.ntnu.idatt1002.app.gui;

import java.io.IOException;
import java.util.ArrayList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import no.ntnu.idatt1002.app.BudgetAndAccountingApp;
import no.ntnu.idatt1002.app.data.Expense;
import no.ntnu.idatt1002.app.data.Project;
import no.ntnu.idatt1002.app.data.ProjectRegistry;
import no.ntnu.idatt1002.app.data.User;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicReference;

public class AllProjectsController {
  
  private ArrayList<Project> projects = new ArrayList<>();
  
  public void initializeWithData(ArrayList<Project> projects) {
    this.projects = projects;
    initialize();
  }
  
  @FXML
  private TableView<Project> table;
  @FXML
  private TableColumn<Project, String> name;
  @FXML
  private TableColumn<Project, Date> dateStart;


  @FXML
  private TableColumn<Project, String> plannedDone;

  @FXML
  private TableColumn<Project, String> category;

  @FXML
  private TableColumn<Project, Double> expense;

  @FXML
  private TableColumn<Project, Double> income;

  @FXML
  private Text showText;

  @FXML
  private Button editButton;

  @FXML
  private Button newProjectButton;

  public void initialize() {
    name.setCellValueFactory(new PropertyValueFactory<>("name"));
    dateStart.setCellValueFactory(new PropertyValueFactory<>("dueDate"));
    plannedDone.setCellValueFactory(new PropertyValueFactory<>("dueDate"));
    category.setCellValueFactory(new PropertyValueFactory<>("category"));
    expense.setCellValueFactory(new PropertyValueFactory<>("AccountingExpenses"));
    income.setCellValueFactory(new PropertyValueFactory<>(""));
  
    
    table.getItems().clear();
    table.getItems().addAll(projects);
    table.refresh();
  }


  public Project getSelectedProject() {
    AtomicReference<Project> selectedProject = new AtomicReference<>();

    table.setOnMouseClicked(mouseEvent -> {
      selectedProject.set(table.getSelectionModel().getSelectedItem());
      showText.setText("");
    });
    return selectedProject.get();
  }

  public void editButton() {
    try {
      Parent root = FXMLLoader.load(getClass().getResource("/EditProject.fxml"));
      BudgetAndAccountingApp.setRoot(root);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public void newProject() {
    try {
      Parent root = FXMLLoader.load(getClass().getResource("/NewProject.fxml"));
      BudgetAndAccountingApp.setRoot(root);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}

