package no.ntnu.idatt1002.app;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Objects;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import no.ntnu.idatt1002.app.data.Expense;
import no.ntnu.idatt1002.app.data.Income;
import no.ntnu.idatt1002.app.data.Project;
import no.ntnu.idatt1002.app.data.User;
import no.ntnu.idatt1002.app.fileHandling.FileHandling;

public class BudgetAndAccountingApp extends Application {
    
    private void testData() {
        User user = new User();
        Project project1 = new Project("Project 1", "Description 1", "Category 1", LocalDate.now());
        Income income1 = new Income("Income 1", "Category 1", 100, LocalDate.now());
        Income income2 = new Income("Income 2", "Category 2", 200, LocalDate.now());
        project1.getAccounting().addIncome(income1);
        project1.getBudgeting().addIncome(income2);
        Expense expense1 = new Expense("Expense 1", "Category 1", 100, LocalDate.now());
        Expense expense2 = new Expense("Expense 2", "Category 2", 200, LocalDate.now());
        project1.getAccounting().addExpense(expense1);
        project1.getBudgeting().addExpense(expense2);
        user.addProject(project1);
        
        Project project2 = new Project("Project 2", "Description 2", "Category 2", LocalDate.now());
        Income income3 = new Income("Income 3", "Category 1", 100, LocalDate.now());
        Income income4 = new Income("Income 4", "Category 2", 200, LocalDate.now());
        project2.getAccounting().addIncome(income3);
        project2.getBudgeting().addIncome(income4);
        Expense expense3 = new Expense("Expense 3", "Category 1", 100, LocalDate.now());
        Expense expense4 = new Expense("Expense 4", "Category 2", 200, LocalDate.now());
        project2.getAccounting().addExpense(expense3);
        project2.getBudgeting().addExpense(expense4);
        user.addProject(project2);
    
        try {
            FileHandling.writeUserToFile(user);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private static Scene scene;
    
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        testData();
        
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(
            "/AllProjects.fxml")));
        
        scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
    public static void setRoot(Parent root) {
        scene.setRoot(root);
    }
}
