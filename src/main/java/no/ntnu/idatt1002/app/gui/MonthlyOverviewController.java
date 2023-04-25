package no.ntnu.idatt1002.app.gui;

import java.time.LocalDate;
import java.time.Month;
import java.time.YearMonth;
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
import javafx.scene.control.ToggleButton;
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
  private YearMonth chosenYearMonth;
  private MonthlyBookkeeping chosenMonthlyBookkeeping;
  
  @FXML private ToggleButton toggleBookkeeping;
  @FXML private ToggleButton toggleType;
  @FXML private ToggleButton toggleTotal;
  
  @FXML private ComboBox<Month> monthComboBox;
  @FXML private ComboBox<String> yearComboBox;
  
  @FXML private TableView<Transaction> transactionTable;
  @FXML private TableColumn<Transaction, LocalDate> dateColumn;
  @FXML private TableColumn<Transaction, String> descriptionColumn;
  @FXML private TableColumn<Transaction, String> categoryColumn;
  @FXML private TableColumn<Transaction, Double> amountColumn;
  
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
    
    if (User.getInstance().getMonthlyBookkeepingRegistry().getMonthlyBookkeepingMap().isEmpty()) {
      chosenYearMonth = YearMonth.now();
      chosenMonthlyBookkeeping = new MonthlyBookkeeping(chosenYearMonth);
      User.getInstance().getMonthlyBookkeepingRegistry().addMonthlyBookkeeping(chosenMonthlyBookkeeping);
      saveUser();
    } else {
      chosenYearMonth = User.getInstance().getMonthlyBookkeepingRegistry().getMonthlyBookkeepingMap().keySet().iterator().next();
      chosenMonthlyBookkeeping = User.getInstance().getMonthlyBookkeepingRegistry().getMonthlyBookkeepingMap().get(
          chosenYearMonth);
    }
    
    // Set up the month combo box
    monthComboBox.setValue(chosenYearMonth.getMonth());
    monthComboBox.getItems().addAll(Month.values());
    monthComboBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
      chosenYearMonth = chosenYearMonth.withMonth(newValue.getValue());
      MonthlyBookkeeping monthlyBookkeeping = User.getInstance().getMonthlyBookkeepingRegistry().getMonthlyBookkeeping(chosenYearMonth);
      chosenMonthlyBookkeeping = monthlyBookkeeping == null
          ? new MonthlyBookkeeping(chosenYearMonth)
          : User.getInstance().getMonthlyBookkeepingRegistry().getMonthlyBookkeeping(chosenYearMonth);
      
      refreshOverview();
    });
    
    
    // Set up the year combo box
    yearComboBox.setValue(Integer.toString(chosenYearMonth.getYear()));
    yearComboBox.getItems().addAll(
        User.getInstance().getMonthlyBookkeepingRegistry().getMonthlyBookkeepingMap().keySet().stream().map(YearMonth::getYear).distinct().sorted().map(String::valueOf).toArray(String[]::new));
    yearComboBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
      //Refresh the month combo box to show the months that have transactions
      chosenYearMonth = chosenYearMonth.withYear(Integer.parseInt(newValue));
      MonthlyBookkeeping monthlyBookkeeping = User.getInstance().getMonthlyBookkeepingRegistry().getMonthlyBookkeeping(chosenYearMonth);
      
      chosenMonthlyBookkeeping = monthlyBookkeeping == null
          ? new MonthlyBookkeeping(chosenYearMonth)
          : User.getInstance().getMonthlyBookkeepingRegistry().getMonthlyBookkeeping(chosenYearMonth);
      
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
            setText(String.valueOf(item));
          }
        }
      }
    });
    
    
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
              MonthlyBookkeeping monthlyBookkeeping = User.getInstance().getMonthlyBookkeepingRegistry().getMonthlyBookkeeping(yearMonth);
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
    
    boolean isAccounting = toggleBookkeeping.isSelected();
    boolean isPersonal = toggleType.isSelected();
    
    Transaction selectedTransaction = transactionTable.getSelectionModel().getSelectedItem();
    
    if (selectedTransaction != null) {
      if (amount > 0 && selectedTransaction instanceof Expense) {
        chosenMonthlyBookkeeping.getBookkeeping(isAccounting, isPersonal).updateTransaction(selectedTransaction, new Income(description, category, amount, date));
      } else if (amount < 0 && selectedTransaction instanceof Income) {
       chosenMonthlyBookkeeping.getBookkeeping(isAccounting, isPersonal).updateTransaction(selectedTransaction, new Expense(description, category, -amount, date));
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
    
    saveUser();
    refreshOverview();
  }
  
  @FXML
  void deleteTransaction() {
    Transaction transaction = transactionTable.getSelectionModel().getSelectedItem();
    
    boolean isAccounting = toggleBookkeeping.isSelected();
    boolean isPersonal = toggleType.isSelected();
    
    chosenMonthlyBookkeeping.getBookkeeping(isAccounting, isPersonal).removeTransaction(transaction);
    
    User.getInstance().getMonthlyBookkeepingRegistry().removeMonthlyBookkeeping(chosenYearMonth);
    User.getInstance().getMonthlyBookkeepingRegistry().addMonthlyBookkeeping(chosenMonthlyBookkeeping);
    
    saveUser();
    selectTransaction();
    refreshOverview();
  }
  
  /**
   * Checks if the user has selected a transaction in the table and updates the
   * selectedTransaction and the text fields accordingly.
   */
  @FXML
  public void selectTransaction() {
    Transaction selectedTransaction = transactionTable.getSelectionModel().getSelectedItem();
    
    if (selectedTransaction == null) {
      dateField.setValue(null);
      descriptionField.setText("");
      categoryField.setText("");
      amountField.setText("");
      return;
    }
    
    dateField.setValue(selectedTransaction.getDate());
    descriptionField.setText(selectedTransaction.getDescription());
    categoryField.setText(selectedTransaction.getCategory());
    amountField.setText(selectedTransaction instanceof Expense
        ? String.valueOf(-selectedTransaction.getAmount())
        : String.valueOf(selectedTransaction.getAmount()));
  }
  
  @FXML
  public void refreshOverview() {
    clearWarning();
    
    transactionTable.getItems().clear();
    
    boolean isAccounting = toggleBookkeeping.isSelected();
    boolean isTotal = toggleTotal.isSelected();
    
    boolean isPersonal = toggleType.isSelected();
    toggleType.setDisable(isTotal);
    
    Bookkeeping chosenBookkeeping = isTotal
        ? chosenMonthlyBookkeeping.getTotalBookkeeping(isAccounting)
        : chosenMonthlyBookkeeping.getBookkeeping(isAccounting, isPersonal);
    
    transactionTable.getItems().addAll(chosenBookkeeping.getTransactions());
    
    String incomeLabelBuilder = (isTotal ? "Total " : (isPersonal ? "Personal " : "Work ")) +
        (isAccounting ? "accounting " : "budgeting ") + "income";
    incomeLabel.setText(incomeLabelBuilder);
    
    String expenseLabelBuilder = (isTotal ? "Total " : (isPersonal ? "Personal " : "Work ")) +
        (isAccounting ? "accounting " : "budgeting ") + "expenses";
    expenseLabel.setText(expenseLabelBuilder);
    
    double totalIncome = chosenBookkeeping.getTotalIncome();
    incomeAmountLabel.setText(totalIncome + "kr");
    double totalExpense = chosenBookkeeping.getTotalExpense();
    expenseAmountLabel.setText(totalExpense + "kr");
    
    totalAmountLabel.setText((totalIncome - totalExpense) + "kr");
    
    dateField.setDisable(isTotal);
    descriptionField.setDisable(isTotal);
    categoryField.setDisable(isTotal);
    amountField.setDisable(isTotal);
    addTransactionButton.setDisable(isTotal);
    deleteTransactionButton.setDisable(isTotal);
    
    
    if (User.getInstance().getMonthlyBookkeepingRegistry().isYearEmpty(chosenYearMonth)) {
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
      if (User.getInstance().getMonthlyBookkeepingRegistry().getMonthlyBookkeeping(chosenYearMonth) == null) {
        User.getInstance().getMonthlyBookkeepingRegistry().addMonthlyBookkeeping(chosenMonthlyBookkeeping);
      } else {
        User.getInstance().getMonthlyBookkeepingRegistry().updateMonthlyBookkeeping(chosenMonthlyBookkeeping);
      }
      
      FileHandling.writeUserToFile(User.getInstance());
    } catch (Exception e) {
      setWarning("Could not save user data" + e.getMessage());
    }
  }
  
  public void switchTheme() {
    BudgetAndAccountingApp.setTheme();
  }
}
