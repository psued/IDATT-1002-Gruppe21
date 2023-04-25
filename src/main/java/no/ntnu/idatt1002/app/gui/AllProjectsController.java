package no.ntnu.idatt1002.app.gui;

import java.util.Date;
import java.util.Objects;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import no.ntnu.idatt1002.app.BudgetAndAccountingApp;
import no.ntnu.idatt1002.app.User;
import no.ntnu.idatt1002.app.registers.Project;

/**
 * FXML Controller class for the AllProjects.fxml file. Displays all projects in the project
 * registry in a table and allows the user to create a new project, view an existing project and
 * edit an existing project
 */
public class AllProjectsController {
  
  @FXML private TableView<Project> table;
  @FXML private TableColumn<Project, String> name;
  @FXML private TableColumn<Project, Date> dueDate;
  @FXML private TableColumn<Project, String> category;
  @FXML private TableColumn<Project, Double> totalAccounting;

  @FXML private Label warningLabel;
  
  /**
   * Sets up the table containing all relevant projects by loading from the serialized user.
   */
  public void initialize() {
    warningLabel.setVisible(false);
    
    name.setCellValueFactory(new PropertyValueFactory<>("name"));
    dueDate.setCellValueFactory(new PropertyValueFactory<>("dueDate"));
    category.setCellValueFactory(new PropertyValueFactory<>("category"));
    totalAccounting.setCellValueFactory(new PropertyValueFactory<>("accountingTotal"));
    
    table.getItems().clear();
    if (User.getInstance().getProjectRegistry().getProjects() != null) {
      table.getItems().addAll(User.getInstance().getProjectRegistry().getProjects());
    }
    table.refresh();

    table.setRowFactory(tv -> new TableRow<>() {
      @Override
      public void updateItem(Project project, boolean empty) {
        super.updateItem(project, empty);
        if (project == null || empty) {
          setStyle("");
        } else {
          switch (project.getStatus()) {
            case "Not started" -> setStyle("-fx-background-color: #ff5e5e;");
            case "Doing" -> setStyle("-fx-background-color: orange;");
            case "Finished" -> setStyle("-fx-background-color: #77dd77");
            default -> setStyle("");
          }
        }
      }
    });
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
    } catch (Exception e) {
      setWarning(e.getMessage());
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
    } catch (Exception e){
      setWarning(e.getMessage());
    }
  }

  public void monthly() {
    try {
      Parent root = FXMLLoader.load(
        Objects.requireNonNull(getClass().getResource("/MonthlyOverview.fxml")));
      BudgetAndAccountingApp.setRoot(root);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public void start() {
    try {
      Parent root = FXMLLoader.load(
        Objects.requireNonNull(getClass().getResource("/Start.fxml")));
      BudgetAndAccountingApp.setRoot(root);
    } catch (IOException e) {
      e.printStackTrace();
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
    } catch (Exception e) {
      setWarning(e.getMessage());
    }
  }
  
  /**
   * Sets the warning label to display the given warning.
   *
   * @param warning The warning to display.
   */
  private void setWarning(String warning) {
    warningLabel.setText(warning);
    warningLabel.setVisible(true);
  }

  public void switchTheme() {
    BudgetAndAccountingApp.setTheme();
  }
}

