package no.ntnu.idatt1002.app.gui;

import java.io.IOException;
import java.time.LocalDate;
import java.time.Month;
import java.time.YearMonth;
import java.time.format.DateTimeParseException;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;
import no.ntnu.idatt1002.app.BudgetAndAccountingApp;
import no.ntnu.idatt1002.app.User;
import no.ntnu.idatt1002.app.bookkeeping.Bookkeeping;
import no.ntnu.idatt1002.app.filehandling.FileHandling;
import no.ntnu.idatt1002.app.registers.MonthlyBookkeeping;
import no.ntnu.idatt1002.app.transactions.Expense;
import no.ntnu.idatt1002.app.transactions.Income;
import no.ntnu.idatt1002.app.transactions.Transaction;


/**
 * Controller class for the MonthlyOverview.fxml file. Displays a list of all the transactions
 * for an entire month and allows the user to add and delete transactions.
 */
public class MonthlyOverviewController {
  // The chosen year and month
  private YearMonth chosenYearMonth;
  // The chosen monthly bookkeeping, is the same as the chosen year and month
  private MonthlyBookkeeping chosenMonthlyBookkeeping;
  
  // Toggle buttons for the table columns
  @FXML
  private ToggleButton toggleBookkeeping;
  @FXML
  private ToggleButton toggleType;
  @FXML
  private ToggleButton toggleTotal;
  
  // The month and year combo boxes
  @FXML
  private ComboBox<Month> monthComboBox;
  @FXML
  private ComboBox<String> yearComboBox;
  @FXML
  private Button addYearButton;
  
  // The table and its columns
  @FXML
  private Label tableLabel;
  @FXML
  private TableView<Transaction> transactionTable;
  @FXML
  private TableColumn<Transaction, LocalDate> dateColumn;
  @FXML
  private TableColumn<Transaction, String> descriptionColumn;
  @FXML
  private TableColumn<Transaction, String> categoryColumn;
  @FXML
  private TableColumn<Transaction, Double> amountColumn;
  
  // The income fields
  @FXML
  private DatePicker dateFieldIncome;
  @FXML
  private Button dateResetIncome;
  @FXML
  private TextField descriptionFieldIncome;
  @FXML
  private TextField categoryFieldIncome;
  @FXML
  private TextField amountFieldIncome;
  @FXML
  private Button addIncomeButton;
  @FXML
  private Button deleteIncomeButton;
  
  // The expense fields
  @FXML
  private DatePicker dateFieldExpense;
  @FXML
  private Button dateResetExpense;
  @FXML
  private TextField descriptionFieldExpense;
  @FXML
  private TextField categoryFieldExpense;
  @FXML
  private TextField amountFieldExpense;
  @FXML
  private Button addExpenseButton;
  @FXML
  private Button deleteExpenseButton;
  
  // The total labels
  @FXML
  private Label incomeLabel;
  @FXML
  private Label incomeAmountLabel;
  @FXML
  private Label expenseLabel;
  @FXML
  private Label expenseAmountLabel;
  @FXML
  private Label totalAmountLabel;
  
  // The warning label
  @FXML
  private Label warningLabel;
  
  // The pie charts
  @FXML
  private PieChart pieIncome;
  @FXML
  private PieChart pieExpense;
  
  /**
   * Initializes the controller class by displaying the transactions for the current month.
   *
   * <p>If there are no existing months in the user singleton class, a new MonthlyBookkeeping
   * object set in the current month is created and added to the user singleton class. If there
   * are, the earliest month is chosen with the current month.
   *
   * <p>Everytime a transaction is added, updated or deleted, the user singleton class is saved
   * to file. This means that the user can quit the application at any time and all changes are
   * saved.
   */
  @FXML
  public void initialize() {
    // Choose the earliest month in the monthly bookkeeping registry
    // If there are no months, create a new one with the current month
    if (User.getInstance().getMonthlyBookkeepingRegistry().getMonthlyBookkeepingMap().isEmpty()) {
      chosenYearMonth = YearMonth.now();
      chosenMonthlyBookkeeping = new MonthlyBookkeeping(chosenYearMonth);
      User.getInstance().getMonthlyBookkeepingRegistry()
          .putMonthlyBookkeeping(chosenMonthlyBookkeeping);
      saveUser();
    } else {
      // Automatically choose the earliest year with the current month
      chosenYearMonth = YearMonth.of(
          User.getInstance().getMonthlyBookkeepingRegistry().getMonthlyBookkeepingMap().keySet()
              .stream().distinct().min(Comparator.naturalOrder()).get().getYear(),
          YearMonth.now().getMonth());
      
      chosenMonthlyBookkeeping =
          User.getInstance().getMonthlyBookkeepingRegistry().getMonthlyBookkeepingMap()
              .get(chosenYearMonth);
    }
    
    // Set up the month combo box
    monthComboBox.setValue(chosenYearMonth.getMonth());
    monthComboBox.getItems().addAll(Month.values());
    // Add a listener that updates the chosen month when a new month is chosen
    monthComboBox.getSelectionModel().selectedItemProperty()
        .addListener((observable, oldValue, newValue) -> {
          chosenYearMonth = chosenYearMonth.withMonth(newValue.getValue());
          MonthlyBookkeeping monthlyBookkeeping = User.getInstance().getMonthlyBookkeepingRegistry()
              .getMonthlyBookkeeping(chosenYearMonth);
          chosenMonthlyBookkeeping =
              monthlyBookkeeping == null ? new MonthlyBookkeeping(chosenYearMonth) :
                  User.getInstance().getMonthlyBookkeepingRegistry()
                      .getMonthlyBookkeeping(chosenYearMonth);
          
          refreshOverview();
        });
    
    
    // Set up the year combo box
    yearComboBox.setValue(Integer.toString(chosenYearMonth.getYear()));
    yearComboBox.getItems().addAll(
        User.getInstance().getMonthlyBookkeepingRegistry().getMonthlyBookkeepingMap().keySet()
            .stream().map(YearMonth::getYear).distinct().sorted().map(String::valueOf)
            .toArray(String[]::new));
    
    // Add a listener that updates the chosen year when a new year is chosen
    yearComboBox.getSelectionModel().selectedItemProperty()
        .addListener((observable, oldValue, newValue) -> {
          //Refresh the month combo box to show the months that have transactions
          chosenYearMonth = chosenYearMonth.withYear(Integer.parseInt(newValue));
          MonthlyBookkeeping monthlyBookkeeping = User.getInstance().getMonthlyBookkeepingRegistry()
              .getMonthlyBookkeeping(chosenYearMonth);
          
          chosenMonthlyBookkeeping =
              monthlyBookkeeping == null ? new MonthlyBookkeeping(chosenYearMonth) :
                  User.getInstance().getMonthlyBookkeepingRegistry()
                      .getMonthlyBookkeeping(chosenYearMonth);
          
          refreshOverview();
        });
    
    // Add a listener to this button
    //Will prompt the user to add a new year to the year combo box and give a warning if the year
    // is invalid or already exists
    addYearButton.setOnAction(event -> {
      //Set up the dialog
      TextInputDialog dialog = new TextInputDialog();
      dialog.setTitle("Add year");
      dialog.setHeaderText(
          "Enter a new year between 2000 and " + YearMonth.now().plusYears(100).getYear());
      Optional<String> result = dialog.showAndWait();
      
      //If the user entered a year
      result.ifPresent(year -> {
        try {
          //Parse the input
          int newYear = Integer.parseInt(year);
          int maxYear = YearMonth.now().plusYears(100).getYear();
          List<Integer> years = yearComboBox.getItems().stream().map(Integer::parseInt).toList();
          
          //User can only add years between 2000 and 100 years from now
          if (newYear < 2000 || newYear > maxYear) {
            throw new IllegalArgumentException("Year must be between 2000 and " + maxYear);
          } else if (years.contains(newYear)) {
            throw new IllegalArgumentException("Year already exists");
          }
          
          //Add the year to the combo box and sort it
          yearComboBox.getItems().add(year);
          yearComboBox.getItems().sort(Comparator.comparingInt(Integer::parseInt));
          yearComboBox.setValue(Integer.toString(newYear));
          
          refreshOverview();
        } catch (NumberFormatException e) {
          yearComboBox.setValue(Integer.toString(chosenYearMonth.getYear()));
          yearComboBox.getEditor().setText(Integer.toString(chosenYearMonth.getYear()));
          setWarning("Year must be a number");
        } catch (IllegalArgumentException e) {
          yearComboBox.setValue(Integer.toString(chosenYearMonth.getYear()));
          yearComboBox.getEditor().setText(Integer.toString(chosenYearMonth.getYear()));
          setWarning(e.getMessage());
        }
      });
    });
    
    // Set up the transaction table
    dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
    descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
    categoryColumn.setCellValueFactory(new PropertyValueFactory<>("category"));
    amountColumn.setCellValueFactory(new PropertyValueFactory<>("amount"));
    
    // Set the cell color to green for income and red for expense
    transactionTable.setRowFactory(tv -> new TableRow<>() {
      @Override
      public void updateItem(Transaction item, boolean empty) {
        super.updateItem(item, empty);
        if (item == null || empty) {
          setStyle("");
        } else {
          if (item instanceof Income) {
            setStyle("-fx-background-color: rgba(0,128,0,0.5)");
          } else {
            setStyle("-fx-background-color: rgba(255,0,0,0.5)");
          }
        }
      }
    });
    
    // Set the default bookkeeping to be accounting
    toggleBookkeeping.setSelected(true);
    
    // Set up listeners for the date pickers that allows the user to remove a date from the field
    dateResetIncome.setOnAction(e -> dateFieldIncome.setValue(null));
    dateResetExpense.setOnAction(e -> dateFieldExpense.setValue(null));

    // Refresh the overview
    refreshOverview();
  }
  
  /**
   * Sets the cell color of the month combo box to green if the month has transactions and white
   * if not.
   *
   * <p>This process must be run as a menu as normal listeners do not work in this use case.
   * Specifically when adding a new year.
   */
  private void setMonthCellColor() {
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
              MonthlyBookkeeping monthlyBookkeeping =
                  User.getInstance().getMonthlyBookkeepingRegistry()
                      .getMonthlyBookkeeping(yearMonth);
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
  
  /**
   * Adds an Income to the chosen MonthlyBookkeeping object if no income is selected in the table.
   * In the case that an income is selected, the selected income will be updated with the new
   * values.
   *
   * <p>Will give a warning if the user has entered invalid values.
   */
  @FXML
  void addIncome() {
    try {
      // Set the values of the new income
      LocalDate date = dateFieldIncome.getValue();
      String description = descriptionFieldIncome.getText();
      String category = categoryFieldIncome.getText();
      double amount = Double.parseDouble(amountFieldIncome.getText());
      
      // Determine what bookkeeping to add the income to
      boolean isAccounting = toggleBookkeeping.isSelected();
      boolean isPersonal = toggleType.isSelected();
      
      // Get the selected transaction
      Transaction selectedTransaction = transactionTable.getSelectionModel().getSelectedItem();

      // If an income is selected, update the income with the new values
      // Otherwise, add a new income
      if (selectedTransaction instanceof Income) {
        Income newIncome = new Income(description, category, amount, date);
        newIncome.setDate(date);
        newIncome.setDescription(description);
        newIncome.setCategory(category);
        newIncome.setAmount(amount);
        
        chosenMonthlyBookkeeping.getBookkeeping(isAccounting, isPersonal)
            .updateTransaction(selectedTransaction, newIncome);
      } else {
        chosenMonthlyBookkeeping.getBookkeeping(isAccounting, isPersonal)
            .addTransaction(new Income(description, category, amount, date));
      }
      
      // Save the user and refresh the overview
      saveUser();
      refreshOverview();
    } catch (DateTimeParseException e) {
      setWarning("Please enter a valid date");
    } catch (NumberFormatException e) {
      setWarning("Please enter a valid amount");
    } catch (IllegalArgumentException e) {
      setWarning(e.getMessage());
    }
  }

  /**
   * Adds an Expense to the chosen MonthlyBookkeeping object if no expense is selected in the
   * table. In the case that an expense is selected, the selected expense will be updated with the
   * new values.
   *
   * <p>Will give a warning if the user has entered invalid values.
   */
  @FXML
  void addExpense() {
    try {
      // Set the values of the new expense
      LocalDate date = dateFieldExpense.getValue();
      String description = descriptionFieldExpense.getText();
      String category = categoryFieldExpense.getText();
      double amount = Double.parseDouble(amountFieldExpense.getText());

      // Determine what bookkeeping to add the expense to
      boolean isAccounting = toggleBookkeeping.isSelected();
      boolean isPersonal = toggleType.isSelected();

      // Get the selected transaction
      Transaction selectedTransaction = transactionTable.getSelectionModel().getSelectedItem();

      // If an expense is selected, update the expense with the new values
      // Otherwise, add a new expense
      if (selectedTransaction instanceof Expense) {
        Expense newExpense = new Expense(description, category, amount, date);
        newExpense.setDate(date);
        newExpense.setDescription(description);
        newExpense.setCategory(category);
        newExpense.setAmount(amount);

        chosenMonthlyBookkeeping.getBookkeeping(isAccounting, isPersonal)
            .updateTransaction(selectedTransaction, newExpense);
      } else {
        chosenMonthlyBookkeeping.getBookkeeping(isAccounting, isPersonal)
            .addTransaction(new Expense(description, category, amount, date));
      }

      // Save the user and refresh the overview
      saveUser();
      refreshOverview();
    } catch (NumberFormatException e) {
      setWarning("Please enter a valid amount");
    } catch (IllegalArgumentException e) {
      setWarning(e.getMessage());
    }
  }

  /**
   * Deletes the selected income from the chosen MonthlyBookkeeping object.
   *
   * <p>Will give a warning if no income is selected.
   */
  @FXML
  public void deleteIncome() {
    Transaction transaction = transactionTable.getSelectionModel().getSelectedItem();

    if (transaction instanceof Income) {
      boolean isAccounting = toggleBookkeeping.isSelected();
      boolean isPersonal = toggleType.isSelected();

      chosenMonthlyBookkeeping.getBookkeeping(isAccounting, isPersonal)
          .removeTransaction(transaction);

      dateFieldIncome.setValue(null);
      descriptionFieldIncome.setText("");
      categoryFieldIncome.setText("");
      amountFieldIncome.setText("");

      saveUser();
      refreshOverview();
    } else {
      setWarning("Please select an income to delete");
    }
  }

  /**
   * Deletes the selected expense from the chosen MonthlyBookkeeping object.
   *
   * <p>Will give a warning if no expense is selected.
   */
  @FXML
  public void deleteExpense() {
    Transaction transaction = transactionTable.getSelectionModel().getSelectedItem();

    if (transaction instanceof Expense) {
      boolean isAccounting = toggleBookkeeping.isSelected();
      boolean isPersonal = toggleType.isSelected();

      chosenMonthlyBookkeeping.getBookkeeping(isAccounting, isPersonal)
          .removeTransaction(transaction);

      dateFieldExpense.setValue(null);
      descriptionFieldExpense.setText("");
      categoryFieldExpense.setText("");
      amountFieldExpense.setText("");

      saveUser();
      refreshOverview();
    } else {
      setWarning("Please select an expense to delete");
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
    } else if (selectedTransaction instanceof Expense) {
      dateFieldExpense.setValue(selectedTransaction.getDate());
      descriptionFieldExpense.setText(selectedTransaction.getDescription());
      categoryFieldExpense.setText(selectedTransaction.getCategory());
      amountFieldExpense.setText(String.valueOf(selectedTransaction.getAmount()));
    }
  }


  /**
   * Refreshes the overview table with the transactions of the chosen MonthlyBookkeeping object.
   */
  @FXML
  public void refreshOverview() {
    // Clear the warning
    clearWarning();
    
    // Clear the table
    transactionTable.getItems().clear();
    
    // Determine what bookkeeping to show
    boolean isAccounting = toggleBookkeeping.isSelected();
    boolean isTotal = toggleTotal.isSelected();
    boolean isPersonal = toggleType.isSelected();
    
    // Disable the toggle type if the user is viewing the total bookkeeping
    toggleType.setDisable(isTotal);
    
    // Set the table title
    tableLabel.setText((isTotal ? "Total " :
        (isPersonal ? "Personal " : "Work ")) + (isAccounting ? "accounting " : "budgeting ")
            + "transactions");
    
    // Get the chosen bookkeeping
    Bookkeeping chosenBookkeeping =
        isTotal ? chosenMonthlyBookkeeping.getTotalBookkeeping(isAccounting) :
            chosenMonthlyBookkeeping.getBookkeeping(isAccounting, isPersonal);
    
    // Add the transactions to the table
    transactionTable.getItems().addAll(chosenBookkeeping.getTransactions());
    
    // Set the labels of the total income and expenses
    String incomeLabelBuilder = (isTotal ? "Total " : (isPersonal ? "Personal " : "Work "))
        + (isAccounting ? "accounting " : "budgeting ") + "income";
    incomeLabel.setText(incomeLabelBuilder);
    String expenseLabelBuilder = (isTotal ? "Total " : (isPersonal ? "Personal " : "Work "))
        + (isAccounting ? "accounting " : "budgeting ") + "expenses";
    expenseLabel.setText(expenseLabelBuilder);
    
    // Set the labels of the total income and expenses
    double totalIncome = chosenBookkeeping.getTotalIncome();
    incomeAmountLabel.setText(totalIncome + "kr");
    double totalExpense = chosenBookkeeping.getTotalExpense();
    expenseAmountLabel.setText(totalExpense + "kr");

    totalAmountLabel.setText((totalIncome - totalExpense) + "kr");
    
    // Disable the input fields and buttons if the user is viewing the total bookkeeping
    // This is necessary as we don't know what bookkeeping type the user wants to add their
    // transaction to.
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

    // Warn the user if there are no transactions for the chosen year as the year will be deleted
    // if the user quits now.
    if (User.getInstance().getMonthlyBookkeepingRegistry().isYearEmpty(chosenYearMonth)) {
      setWarning("This year is empty. It will be deleted if you quit now.");
    }
    
    //Update month cells
    setMonthCellColor();
    
    //Update pie charts
    updatePieCharts();
  }

  /**
   * Updates the pie charts with the data from the chosen MonthlyBookkeeping object.
   *
   * <p>Displays each category as a slice of the pie chart.
   */
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
      } else {
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
  
  /**
   * Sets the warning label to the given warning.
   *
   * @param warning The warning to be displayed.
   */
  private void setWarning(String warning) {
    warningLabel.setText(warning);
    warningLabel.setVisible(true);
  }
  
  /**
   * Clears the warning label.
   */
  private void clearWarning() {
    warningLabel.setVisible(false);
  }
  
  /**
   * Updates the user data with the data from the chosen MonthlyBookkeeping object and writes the
   * user singleton to file.
   *
   * <p>Gives a warning message if the user data could not be saved.
   */
  private void saveUser() {
    try {
      User.getInstance().getMonthlyBookkeepingRegistry()
          .putMonthlyBookkeeping(chosenMonthlyBookkeeping);
      
      FileHandling.writeUserToFile(User.getInstance());
    } catch (Exception e) {
      setWarning("Could not save user data" + e.getMessage());
    }
  }
  
  /**
   * Toggles between the light and dark theme.
   */
  @FXML
  public void switchTheme() {
    BudgetAndAccountingApp.switchTheme();
  }
  
  /**
   * Goes to the all projects page.
   */
  @FXML
  public void projects() {
    try {
      Parent root =
          FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/AllProjects.fxml")));
      BudgetAndAccountingApp.setRoot(root);
    } catch (IOException e) {
      setWarning("Could not load projects, please restart the application");
    }
  }
  
  /**
   * Goes to the start page.
   */
  @FXML
  public void start() {
    try {
      Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/Start.fxml")));
      BudgetAndAccountingApp.setRoot(root);
    } catch (IOException e) {
      setWarning("Could not load start, please restart the application");
    }
  }
}
