package no.ntnu.idatt1002.app.gui;

import java.time.LocalDate;
import java.util.ArrayList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import no.ntnu.idatt1002.app.data.Expense;
import no.ntnu.idatt1002.app.data.Income;
import no.ntnu.idatt1002.app.data.Project;
import no.ntnu.idatt1002.app.data.User;

/**
 * Controller for the view project view. Just displays the project data and allows the user to
 * either edit the project or go back to the all projects view.
 */
public class ViewProjectController {
  
  private User createTestProject() {
    Project project = new Project("Test Project", "This is a test project", "Test",
        LocalDate.now());
    project.getAccounting().addIncome(new Income("Test Accounting ", "Test Income", 100,
        LocalDate.now()));
    project.getAccounting().addExpense(new Expense("Test Accounting", "Test Expense", 200,
        LocalDate.now()));
    project.getBudgeting().addIncome(new Income("Test Budgeting", "Test Income", 300,
        LocalDate.now()));
    project.getBudgeting().addExpense(new Expense("Test Budgeting", "Test Expense", 400,
        LocalDate.now()));
    
    User user = new User();
    user.getProjectRegistry().addProject(project);
    return user;
  }
  
  private User user = createTestProject();
  
  private Project project = user.getProjectRegistry().getProjects().get(0);
  
  private final ArrayList<Income> accountingIncome = project.getAccounting()
      .getIncomeList();
  private final ArrayList<Expense> accountingExpense = project.getAccounting()
      .getExpenseList();
  
  // Local Budgeting overview
  private final ArrayList<Income> budgetingIncome = project.getBudgeting()
      .getIncomeList();
  private final ArrayList<Expense> budgetingExpense = project.getBudgeting()
      .getExpenseList();

  @FXML private Text viewTitle;
  @FXML private Text name;
  @FXML private Text category;
  @FXML private Text dueDate;
  @FXML private Text description;

  @FXML private Button edit;
  @FXML private Button allProjects;

  @FXML private Button accounting;
  @FXML private Button budgeting;
  private boolean isAccounting = true;
  
  @FXML private TableView<Income> incomeTable;
  @FXML private TableColumn<Income, LocalDate> incomeDate;
  @FXML private TableColumn<Income, String> incomeDescription;
  @FXML private TableColumn<Income, String> incomeCategory;
  @FXML private TableColumn<Income, Double> incomeAmount;


  @FXML private TableView<Expense> expenseTable;
  @FXML private TableColumn<Expense, LocalDate> expenseDate;
  @FXML private TableColumn<Expense, String> expenseDescription;
  @FXML private TableColumn<Expense, String> expenseCategory;
  @FXML private TableColumn<Expense, Double> expenseAmount;
  
  @FXML private Text totalIncome;
  @FXML private Text totalExpense;
  @FXML private Text totalAmount;
  
  /**
   * Initialize the view project controller. Sets all text objects to match with the project data
   * and sets up the tables.
   */
  public void initialize() {
    viewTitle.setText("View " + project.getName());
    name.setText(project.getName());
    category.setText(project.getCategory());
    dueDate.setText(project.getDueDate().toString());
    description.setText(project.getDescription());
    
    accounting.setStyle("-fx-border-color: #000000");
    
    // Accounting table
    incomeDate.setCellValueFactory(new PropertyValueFactory<>("date"));
    incomeDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
    incomeCategory.setCellValueFactory(new PropertyValueFactory<>("category"));
    incomeAmount.setCellValueFactory(new PropertyValueFactory<>("amount"));
    
    // Budgeting table
    expenseDate.setCellValueFactory(new PropertyValueFactory<>("date"));
    expenseDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
    expenseCategory.setCellValueFactory(new PropertyValueFactory<>("category"));
    expenseAmount.setCellValueFactory(new PropertyValueFactory<>("amount"));
    
    refreshLocalOverview();
  }
  
  /**
   * Toggle to accounting view, makes the accounting button style look active. Updates the
   * isAccounting boolean so that when the tables are updated, the correct table is referenced.
   */
  @FXML
  public void toggleAccounting() {
    accounting.setStyle("-fx-border-color: #000000");
    budgeting.setStyle("-fx-border-color: none");
    isAccounting = true;
    
    refreshLocalOverview();
  }
  
  /**
   * Toggle to budgeting view, makes the budgeting button style look active. Updates the
   * isAccounting boolean so that when a table is updated, the correct table is referenced.
   */
  public void toggleBudgeting() {
    budgeting.setStyle("-fx-border-color: #000000");
    accounting.setStyle("-fx-border-color: none");
    isAccounting = false;
    
    refreshLocalOverview();
  }
  
  /**
   * Refreshes the local overview tables and totals. Updates the tables with the correct data
   * depending on the isAccounting boolean.
   */
  private void refreshLocalOverview() {
    // Update tables
    incomeTable.getItems().clear();
    expenseTable.getItems().clear();
    
    incomeTable.getItems().addAll(isAccounting ? accountingIncome : budgetingIncome);
    expenseTable.getItems().addAll(isAccounting ? accountingExpense : budgetingExpense);
    
    incomeTable.refresh();
    expenseTable.refresh();
    
    // Update totals
    double incomeAmount = isAccounting ? accountingIncome.stream().mapToDouble(Income::getAmount)
        .sum() : budgetingIncome.stream().mapToDouble(Income::getAmount).sum();
    double expenseAmount = isAccounting ? accountingExpense.stream().mapToDouble(Expense::getAmount)
        .sum() : budgetingExpense.stream().mapToDouble(Expense::getAmount).sum();
    
    totalIncome.setText(String.format("%.2f kr", incomeAmount));
    totalExpense.setText(String.format("- %.2f kr", expenseAmount));
    totalAmount.setText(String.format("%.2f kr", incomeAmount - expenseAmount));
  }
  
  /**
   * Opens the edit project view of the current project.
   */
  public void editProject() {
  
  }
  
  /**
   * Opens the all projects view.
   */
  public void goToAllProjects() {
  
  }
}
