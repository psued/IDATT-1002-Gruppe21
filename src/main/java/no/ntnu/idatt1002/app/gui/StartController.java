package no.ntnu.idatt1002.app.gui;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import no.ntnu.idatt1002.app.BudgetAndAccountingApp;

import java.io.IOException;
import java.util.Objects;

public class StartController {

  public void projects() {
    try {
      Parent root = FXMLLoader.load(
        Objects.requireNonNull(getClass().getResource("/AllProjects.fxml")));
      BudgetAndAccountingApp.setRoot(root);
    } catch (IOException e) {
      e.printStackTrace();
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
}
