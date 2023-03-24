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
  
  private ProjectRegistry projectRegistry;
  
  public void initializeWithData(ProjectRegistry projectRegistry) {
    this.projectRegistry = projectRegistry;
    initializeController();
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
  private Text errorMessage;
  

  public void initializeController() {
    errorMessage.setVisible(false);
    
    name.setCellValueFactory(new PropertyValueFactory<>("name"));
    dateStart.setCellValueFactory(new PropertyValueFactory<>("dueDate"));
    plannedDone.setCellValueFactory(new PropertyValueFactory<>("dueDate"));
    category.setCellValueFactory(new PropertyValueFactory<>("category"));
    expense.setCellValueFactory(new PropertyValueFactory<>("AccountingExpenses"));
    income.setCellValueFactory(new PropertyValueFactory<>(""));
  
    
    table.getItems().clear();
    if (projectRegistry != null) {
      table.getItems().addAll(projectRegistry.getProjects());
    }
    table.refresh();
  }

  public void editProject() {
    Project selectedProject = table.getSelectionModel().getSelectedItem();
    try {
      FXMLLoader loader = new FXMLLoader(getClass().getResource("/EditProject.fxml"));
      Parent root = loader.load();
      
      EditProjectController controller = loader.getController();
      controller.initializeWithData(selectedProject,
          (ArrayList<String>)projectRegistry.getCategories());
      
      BudgetAndAccountingApp.setRoot(root);
    } catch (IOException e) {
      e.printStackTrace();
    } catch (NullPointerException e) {
      errorMessage.setText("Please select a project");
      errorMessage.setVisible(true);
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

