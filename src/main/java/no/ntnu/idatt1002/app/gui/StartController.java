package no.ntnu.idatt1002.app.gui;

import java.io.IOException;
import java.util.Objects;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import no.ntnu.idatt1002.app.BudgetAndAccountingApp;
import no.ntnu.idatt1002.app.User;
import no.ntnu.idatt1002.app.filehandling.FileHandling;

/**
 * FXML Controller class for the Start.fxml file. Displays the start page of the application and
 * handles the switching between the different pages.
 *
 * <p>In the case of an error loading a page, an error message is displayed prompting the user to
 * restart the application. There are no framework for handling corrupted files and similar
 * errors so the only possible solution could be to restart the application.
 */
public class StartController {
  
  @SuppressWarnings("CanBeFinal")
  @FXML
  private Label warningLabel = new Label();
  
  /**
   * Initializes the start page. If its the first time the application is run, and there is no
   * earlier user data saved, a new user is created and a message is displayed. If there is a user
   * file, the user is loaded.
   */
  @FXML
  public void initialize() {
    warningLabel.setVisible(false);
    
    try {
      User.getInstance().loadUser(FileHandling.readUserFromFile());
    } catch (IOException | ClassNotFoundException e) {
      warningLabel.setVisible(true);
      warningLabel.setText("Could not load user data, user has been reset. Do not mind this "
          + "warning if this is the first ever time you run this application.");
    }
  }
  
  /**
   * Switches to the projects page. If there is an error loading the page, an error message is
   * displayed prompting the user to restart the application.
   */
  @FXML
  public void projects() {
    try {
      Parent root =
          FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/AllProjects.fxml")));
      BudgetAndAccountingApp.setRoot(root);
    } catch (IOException e) {
      warningLabel.setVisible(true);
      warningLabel.setText("Could not load projects, please restart the application");
    }
  }
  
  /**
   * Switches to the monthly overview page. If there is an error loading the page, an error message
   * is displayed prompting the user to restart the application.
   */
  @FXML
  public void monthly() {
    try {
      Parent root =
          FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/MonthlyOverview.fxml")));
      BudgetAndAccountingApp.setRoot(root);
    } catch (IOException e) {
      warningLabel.setVisible(true);
      warningLabel.setText("Could not load monthly overview, please restart the application");
    }
  }
}
