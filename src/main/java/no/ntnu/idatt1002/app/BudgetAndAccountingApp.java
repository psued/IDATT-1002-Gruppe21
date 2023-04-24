package no.ntnu.idatt1002.app;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Objects;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import no.ntnu.idatt1002.app.filehandling.FileHandling;
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
  
  private void testData() {
    Project project1 = new Project("Project 1", "Description 1", "Category 1", LocalDate.now(), "Doing");
    Income income1 = new Income("Income 1", "Category 1", 100, LocalDate.now());
    Income income2 = new Income("Income 2", "Category 2", 200, LocalDate.now());
    project1.getAccounting().addIncome(income1);
    project1.getBudgeting().addIncome(income2);
    Expense expense1 = new Expense("Expense 1", "Category 1", 100, LocalDate.now());
    Expense expense2 = new Expense("Expense 2", "Category 2", 200, LocalDate.now());
    project1.getAccounting().addExpense(expense1);
    project1.getBudgeting().addExpense(expense2);
    
    User user = User.getInstance();
    user.getProjectRegistry().addProject(project1);

    Project project2 = new Project("Project 2", "Description 2", "Category 2", LocalDate.now(), "Finished");
    Income income3 = new Income("Income 3", "Category 1", 100, LocalDate.now());
    Income income4 = new Income("Income 4", "Category 2", 200, LocalDate.now());
    project2.getAccounting().addIncome(income3);
    project2.getBudgeting().addIncome(income4);
    Expense expense3 = new Expense("Expense 3", "Category 1", 100, LocalDate.now());
    Expense expense4 = new Expense("Expense 4", "Category 2", 200, LocalDate.now());
    project2.getAccounting().addExpense(expense3);
    project2.getBudgeting().addExpense(expense4);
    user.getProjectRegistry().addProject(project2);
    
    try {
      FileHandling.writeUserToFile(user);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
  
  @Override
  public void start(Stage primaryStage) throws IOException {
    try {
      User GlobalUser = FileHandling.readUserFromFile();
    } catch (Exception e) {
      e.printStackTrace();
    }
    
    Parent root = FXMLLoader
        .load(Objects.requireNonNull(getClass().getResource("/AllProjects.fxml")));
    
    scene = new Scene(root);

    primaryStage.setScene(scene);
    primaryStage.show();
  }
}
