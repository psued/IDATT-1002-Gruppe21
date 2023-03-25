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
import no.ntnu.idatt1002.app.data.Project;
import no.ntnu.idatt1002.app.data.User;
import no.ntnu.idatt1002.app.fileHandling.FileHandling;

public class AllProjectsController {
  
  private User tempUser;
  
  @FXML private TableView<Project> table;
  @FXML private TableColumn<Project, String> name;
  @FXML private TableColumn<Project, Date> dueDate;
  @FXML private TableColumn<Project, String> category;
  @FXML private TableColumn<Project, Double> totalAccounting;

  @FXML private Text errorMessage;
  

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
      errorMessage.setText("Please select a project");
      errorMessage.setVisible(true);
    }
  }
  
  public void viewProject(){
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
      errorMessage.setText("Please select a project");
      errorMessage.setVisible(true);
    }
    
  }

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

