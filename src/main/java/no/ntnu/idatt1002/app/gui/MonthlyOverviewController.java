package no.ntnu.idatt1002.app.gui;

import java.time.LocalDate;
import java.time.Month;
import java.time.Year;
import java.time.YearMonth;
import java.util.List;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.util.Callback;
import no.ntnu.idatt1002.app.BudgetAndAccountingApp;
import no.ntnu.idatt1002.app.User;
import no.ntnu.idatt1002.app.bookkeeping.Bookkeeping;
import no.ntnu.idatt1002.app.filehandling.FileHandling;
import no.ntnu.idatt1002.app.registers.MonthlyBookkeeping;
import no.ntnu.idatt1002.app.transactions.Expense;
import no.ntnu.idatt1002.app.transactions.Income;
import no.ntnu.idatt1002.app.transactions.Transaction;

public class MonthlyOverviewController {
  private User user;
  private YearMonth chosenYearMonth;
  private MonthlyBookkeeping chosenMonthlyBookkeeping;
  private boolean isAccounting = true;
  private boolean isPersonal = true;
  private boolean isTotal = true;
  
  @FXML private Button accountingButton;
  @FXML private Button budgetingButton;
  @FXML private Button personalButton;
  @FXML private Button workButton;
  @FXML private Button totalButton;
  
  @FXML private ComboBox<Month> monthComboBox;
  @FXML private ComboBox<String> yearComboBox;
  
  @FXML private TableView<Transaction> transactionTable;
  @FXML private TableColumn<Transaction, LocalDate> dateColumn;
  @FXML private TableColumn<Transaction, String> descriptionColumn;
  @FXML private TableColumn<Transaction, String> categoryColumn;
  @FXML private TableColumn<Transaction, Double> amountColumn;
  private Transaction selectedTransaction;
  
  @FXML private DatePicker dateField;
  @FXML private TextField descriptionField;
  @FXML private TextField categoryField;
  @FXML private TextField amountField;
 
  @FXML private Button addTransactionButton;
  @FXML private Button deleteTransactionButton;
  
  @FXML private Label incomeLabel;
  @FXML private Label incomeAmountLabel;
 
  @FXML private Label expenseLabel;
  @FXML private Label expenseAmountLabel;
  
  @FXML private Label totalAmountLabel;
  
  @FXML private Label warningLabel;
  
  
  public void initialize() {
    clearWarning();
    
    try {
    user = FileHandling.readUserFromFile();
    } catch (Exception e) {
      setWarning("Could not load user data" + e.getMessage());
    }
    
    if (user.getMonthlyBookkeepingRegistry().getMonthlyBookkeepingMap().isEmpty()) {
      chosenYearMonth = YearMonth.now();
      chosenMonthlyBookkeeping = new MonthlyBookkeeping(chosenYearMonth);
      user.addMonthlyBookkeeping(chosenMonthlyBookkeeping);
      saveUser();
    } else {
      chosenYearMonth = user.getMonthlyBookkeepingRegistry().getMonthlyBookkeepingMap().keySet().iterator().next();
      chosenMonthlyBookkeeping = user.getMonthlyBookkeepingRegistry().getMonthlyBookkeepingMap().get(
          chosenYearMonth);
    }
    
    // Set up the month combo box
    monthComboBox.setValue(chosenYearMonth.getMonth());
    monthComboBox.getItems().addAll(Month.values());
    monthComboBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
      chosenYearMonth = chosenYearMonth.withMonth(newValue.getValue());
      MonthlyBookkeeping monthlyBookkeeping = user.getMonthlyBookkeepingRegistry().getMonthlyBookkeeping(chosenYearMonth);
      chosenMonthlyBookkeeping = monthlyBookkeeping == null
              ? new MonthlyBookkeeping(chosenYearMonth)
              : user.getMonthlyBookkeepingRegistry().getMonthlyBookkeeping(chosenYearMonth);
          
      refreshOverview();
    });
    
    
    // Set up the year combo box
    yearComboBox.setValue(Integer.toString(chosenYearMonth.getYear()));
    yearComboBox.getItems().addAll(user.getMonthlyBookkeepingRegistry().getMonthlyBookkeepingMap().keySet().stream().map(YearMonth::getYear).distinct().sorted().map(String::valueOf).toArray(String[]::new));
    yearComboBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
      //Refresh the month combo box to show the months that have transactions
      chosenYearMonth = chosenYearMonth.withYear(Integer.parseInt(newValue));
      MonthlyBookkeeping monthlyBookkeeping = user.getMonthlyBookkeepingRegistry().getMonthlyBookkeeping(chosenYearMonth);
      
      chosenMonthlyBookkeeping = monthlyBookkeeping == null
              ? new MonthlyBookkeeping(chosenYearMonth)
              : user.getMonthlyBookkeepingRegistry().getMonthlyBookkeeping(chosenYearMonth);
      
      refreshOverview();
    });
 
    
    yearComboBox.setEditable(true);
    yearComboBox.focusedProperty().addListener((observable, oldValue, newValue) -> {
      if (!newValue) {
        yearComboBox.getEditor().addEventFilter(KeyEvent.KEY_RELEASED, event -> {
          if (event.getCode() == KeyCode.ENTER) {
            try {
              int newYear = Integer.parseInt(yearComboBox.getEditor().getText());
              if (newYear < 2000 || newYear > 2100) {
                throw new NumberFormatException("Year must be between 2000 and 2100");
              } else if (yearComboBox.getItems().contains(Integer.toString(newYear))) {
                throw new NumberFormatException("Year already exists");
              }
              
              yearComboBox.getItems().add(Integer.toString(newYear));
              chosenYearMonth = chosenYearMonth.withYear(newYear);
            } catch (NumberFormatException e) {
              yearComboBox.setValue(Integer.toString(chosenYearMonth.getYear()));
              yearComboBox.getEditor().setText(Integer.toString(chosenYearMonth.getYear()));
              setWarning(e.getMessage());
            }
          }
        });
      }
    });
    
    
    dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
    descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
    categoryColumn.setCellValueFactory(new PropertyValueFactory<>("category"));
    amountColumn.setCellValueFactory(new PropertyValueFactory<>("amount"));
    
    // Every transaction that is an expense will be displayed with a negative sign
    amountColumn.setCellFactory(column -> new TableCell<>() {
      @Override
      protected void updateItem(Double item, boolean empty) {
        super.updateItem(item, empty);
        
        if (empty || item == null) {
          setText(null);
        } else {
          Transaction transaction = getTableView().getItems().get(getIndex());
          if (transaction instanceof Expense) {
            setText("-" + item);
          } else {
            setText("" + item);
          }
        }
      }
    });
    
    
    toggleAccounting();
    togglePersonal();
    refreshOverview();
  }
  
  private void setCellColor() {
    monthComboBox.setCellFactory(new Callback<>() {
      @Override
      public ListCell<Month> call(ListView<Month> param) {
        return new ListCell<>() {
          @Override
          protected void updateItem(Month month, boolean empty) {
            super.updateItem(month, empty);
            if (empty || month == null) {
              setText(null);
              setStyle("");
            } else {
              setText(month.toString());
              YearMonth yearMonth = YearMonth.of(chosenYearMonth.getYear(), month);
              MonthlyBookkeeping monthlyBookkeeping = user.getMonthlyBookkeepingRegistry().getMonthlyBookkeeping(yearMonth);
              if (monthlyBookkeeping != null && !monthlyBookkeeping.isEmpty()) {
                setStyle("-fx-background-color: #90EE90");
              } else {
                setStyle("");
              }
            }
          }
        };
      }
    });
  }
  
  
  @FXML
  void addTransaction() {
    LocalDate date = dateField.getValue();
    String description = descriptionField.getText();
    String category = categoryField.getText();
    double amount = Double.parseDouble(amountField.getText());
    
    if (selectedTransaction != null) {
      if (amount > 0 && selectedTransaction instanceof Expense) {
        chosenMonthlyBookkeeping.getBookkeeping(isAccounting, isPersonal).removeTransaction(selectedTransaction);
        chosenMonthlyBookkeeping.getBookkeeping(isAccounting, isPersonal).addTransaction(new Income(description, category, amount, date));
      } else if (amount < 0 && selectedTransaction instanceof Income) {
        chosenMonthlyBookkeeping.getBookkeeping(isAccounting, isPersonal).removeTransaction(selectedTransaction);
        chosenMonthlyBookkeeping.getBookkeeping(isAccounting, isPersonal).addTransaction(new Expense(description, category, -amount, date));
      } else {
        selectedTransaction.setDate(date);
        selectedTransaction.setDescription(description);
        selectedTransaction.setCategory(category);
        selectedTransaction.setAmount(amount > 0 ? amount : -amount);
      }
    } else {
      chosenMonthlyBookkeeping.getBookkeeping(isAccounting, isPersonal).addTransaction(amount > 0
          ? new Income(description, category, amount, date)
          : new Expense(description, category, -amount, date));
    }
    
    if (user.getMonthlyBookkeepingRegistry().getMonthlyBookkeeping(chosenYearMonth) == null) {
      user.addMonthlyBookkeeping(chosenMonthlyBookkeeping);
    } else {
      user.getMonthlyBookkeepingRegistry().removeMonthlyBookkeeping(chosenYearMonth);
      user.addMonthlyBookkeeping(chosenMonthlyBookkeeping);
    }
    
    selectedTransaction = null;
    refreshOverview();
    saveUser();
  }
  
  @FXML
  void deleteTransaction() {
    Transaction transaction = transactionTable.getSelectionModel().getSelectedItem();
    
    chosenMonthlyBookkeeping.getBookkeeping(isAccounting, isPersonal).removeTransaction(transaction);
    
    user.getMonthlyBookkeepingRegistry().removeMonthlyBookkeeping(chosenYearMonth);
    user.addMonthlyBookkeeping(chosenMonthlyBookkeeping);
    
    selectTransaction();
    refreshOverview();
    saveUser();
  }
  
  @FXML
  void toggleAccounting() {
    isAccounting = true;
    
    accountingButton.setStyle("-fx-border-color: #000000;");
    budgetingButton.setStyle("-fx-border-color: none;");
    
    refreshOverview();
  }
  
  @FXML
  void toggleBudget() {
    isAccounting = false;
    
    accountingButton.setStyle("-fx-border-color: none;");
    budgetingButton.setStyle("-fx-border-color: #000000;");
    
    refreshOverview();
  }
  
  @FXML
  void togglePersonal() {
    isPersonal = true;
    isTotal = false;
    
    personalButton.setStyle("-fx-border-color: #000000;");
    workButton.setStyle("-fx-border-color: none;");
    totalButton.setStyle("-fx-border-color: none;");
    
    refreshOverview();
  }
  
  @FXML
  void toggleWork() {
    isPersonal = false;
    isTotal = false;
    
    personalButton.setStyle("-fx-border-color: none;");
    workButton.setStyle("-fx-border-color: #000000;");
    totalButton.setStyle("-fx-border-color: none;");
    
    refreshOverview();
  }
  
  @FXML
  void toggleTotal() {
    isTotal = true;
    
    personalButton.setStyle("-fx-border-color: none;");
    workButton.setStyle("-fx-border-color: none;");
    totalButton.setStyle("-fx-border-color: #000000;");
    
    refreshOverview();
  }
  
  /**
   * Checks if the user has selected a transaction in the table and updates the
   * selectedTransaction and the text fields accordingly.
   */
  @FXML
  public void selectTransaction() {
    boolean isNewTransaction = selectedTransaction != transactionTable.getSelectionModel().getSelectedItem();
    
    if (transactionTable.getSelectionModel().getSelectedItem() == null) {
      isNewTransaction = false;
    }
    
    selectedTransaction = isNewTransaction ? transactionTable.getSelectionModel().getSelectedItem() : null;
    
    dateField.setValue(isNewTransaction ? (selectedTransaction.getDate() == null
        ? null : selectedTransaction.getDate()) : null);
    descriptionField.setText(isNewTransaction ? selectedTransaction.getDescription() : "");
    categoryField.setText(isNewTransaction ? selectedTransaction.getCategory() : "");
    amountField.setText(isNewTransaction ? (selectedTransaction instanceof Income
        ? "" + selectedTransaction.getAmount() : "-" + selectedTransaction.getAmount()) : "");
    
    
    if (!isNewTransaction) {
      transactionTable.getSelectionModel().clearSelection();
    }
  }
  
  private void refreshOverview() {
    clearWarning();
    
    transactionTable.getItems().clear();
    
    Bookkeeping chosenBookkeeping = isTotal
        ? chosenMonthlyBookkeeping.getTotalBookkeeping(isAccounting)
        : chosenMonthlyBookkeeping.getBookkeeping(isAccounting, isPersonal);
    
    transactionTable.getItems().addAll(chosenBookkeeping.getTransactions());
    
    String incomeLabelBuilder = (isTotal ? "Total " : (isPersonal ? "Personal " : "Work ")) +
        (isAccounting ? "accounting " : "budgeting ") + "income";
    incomeLabel.setText(incomeLabelBuilder);
    
    double totalIncome = chosenBookkeeping.getTotalIncome();
    incomeAmountLabel.setText(totalIncome + "kr");
    
    String expenseLabelBuilder = (isTotal ? "Total " : (isPersonal ? "Personal " : "Work ")) +
        (isAccounting ? "accounting " : "budgeting ") + "expenses";
    expenseLabel.setText(expenseLabelBuilder);
    
    double totalExpense = chosenBookkeeping.getTotalExpense();
    expenseAmountLabel.setText(totalExpense + "kr");

    totalAmountLabel.setText((totalIncome - totalExpense) + "kr");
    
    dateField.setDisable(isTotal);
    descriptionField.setDisable(isTotal);
    categoryField.setDisable(isTotal);
    amountField.setDisable(isTotal);
    addTransactionButton.setDisable(isTotal);
    deleteTransactionButton.setDisable(isTotal);
    
    if (user.getMonthlyBookkeepingRegistry().isYearEmpty(chosenYearMonth)) {
      setWarning("There are no transactions for this whole year. If you quit now, the year will " +
          "be deleted.");
    }
    
    setCellColor();
  }
  
  private void setWarning(String warning) {
    warningLabel.setText(warning);
    warningLabel.setVisible(true);
  }
  
  private void clearWarning() {
    warningLabel.setVisible(false);
  }
  
  private void saveUser() {
    try {
      FileHandling.writeUserToFile(user);
    } catch (Exception e) {
      setWarning("Could not save user data" + e.getMessage());
    }
  }

  public void switchTheme() {
    BudgetAndAccountingApp.setTheme();
  }
}
