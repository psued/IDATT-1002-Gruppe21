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

public class StartController {
  
  @FXML private Label warningLabel = new Label();
  
  public void initialize() {
    warningLabel.setVisible(false);
    
    try {
      User.getInstance().loadUser(FileHandling.readUserFromFile());
    } catch (IOException | ClassNotFoundException e) {
      warningLabel.setText("Could not load user data, user has been reset");
    }
  }

  public void projects() {
    try {
      Parent root = FXMLLoader.load(
        Objects.requireNonNull(getClass().getResource("/AllProjects.fxml")));
      BudgetAndAccountingApp.setRoot(root);
    } catch (IOException e) {
      warningLabel.setText("Could not load projects, please restart the application");
    }
  }

  public void monthly() {
    try {
      Parent root = FXMLLoader.load(
        Objects.requireNonNull(getClass().getResource("/MonthlyOverview.fxml")));
      BudgetAndAccountingApp.setRoot(root);
    } catch (IOException e) {
      warningLabel.setText("Could not load monthly overview, please restart the application");
    }
  }
}
