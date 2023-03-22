package no.ntnu.idatt1002.app.gui;

import java.time.LocalDate;
import java.util.ArrayList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import no.ntnu.idatt1002.app.data.Expense;
import no.ntnu.idatt1002.app.data.Income;
import no.ntnu.idatt1002.app.data.Project;
import no.ntnu.idatt1002.app.data.User;
import no.ntnu.idatt1002.app.registry.ProjectRegistry;

public class NewProjectController {
  
  private User user = new User();
  
  private ProjectRegistry projectRegistry = user.getProjectRegistry();
  
  // Local Accounting overview
  private ArrayList<Income> AccountingIncome = new ArrayList<>();
  private ArrayList<Expense> AccountingExpense = new ArrayList<>();
  
  // Local Budgeting overview
  private ArrayList<Income> BudgetingIncome = new ArrayList<>();
  private ArrayList<Expense> BudgetingExpense = new ArrayList<>();
  
  // Fundamental project information
  @FXML private TextField name;
  @FXML private MenuButton category;
  @FXML private TextArea description;
  @FXML private DatePicker dueDate;
  
  //Accounting and Budgeting buttons
  @FXML private Button accounting;
  @FXML private Button budgeting;
  
  //Accounting Table
  @FXML private TableView<Income> incomeTable;
  @FXML private TableColumn<Income, LocalDate> incomeDate;
  @FXML private TableColumn<Income, String> incomeDescription;
  @FXML private TableColumn<Income, String> incomeCategory;
  @FXML private TableColumn<Income, Double> incomeAmount;
  @FXML private DatePicker incomeDatePicker;
  @FXML private TextField incomeDescriptionField;
  @FXML private TextField incomeCategoryField;
  @FXML private TextField incomeAmountField;
  @FXML private Button addIncome;
  
  //Budgeting Table
  @FXML private TableView<Expense> expenseTable;
  @FXML private TableColumn<Expense, LocalDate> expenseDate;
  @FXML private TableColumn<Expense, String> expenseDescription;
  @FXML private TableColumn<Expense, String> expenseCategory;
  @FXML private TableColumn<Expense, Double> expenseAmount;
  @FXML private DatePicker expenseDatePicker;
  @FXML private TextField expenseDescriptionField;
  @FXML private TextField expenseCategoryField;
  @FXML private TextField expenseAmountField;
  @FXML private Button addExpense;
  
  //Save and delete buttons
  @FXML private Button save;
  @FXML private Button delete;
  
  //Error message
  @FXML private Label nameError = new Label();
  
  /**
   * Initializes the controller class.
   */
  public void initialize() {
    user.addTestProjects();
    
    category.getItems().clear();
  
    for (String category : projectRegistry.getCategories()) {
      MenuItem menuItem = new MenuItem(category);
      menuItem.setOnAction(event -> this.category.setText(menuItem.getText()));
      this.category.getItems().add(menuItem);
    }
    
    accounting.setStyle("-fx-border-color: #000000");
    
    nameError.setVisible(false);
  }
  
  /**
   * Toggle to accounting view, makes the accounting button style look active
   */
  public void toggleAccounting() {
    accounting.setStyle("-fx-border-color: #000000");
    budgeting.setStyle("-fx-border-color: none");
  }
  
  /**
   * Toggle to budgeting view, makes the budgeting button style look active
   */
  public void toggleBudgeting() {
    budgeting.setStyle("-fx-border-color: #000000");
    accounting.setStyle("-fx-border-color: none");
  }
  
  public void saveProject() {
    try {
      Project project = new Project(name.getText(), description.getText(), category.getText());
      user.getProjectRegistry().addProject(project);
      nameError.setText(project.toString());
      nameError.setVisible(true);
    } catch (IllegalArgumentException e) {
      nameError.setVisible(true);
      nameError.setText(e.getMessage());
    }
  }
}
