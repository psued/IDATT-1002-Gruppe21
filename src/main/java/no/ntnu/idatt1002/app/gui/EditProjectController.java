package no.ntnu.idatt1002.app.gui;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Text;
import no.ntnu.idatt1002.app.BudgetAndAccountingApp;
import no.ntnu.idatt1002.app.data.Expense;
import no.ntnu.idatt1002.app.data.Income;
import no.ntnu.idatt1002.app.data.Project;
import no.ntnu.idatt1002.app.data.Transaction;
import no.ntnu.idatt1002.app.data.User;
import no.ntnu.idatt1002.app.fileHandling.FileHandling;

/**
 * FXML Controller class for the EditProject.fxml file. Takes an existing project and allows the
 * user to edit the project information, accounting and budgeting.
 */
public class EditProjectController {
  
  private User tempUser;
  private Project originalProject;
  
  /**
   * Initializes the controller class.
   */
  public void initializeWithData(Project project) throws NullPointerException {
    originalProject = Objects.requireNonNull(project);
    initializeController();
  }
  
  
  // Local Accounting overview
  private ArrayList<Income> accountingIncome;
  private ArrayList<Expense> accountingExpense;
  
  // Local Budgeting overview
  private ArrayList<Income> budgetingIncome;
  private  ArrayList<Expense> budgetingExpense;
  
  // Fundamental project information
  @FXML private TextField name;
  @FXML private MenuButton category;
  @FXML private TextArea description;
  @FXML private DatePicker dueDate;
  
  //Accounting and Budgeting buttons
  @FXML private Button accounting;
  @FXML private Button budgeting;
  
  //Selected transaction status
  private boolean isAccounting = true;
  private Transaction selectedTransaction = null;
  
  //Income Table
  @FXML private TableView<Income> incomeTable;
  @FXML private TableColumn<Income, LocalDate> incomeDate;
  @FXML private TableColumn<Income, String> incomeDescription;
  @FXML private TableColumn<Income, String> incomeCategory;
  @FXML private TableColumn<Income, Double> incomeAmount;
  //Income fields
  @FXML private DatePicker incomeDatePicker;
  @FXML private TextField incomeDescriptionField;
  @FXML private TextField incomeCategoryField;
  @FXML private TextField incomeAmountField;
  
  //Expense Table
  @FXML private TableView<Expense> expenseTable;
  @FXML private TableColumn<Expense, LocalDate> expenseDate;
  @FXML private TableColumn<Expense, String> expenseDescription;
  @FXML private TableColumn<Expense, String> expenseCategory;
  @FXML private TableColumn<Expense, Double> expenseAmount;
  //Expense fields
  @FXML private DatePicker expenseDatePicker;
  @FXML private TextField expenseDescriptionField;
  @FXML private TextField expenseCategoryField;
  @FXML private TextField expenseAmountField;
  
  //Total income, expense and amount overview
  @FXML private Text totalIncome;
  @FXML private Text totalExpense;
  @FXML private Text totalAmount;
  
  //Error message
  @FXML private final Label nameError = new Label();
  
  /**
   * Initializes the controller class. Also sets up the text fields and tables to display the
   * data of the project that is being edited.
   */
  public void initializeController() {
    try {
      tempUser = FileHandling.readUserFromFile();
    } catch (IOException e) {
      e.printStackTrace();
    } catch (ClassNotFoundException e) {
      throw new RuntimeException(e);
    }
    
    // Set up the text fields to display the project information
    name.setText(originalProject.getName());
    category.setText(originalProject.getCategory());
    description.setText(originalProject.getDescription());
    dueDate.setValue(originalProject.getDueDate());
    
    category.getItems().clear();
  
    accountingIncome = originalProject.getAccounting().getIncomeList();
    accountingExpense = originalProject.getAccounting().getExpenseList();
    budgetingIncome = originalProject.getBudgeting().getIncomeList();
    budgetingExpense = originalProject.getBudgeting().getExpenseList();
    
    
    // Add categories to the category menu button
    for (String category : tempUser.getProjectRegistry().getCategories()) {
      MenuItem menuItem = new MenuItem(category);
      menuItem.setOnAction(event -> this.category.setText(menuItem.getText()));
      this.category.getItems().add(menuItem);
    }
    
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
    
    // Set up the tables to display the transactions of the project that is being edited
    refreshLocalOverview();
    
    nameError.setVisible(false);
  }
  
  /**
   * Toggle to accounting view, makes the accounting button style look active. Updates the
   * isAccounting boolean so that when a table is changed, the correct table is updated.
   */
  public void toggleAccounting() {
    accounting.setStyle("-fx-border-color: #000000");
    budgeting.setStyle("-fx-border-color: none");
    isAccounting = true;
    
    refreshLocalOverview();
  }
  
  /**
   * Toggle to budgeting view, makes the budgeting button style look active. Updates the
   * isAccounting boolean so that when a table is changed, the correct table is updated.
   */
  public void toggleBudgeting() {
    budgeting.setStyle("-fx-border-color: #000000");
    accounting.setStyle("-fx-border-color: none");
    isAccounting = false;
    
    refreshLocalOverview();
  }
  
  /**
   * Checks if the user has selected an income and if so, it will input the income values into
   * the income fields. If the user has clicked an empty row or the same row twice, the income
   * fields will be reset.
   *
   * <p>Clicking an income row will also update the selectedTransaction variable which has the
   * effect of updating the chosen income rather than creating a new one.
   */
  public void selectedIncome() {
    if (incomeTable.getSelectionModel().getSelectedItem() != selectedTransaction) {
      selectedTransaction = incomeTable.getSelectionModel().getSelectedItem();
      incomeDatePicker.setValue(selectedTransaction.getDate() == null
          ? null : selectedTransaction.getDate());
      incomeDescriptionField.setText(selectedTransaction.getDescription());
      incomeCategoryField.setText(selectedTransaction.getCategory());
      incomeAmountField.setText(String.valueOf(selectedTransaction.getAmount()));
    } else {
      incomeTable.getSelectionModel().clearSelection();
      selectedTransaction = null;
      resetIncomeFields();
    }
  }
  
  /**
   * Checks if the user has selected an expense and if so, it will input the expense values into
   * the expense fields. If the user has clicked an empty row or the same row twice, the expense
   * fields will be reset.
   *
   * <p>Clicking an expense row will also update the selectedTransaction variable which has the
   * effect of updating the chosen expense rather than creating a new one.
   */
  public void selectedExpense() {
    if (expenseTable.getSelectionModel().getSelectedItem() != selectedTransaction) {
      selectedTransaction = expenseTable.getSelectionModel().getSelectedItem();
      expenseDatePicker.setValue(selectedTransaction.getDate() == null
          ? null : selectedTransaction.getDate());
      expenseDescriptionField.setText(selectedTransaction.getDescription());
      expenseCategoryField.setText(selectedTransaction.getCategory());
      expenseAmountField.setText(String.valueOf(selectedTransaction.getAmount()));
    } else {
      expenseTable.getSelectionModel().clearSelection();
      selectedTransaction = null;
      resetExpenseFields();
    }
  }
  
  /**
   * When pressing the add button, the addIncomeToLocal method will be called and depending on
   * whether a row is selected or not, the income will be added or updated.
   */
  public void addIncomeToLocal() {
    try {
      if (isAccounting) {
        if (selectedTransaction != null) {
          accountingIncome.remove((Income) selectedTransaction);
          accountingIncome.add(new Income(incomeDescriptionField.getText(), incomeCategoryField.getText(), Double.parseDouble(incomeAmountField.getText()), incomeDatePicker.getValue()));
        } else {
          accountingIncome.add(new Income(incomeDescriptionField.getText(), incomeCategoryField.getText(), Double.parseDouble(incomeAmountField.getText()), incomeDatePicker.getValue()));
        }
      } else {
        if (selectedTransaction != null) {
          budgetingIncome.remove((Income) selectedTransaction);
          budgetingIncome.add(new Income(incomeDescriptionField.getText(), incomeCategoryField.getText(), Double.parseDouble(incomeAmountField.getText()), incomeDatePicker.getValue()));
        } else {
          budgetingIncome.add(new Income(incomeDescriptionField.getText(), incomeCategoryField.getText(), Double.parseDouble(incomeAmountField.getText()), incomeDatePicker.getValue()));
        }
      }

      
      refreshLocalOverview();
      resetIncomeFields();
      
    } catch (NumberFormatException e) {
      nameError.setText("Please enter a valid amount");
      nameError.setVisible(true);
    } catch (IllegalArgumentException e) {
      nameError.setText(e.getMessage());
      nameError.setVisible(true);
    }
  }
  
  /**
   * When pressing the add button, the addExpenseToLocal method will be called and depending on
   * whether a row is selected or not, the expense will be added or updated.
   */
  public void addExpenseToLocal() {
    try {
      if (isAccounting) {
        if (selectedTransaction != null) {
          accountingExpense.remove((Expense) selectedTransaction);
          accountingExpense.add(new Expense(expenseDescriptionField.getText(), expenseCategoryField.getText(), Double.parseDouble(expenseAmountField.getText()), expenseDatePicker.getValue()));
        } else {
          accountingExpense.add(new Expense(expenseDescriptionField.getText(), expenseCategoryField.getText(), Double.parseDouble(expenseAmountField.getText()), expenseDatePicker.getValue()));
        }
      } else {
        if (selectedTransaction != null) {
          budgetingExpense.remove((Expense) selectedTransaction);
          budgetingExpense.add(new Expense(expenseDescriptionField.getText(), expenseCategoryField.getText(), Double.parseDouble(expenseAmountField.getText()), expenseDatePicker.getValue()));
        } else {
          budgetingExpense.add(new Expense(expenseDescriptionField.getText(), expenseCategoryField.getText(), Double.parseDouble(expenseAmountField.getText()), expenseDatePicker.getValue()));
        }
      }
      
      refreshLocalOverview();
      resetExpenseFields();
      
    } catch (NumberFormatException e) {
      nameError.setText("Please enter a valid amount");
      nameError.setVisible(true);
    } catch (IllegalArgumentException e) {
      nameError.setText(e.getMessage());
      nameError.setVisible(true);
    }
  }
  
  /**
   * Removes a transaction from the local overview when the delete or backspace key is pressed.
   * Also checks if the overview is set to accounting or budgeting.
   */
  @FXML
  public void removeTransaction(KeyEvent event) {
    if (event.getCode() == KeyCode.DELETE || event.getCode() == KeyCode.BACK_SPACE) {
      
      List<? extends Transaction> transactionList = isAccounting
          ? (selectedTransaction instanceof Income ? accountingIncome : accountingExpense)
          : (selectedTransaction instanceof Income ? budgetingIncome : budgetingExpense);
      
      if (selectedTransaction != null) {
        transactionList.remove(selectedTransaction);
      }
      
      refreshLocalOverview();
    }
  }
  
  /**
   * Refreshes the local overview by updating the tables and totals, resetting the selected row
   * and resets the error message.
   */
  private void refreshLocalOverview() {
    selectedTransaction = null;
    
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
    
    // Reset error message
    nameError.setVisible(false);
    nameError.setText("");
  }
  
  // Resets the income fields
  private void resetIncomeFields() {
    incomeDatePicker.setValue(null);
    incomeDescriptionField.setText("");
    incomeCategoryField.setText("");
    incomeAmountField.setText("");
  }
  
  // Resets the expense fields
  private void resetExpenseFields() {
    expenseDatePicker.setValue(null);
    expenseDescriptionField.setText("");
    expenseCategoryField.setText("");
    expenseAmountField.setText("");
  }
  
  /**
   * Updates the project with the new values and saves it to the user registry. It does this by
   * deleting the old project and adding the new one.
   */
  public void saveProject() {
    try {
      Project project = new Project(name.getText(), description.getText(), category.getText(),
          dueDate.getValue());
  
      accountingIncome.forEach(project.getAccounting()::addIncome);
      accountingExpense.forEach(project.getAccounting()::addExpense);
      budgetingIncome.forEach(project.getBudgeting()::addIncome);
      budgetingExpense.forEach(project.getBudgeting()::addExpense);
      
      tempUser.removeProject(originalProject);
      tempUser.addProject(project);
      
      try {
        FileHandling.writeUserToFile(tempUser);
        Parent root = FXMLLoader.load(Objects.requireNonNull(
            Objects.requireNonNull(getClass().getResource("/AllProjects.fxml"))));
        BudgetAndAccountingApp.setRoot(root);
      } catch (IOException e) {
        e.printStackTrace();
      }
      
    } catch (IllegalArgumentException e) {
      nameError.setVisible(true);
      nameError.setText(e.getMessage());
    }
  }
  
  public void deleteProject() {
    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
    alert.setTitle("Delete project");
    alert.setHeaderText("Are you sure you want to delete this project?");
    alert.setContentText("This action cannot be undone.");
    
    Optional<ButtonType> result = alert.showAndWait();
    
    if (result.isPresent() && result.get() == ButtonType.OK) {
      tempUser.removeProject(originalProject);
      try {
        
        FileHandling.writeUserToFile(tempUser);
        Parent root = FXMLLoader.load(
            Objects.requireNonNull(getClass().getResource("/AllProjects.fxml")));
        BudgetAndAccountingApp.setRoot(root);
        
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }
}
