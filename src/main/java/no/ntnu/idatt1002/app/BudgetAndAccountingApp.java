package no.ntnu.idatt1002.app;

import java.io.IOException;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.Objects;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import no.ntnu.idatt1002.app.filehandling.FileHandling;
import no.ntnu.idatt1002.app.registers.MonthlyBookkeeping;
import no.ntnu.idatt1002.app.registers.Project;
import no.ntnu.idatt1002.app.transactions.Expense;
import no.ntnu.idatt1002.app.transactions.Income;

/**
 * Starts the program by setting the root to the all projects page.
 */
public class BudgetAndAccountingApp extends Application {
  
  private static Scene scene;
  
  public static void main(String[] args) {
    launch(args);
  }
  
  public static void setRoot(Parent root) {
    scene.setRoot(root);
  }

  public static void setTheme() {
    if (scene.getStylesheets().size() == 1) {
      scene.getStylesheets().remove(0);
    }
    else {
      scene.getStylesheets().add("styles/dark-theme.css");
    }
  }
  
  @Override
  public void start(Stage primaryStage) throws IOException {
    Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/Start.fxml")));
    scene = new Scene(root);

    primaryStage.setScene(scene);
    primaryStage.setTitle("Budget and Accounting application");
    primaryStage.setIconified(false);

    primaryStage.setWidth(1250);
    primaryStage.setHeight(750);

    primaryStage.show();
    
    //Delete all empty years when closing the program
    primaryStage.setOnCloseRequest(event -> {
      try {
        for (MonthlyBookkeeping monthlyBookkeeping :
            User.getInstance().getMonthlyBookkeepingRegistry().getMonthlyBookkeepingMap().values()) {
          if (User.getInstance().getMonthlyBookkeepingRegistry().isYearEmpty(monthlyBookkeeping.getYearMonth())) {
            User.getInstance().getMonthlyBookkeepingRegistry().removeMonthlyBookkeeping(monthlyBookkeeping.getYearMonth());
          }
        }
        
        FileHandling.writeUserToFile(User.getInstance());
      } catch (Exception e) {
        e.printStackTrace();
      }
    });
    
  }
}
