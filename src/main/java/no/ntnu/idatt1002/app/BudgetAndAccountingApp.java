package no.ntnu.idatt1002.app;

import java.io.IOException;
import java.util.Objects;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import no.ntnu.idatt1002.app.filehandling.FileHandling;
import no.ntnu.idatt1002.app.registers.MonthlyBookkeeping;

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
  
  /**
   * Toggles the theme between dark mode and light mode.
   */
  public static void switchTheme() {
    if (scene.getStylesheets().contains("styles/dark-theme.css")) {
      scene.getStylesheets().clear();
      scene.getStylesheets().add("styles/light-theme.css");
    } else {
      scene.getStylesheets().clear();
      scene.getStylesheets().add("styles/dark-theme.css");
    }
  }
  
  @Override
  public void start(Stage primaryStage) throws IOException {
    Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/Start.fxml")));
    scene = new Scene(root);

    scene.getStylesheets().add("styles/dark-theme.css");

    primaryStage.setScene(scene);
    primaryStage.setTitle("Budget and Accounting application");
    primaryStage.setIconified(false);

    primaryStage.setMaximized(true);

    primaryStage.setMinHeight(720);
    primaryStage.setMinWidth(1280);

    primaryStage.show();
    
    //Delete all empty years when closing the program
    primaryStage.setOnCloseRequest(event -> {
      try {
        User.getInstance().loadUser(FileHandling.readUserFromFile());
        for (MonthlyBookkeeping monthlyBookkeeping : User.getInstance()
            .getMonthlyBookkeepingRegistry().getMonthlyBookkeepingMap().values()) {
          if (User.getInstance().getMonthlyBookkeepingRegistry()
              .isYearEmpty(monthlyBookkeeping.getYearMonth())) {
            User.getInstance().getMonthlyBookkeepingRegistry()
                .removeMonthlyBookkeeping(monthlyBookkeeping.getYearMonth());
          }
        }
        
        FileHandling.writeUserToFile(User.getInstance());
      } catch (Exception e) {
        e.printStackTrace();
      }
    });
    
  }
}
