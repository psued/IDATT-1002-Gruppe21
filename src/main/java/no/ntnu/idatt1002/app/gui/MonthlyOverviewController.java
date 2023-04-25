package no.ntnu.idatt1002.app.gui;

import java.io.IOException;
import java.time.LocalDate;
import java.time.Month;
import java.time.YearMonth;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.util.Callback;
import no.ntnu.idatt1002.app.BudgetAndAccountingApp;
import no.ntnu.idatt1002.app.User;
import no.ntnu.idatt1002.app.bookkeeping.Bookkeeping;
import no.ntnu.idatt1002.app.filehandling.FileHandling;
import no.ntnu.idatt1002.app.registers.MonthlyBookkeeping;
import no.ntnu.idatt1002.app.registers.Project;
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
  
  @FXML private DatePicker dateFieldIncome;
  @FXML private TextField descriptionFieldIncome;
  @FXML private TextField categoryFieldIncome;
  @FXML private TextField amountFieldIncome;
  @FXML private Button addIncomeButton;
  @FXML private Button deleteIncomeButton;

  @FXML private DatePicker dateFieldExpense;
  @FXML private TextField descriptionFieldExpense;
  @FXML private TextField categoryFieldExpense;
  @FXML private TextField amountFieldExpense;
  @FXML private Button addExpenseButton;
  @FXML private Button deleteExpenseButton;
  
  @FXML private Label incomeLabel;
  @FXML private Label incomeAmountLabel;

  @FXML private Label expenseLabel;
  @FXML private Label expenseAmountLabel;
  
  @FXML private Label totalAmountLabel;
  
  @FXML private Label warningLabel;

  @FXML private PieChart pieIncome;
  @FXML private PieChart pieExpense;

  
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

    transactionTable.setRowFactory(tv -> new TableRow<Transaction>() {
      @Override
      public void updateItem(Transaction item, boolean empty) {
        super.updateItem(item, empty);
        if (item == null || empty) {
          setStyle("");
        } else {
          if (item instanceof Income) {
            setStyle("-fx-background-color: rgba(0,128,0,0.5)");
          }
          else {
            setStyle("-fx-background-color: rgba(255,0,0,0.5)");
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
  void addIncome() {
    LocalDate date = dateFieldIncome.getValue();
    String description = descriptionFieldIncome.getText();
    String category = categoryFieldIncome.getText();
    double amount = Double.parseDouble(amountFieldIncome.getText());
    
    boolean isAccounting = toggleBookkeeping.isSelected();
    boolean isPersonal = toggleType.isSelected();

    Transaction selectedTransaction = transactionTable.getSelectionModel().getSelectedItem();

    if (selectedTransaction != null) {
      selectedTransaction.setDate(date);
      selectedTransaction.setDescription(description);
      selectedTransaction.setCategory(category);
      selectedTransaction.setAmount(amount > 0 ? amount : -amount);
    }

    else {
      chosenMonthlyBookkeeping.getBookkeeping(isAccounting, isPersonal).addTransaction(new Income(description, category, amount, date));
    }

    saveUser();
    refreshOverview();
  }

  @FXML
  void addExpense() {
    LocalDate date = dateFieldExpense.getValue();
    String description = descriptionFieldExpense.getText();
    String category = categoryFieldExpense.getText();
    double amount = Double.parseDouble(amountFieldExpense.getText());

    boolean isAccounting = toggleBookkeeping.isSelected();
    boolean isPersonal = toggleType.isSelected();

    Transaction selectedTransaction = transactionTable.getSelectionModel().getSelectedItem();

    if (selectedTransaction != null) {
      selectedTransaction.setDate(date);
      selectedTransaction.setDescription(description);
      selectedTransaction.setCategory(category);
      selectedTransaction.setAmount(amount > 0 ? amount : -amount);
    }

    else {
      chosenMonthlyBookkeeping.getBookkeeping(isAccounting, isPersonal).addTransaction(new Expense(description, category, amount, date));
    }

    saveUser();
    refreshOverview();
  }

  @FXML
  public void deleteIncome() {
    Transaction transaction = transactionTable.getSelectionModel().getSelectedItem();

    if (transaction instanceof Income) {
      boolean isAccounting = toggleBookkeeping.isSelected();
      boolean isPersonal = toggleType.isSelected();

      chosenMonthlyBookkeeping.getBookkeeping(isAccounting, isPersonal).removeTransaction(transaction);

      User.getInstance().getMonthlyBookkeepingRegistry().removeMonthlyBookkeeping(chosenYearMonth);
      User.getInstance().getMonthlyBookkeepingRegistry().addMonthlyBookkeeping(chosenMonthlyBookkeeping);

      saveUser();
      selectTransaction();
      refreshOverview();
    }
  }

  public void deleteExpense() {
    Transaction transaction = transactionTable.getSelectionModel().getSelectedItem();

    if (transaction instanceof Expense) {
      boolean isAccounting = toggleBookkeeping.isSelected();
      boolean isPersonal = toggleType.isSelected();

      chosenMonthlyBookkeeping.getBookkeeping(isAccounting, isPersonal).removeTransaction(transaction);

      User.getInstance().getMonthlyBookkeepingRegistry().removeMonthlyBookkeeping(chosenYearMonth);
      User.getInstance().getMonthlyBookkeepingRegistry().addMonthlyBookkeeping(chosenMonthlyBookkeeping);

      saveUser();
      selectTransaction();
      refreshOverview();
    }
  }

  
  /**
   * Checks if the user has selected a transaction in the table and updates the
   * selectedTransaction and the text fields accordingly.
   */
  @FXML
  public void selectTransaction() {
    Transaction selectedTransaction = transactionTable.getSelectionModel().getSelectedItem();

    if (selectedTransaction instanceof Income) {
      dateFieldIncome.setValue(selectedTransaction.getDate());
      descriptionFieldIncome.setText(selectedTransaction.getDescription());
      categoryFieldIncome.setText(selectedTransaction.getCategory());
      amountFieldIncome.setText(String.valueOf(selectedTransaction.getAmount()));
    }
    else {
      dateFieldExpense.setValue(selectedTransaction.getDate());
      descriptionFieldExpense.setText(selectedTransaction.getDescription());
      categoryFieldExpense.setText(selectedTransaction.getCategory());
      amountFieldExpense.setText(String.valueOf(selectedTransaction.getAmount()));
    }
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
    
    dateFieldIncome.setDisable(isTotal);
    descriptionFieldIncome.setDisable(isTotal);
    categoryFieldIncome.setDisable(isTotal);
    amountFieldIncome.setDisable(isTotal);
    addIncomeButton.setDisable(isTotal);
    deleteIncomeButton.setDisable(isTotal);

    dateFieldExpense.setDisable(isTotal);
    descriptionFieldExpense.setDisable(isTotal);
    categoryFieldExpense.setDisable(isTotal);
    amountFieldExpense.setDisable(isTotal);
    addExpenseButton.setDisable(isTotal);
    deleteExpenseButton.setDisable(isTotal);

    if (User.getInstance().getMonthlyBookkeepingRegistry().isYearEmpty(chosenYearMonth)) {
      setWarning("There are no transactions for this whole year. If you quit now, the year will " +
          "be deleted.");
    }
    
    setCellColor();
    updatePieCharts();
  }

  private void updatePieCharts() {
    // Update pieChart income
    ObservableList<PieChart.Data> pieChartDataIncome = FXCollections.observableArrayList();
    ObservableList<PieChart.Data> pieChartDataExpense = FXCollections.observableArrayList();

    HashMap<String, Double> categoriesIncome = new HashMap<>();
    HashMap<String, Double> categoriesExpense = new HashMap<>();

    for (int i = 0; i < transactionTable.getItems().size(); i++) {
      String category = transactionTable.getItems().get(i).getCategory();
      Double amount = transactionTable.getItems().get(i).getAmount();

      if (transactionTable.getItems().get(i).getClass().equals(Income.class)) {
        if (categoriesIncome.containsKey(category)) {
          Double currentAmount = categoriesIncome.get(category);
          categoriesIncome.put(category, currentAmount + amount);
        } else {
          categoriesIncome.put(category, amount);
        }
      }
      else {
        if (categoriesExpense.containsKey(category)) {
          Double currentAmount = categoriesExpense.get(category);
          categoriesExpense.put(category, currentAmount + amount);
        } else {
          categoriesExpense.put(category, amount);
        }
      }
    }

    for (Map.Entry<String, Double> entry : categoriesIncome.entrySet()) {
      String categoryIncome = entry.getKey();
      Double amountIncome = entry.getValue();

      pieChartDataIncome.add(new PieChart.Data(categoryIncome, amountIncome));
    }

    for (Map.Entry<String, Double> entry : categoriesExpense.entrySet()) {
      String categoryExpense = entry.getKey();
      Double amountExpense = entry.getValue();

      pieChartDataExpense.add(new PieChart.Data(categoryExpense, amountExpense));
    }

    pieIncome.setData(pieChartDataIncome);
    pieExpense.setData(pieChartDataExpense);
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

  public void projects() {
    try {
      Parent root = FXMLLoader.load(
        Objects.requireNonNull(getClass().getResource("/AllProjects.fxml")));
      BudgetAndAccountingApp.setRoot(root);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public void start() {
    try {
      Parent root = FXMLLoader.load(
        Objects.requireNonNull(getClass().getResource("/Start.fxml")));
      BudgetAndAccountingApp.setRoot(root);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
