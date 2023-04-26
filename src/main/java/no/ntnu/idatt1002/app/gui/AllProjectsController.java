package no.ntnu.idatt1002.app.gui;

import java.io.IOException;
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
  //Sets up the table and its columns
  @FXML
  private TableView<Project> table;
  @FXML
  private TableColumn<Project, String> name;
  @FXML
  private TableColumn<Project, Date> dueDate;
  @FXML
  private TableColumn<Project, String> category;
  @FXML
  private TableColumn<Project, Double> totalAccounting;
  
  //Sets up the warning label
  @FXML
  private Label warningLabel = new Label();
  
  /**
   * Sets up the table containing all the projects in the project registry.
   *
   * <p>Makes each row in the table have a different color depending on the status of the project.
   */
  @FXML
  public void initialize() {
    //Hide warning
    warningLabel.setVisible(false);
    
    //Set up table and the column values
    //The property value factory is the name of the getter method in the project class and allows
    // for the table to independently fetch the values for each column
    name.setCellValueFactory(new PropertyValueFactory<>("name"));
    dueDate.setCellValueFactory(new PropertyValueFactory<>("dueDate"));
    category.setCellValueFactory(new PropertyValueFactory<>("category"));
    totalAccounting.setCellValueFactory(new PropertyValueFactory<>("accountingTotal"));
    
    //Clears the table and adds all the projects in the project registry to the table
    table.getItems().clear();
    if (User.getInstance().getProjectRegistry().getProjects() != null) {
      table.getItems().addAll(User.getInstance().getProjectRegistry().getProjects());
    }
    table.refresh();
    
    //Sets the color of each row depending on the status of the project
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
   * Opens the new project page.
   *
   * <p>In case of an error opening the page, an error message is displayed prompting the user to
   * restart the application as no other potential solution is available.
   */
  @FXML
  public void newProject() {
    try {
      Parent root =
          FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/NewProject.fxml")));
      BudgetAndAccountingApp.setRoot(root);
    } catch (Exception e) {
      setWarning("Could not create new project, please restart the application.");
    }
  }
  
  /**
   * Takes the chosen project and loads the edit project page by initializing it with the chosen
   * project.
   *
   * <p>If no project is chosen, an error message is displayed prompting the user to choose a
   * project.
   *
   * <p>In case of an error opening the page, an error message is displayed prompting the user
   * to restart the application as no other potential solution is available.
   */
  @FXML
  public void editProject() {
    try {
      Project selectedProject = table.getSelectionModel().getSelectedItem();
      
      FXMLLoader loader = new FXMLLoader(getClass().getResource("/EditProject.fxml"));
      Parent root = loader.load();
      
      EditProjectController controller = loader.getController();
      controller.initializeWithData(selectedProject);
      
      BudgetAndAccountingApp.setRoot(root);
    } catch (IllegalArgumentException e) {
      setWarning(e.getMessage());
    } catch (IOException e) {
      setWarning("Could not load edit project page, please restart the application.");
    }
  }
  
  /**
   * Takes the chosen project and loads the view project page by initializing it with the chosen
   * project.
   *
   * <p>If no project is chosen, an error message is displayed prompting the user to choose a
   * project.
   *
   * <p>In case of an error opening the page, an error message is displayed prompting the user
   * to restart the application as no other potential solution is available.
   */
  @FXML
  public void viewProject() {
    try {
      Project selectedProject = table.getSelectionModel().getSelectedItem();
      
      FXMLLoader loader = new FXMLLoader(getClass().getResource("/ViewProject.fxml"));
      Parent root = loader.load();
      
      ViewProjectController controller = loader.getController();
      controller.initializeWithData(selectedProject);
      
      BudgetAndAccountingApp.setRoot(root);
    } catch (IllegalArgumentException e) {
      setWarning(e.getMessage());
    } catch (IOException e) {
      setWarning("Could not load view project page, please restart the application.");
    }
  }
  
  /**
   * Loads the monthly overview page.
   *
   * <p>In case of an error opening the page, an error message is displayed prompting the user to
   * restart the application as no other potential solution is available.
   */
  @FXML
  public void monthly() {
    try {
      Parent root =
          FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/MonthlyOverview.fxml")));
      BudgetAndAccountingApp.setRoot(root);
    } catch (IOException e) {
      setWarning("Could not load monthly overview, please restart the application.");
    }
  }
  
  /**
   * Loads the start page.
   *
   * <p>In case of an error opening the page, an error message is displayed prompting the user to
   * restart the application as no other potential solution is available.
   */
  @FXML
  public void start() {
    try {
      Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/Start.fxml")));
      BudgetAndAccountingApp.setRoot(root);
    } catch (IOException e) {
      setWarning("Could not load start page, please restart the application.");
    }
  }
  
  /**
   * Sets the warning label to display the given warning.
   *
   * @param warning The warning to display.
   */
  private void setWarning(String warning) {
    warningLabel.setVisible(true);
    warningLabel.setText(warning);
  }
  
  /**
   * Switches the theme of the application.
   */
  public void switchTheme() {
    BudgetAndAccountingApp.switchTheme();
  }
}

