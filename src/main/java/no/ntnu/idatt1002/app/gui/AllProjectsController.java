package no.ntnu.idatt1002.app.gui;

import java.io.IOException;
import java.util.Date;
import java.util.Objects;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import no.ntnu.idatt1002.app.BudgetAndAccountingApp;
import no.ntnu.idatt1002.app.User;
import no.ntnu.idatt1002.app.filehandling.FileHandling;
import no.ntnu.idatt1002.app.registers.Project;

/**
 * FXML Controller class for the AllProjects.fxml file. Displays all projects in the project
 * registry in a table and allows the user to create a new project, view an existing project and
 * edit an existing project
 */
public class AllProjectsController {
  
  private User tempUser;
  
  @FXML private TableView<Project> table;
  @FXML private TableColumn<Project, String> name;
  @FXML private TableColumn<Project, Date> dueDate;
  @FXML private TableColumn<Project, String> category;
  @FXML private TableColumn<Project, Double> totalAccounting;

  @FXML private Text errorMessage;
  
  /**
   * Sets up the table containing all relevant projects by loading from the serialized user.
   */
  public void initialize() {
    try {
      tempUser = FileHandling.readUserFromFile();
    } catch (IOException e) {
      e.printStackTrace();
    } catch (ClassNotFoundException e) {
      throw new RuntimeException(e);
    }
  
    errorMessage.setVisible(false);
    
    name.setCellValueFactory(new PropertyValueFactory<>("name"));
    dueDate.setCellValueFactory(new PropertyValueFactory<>("dueDate"));
    category.setCellValueFactory(new PropertyValueFactory<>("category"));
    totalAccounting.setCellValueFactory(new PropertyValueFactory<>("accountingTotal"));
  
    
    table.getItems().clear();
    if (tempUser.getProjectRegistry().getProjects() != null) {
      table.getItems().addAll(tempUser.getProjectRegistry().getProjects());
    }
    table.refresh();
  }
  
  /**
   * Takes the chosen project and loads the edit project page by initializing it with the chosen
   * project. If no project is chosen, an error message is displayed.
   */
  public void editProject() {
    Project selectedProject = table.getSelectionModel().getSelectedItem();
    try {
      FXMLLoader loader = new FXMLLoader(getClass().getResource("/EditProject.fxml"));
      Parent root = loader.load();
      
      EditProjectController controller = loader.getController();
      controller.initializeWithData(selectedProject);
      
      BudgetAndAccountingApp.setRoot(root);
    } catch (IOException e) {
      e.printStackTrace();
    } catch (NullPointerException e) {
      errorMessage.setText("Please select a project to edit");
      errorMessage.setVisible(true);
    }
  }
  
  /**
   * Takes the chosen project and loads the view project page by initializing it with the chosen
   * project. If no project is chosen, an error message is displayed.
   */
  public void viewProject() {
    Project selectedProject = table.getSelectionModel().getSelectedItem();
    try {
      FXMLLoader loader = new FXMLLoader(getClass().getResource("/ViewProject.fxml"));
      Parent root = loader.load();
    
      ViewProjectController controller = loader.getController();
      controller.initializeWithData(selectedProject);
    
      BudgetAndAccountingApp.setRoot(root);
    } catch (IOException e) {
      e.printStackTrace();
    } catch (NullPointerException e) {
      errorMessage.setText(e.getMessage());
      errorMessage.setVisible(true);
    }
    
  }
  
  /**
   * Opens the new project page.
   */
  public void newProject() {
    try {
      Parent root = FXMLLoader.load(
          Objects.requireNonNull(getClass().getResource("/NewProject.fxml")));
      BudgetAndAccountingApp.setRoot(root);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}

