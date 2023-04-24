package no.ntnu.idatt1002.app.gui;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.*;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.chart.PieChart;
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
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import no.ntnu.idatt1002.app.BudgetAndAccountingApp;
import no.ntnu.idatt1002.app.User;
import no.ntnu.idatt1002.app.filehandling.FileHandling;
import no.ntnu.idatt1002.app.registers.Project;
import no.ntnu.idatt1002.app.transactions.Expense;
import no.ntnu.idatt1002.app.transactions.Income;
import no.ntnu.idatt1002.app.transactions.Transaction;

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
  @FXML private MenuButton status;
  
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
  @FXML private Button deleteIncomeButton;
  
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
  @FXML private Button deleteExpenseButton;
  
  //Image view
  @FXML private ImageView imagePreview;
  @FXML private Button imageLeft;
  @FXML private Button imageRight;
  @FXML private Button deleteImageButton;
  private final List<File> images = new ArrayList<>();
  private int imageIndex = 0;
  
  //Total income, expense and amount overview
  @FXML private Label totalIncome;
  @FXML private Label totalExpense;
  @FXML private Label totalAmount;

  // Pie charts
  @FXML private PieChart pieIncome;
  @FXML private PieChart pieExpense;
  
  //Error message
  @FXML private Label warningLabel = new Label();
  
  /**
   * Initializes the controller class. Also sets up the text fields and tables to display the
   * data of the project that is being edited.
   */
  public void initializeController(
  ) {
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
    status.setText(originalProject.getStatus());

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

    // Add statuses to the status menu button
    for (String status: tempUser.getProjectRegistry().getStatuses()) {
      MenuItem menuItem = new MenuItem(status);
      menuItem.setOnAction(event -> this.status.setText(menuItem.getText()));
      this.status.getItems().add(menuItem);
    }
    
    // Add option to create new category
    MenuItem newCategoryItem = new MenuItem("-New Category-");
    newCategoryItem.setOnAction(event -> {
      TextInputDialog dialog = new TextInputDialog();
      dialog.setTitle("New Category");
      dialog.setHeaderText("Enter name for new category:");
      Optional<String> result = dialog.showAndWait();
      
      result.ifPresent(name -> {
        MenuItem menuItem = new MenuItem(name);
        menuItem.setOnAction(e -> category.setText(name));
        category.getItems().add(category.getItems().size() - 1, menuItem);
        category.setText(name);
      });
    });
    category.getItems().add(newCategoryItem);
    
    
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
    resetIncomeFields();
    resetExpenseFields();
    
    // Set up the image preview
    images.addAll(originalProject.getImages());
    refreshImages();
    
    warningLabel.setVisible(false);
  }
  
  /**
   * Deletes a category from the user's project registry if it is not used by any projects.
   */
  public void deleteCategory() {
    //Get the category that is selected
    MenuItem chosenCategory = category.getItems().stream()
        .filter(item -> item.getText().equals(category.getText())).findFirst().orElse(null);
    
    try {
      tempUser.getProjectRegistry().removeCategory(category.getText());
      category.getItems().remove(chosenCategory);
      category.setText("");
      
      warningLabel.setVisible(false);
    } catch (IllegalArgumentException e) {
      warningLabel.setText(e.getMessage());
      warningLabel.setVisible(true);
    }
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
      
      deleteIncomeButton.setDisable(false);
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
      
      deleteExpenseButton.setDisable(false);
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
          accountingIncome.add(new Income(
              incomeDescriptionField.getText(), incomeCategoryField.getText(), Double.parseDouble(
              incomeAmountField.getText()), incomeDatePicker.getValue()));
        } else {
          accountingIncome.add(new Income(
              incomeDescriptionField.getText(), incomeCategoryField.getText(), Double.parseDouble(
              incomeAmountField.getText()), incomeDatePicker.getValue()));
        }
      } else {
        if (selectedTransaction != null) {
          budgetingIncome.remove((Income) selectedTransaction);
          budgetingIncome.add(new Income(
              incomeDescriptionField.getText(), incomeCategoryField.getText(), Double.parseDouble(
              incomeAmountField.getText()), incomeDatePicker.getValue()));
        } else {
          budgetingIncome.add(new Income(
              incomeDescriptionField.getText(), incomeCategoryField.getText(), Double.parseDouble(
              incomeAmountField.getText()), incomeDatePicker.getValue()));
        }
      }
      
      refreshLocalOverview();
      resetIncomeFields();
      
    } catch (NumberFormatException e) {
      warningLabel.setText("Please enter a valid amount");
      warningLabel.setVisible(true);
    } catch (IllegalArgumentException e) {
      warningLabel.setText(e.getMessage());
      warningLabel.setVisible(true);
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
          accountingExpense.add(new Expense(
              expenseDescriptionField.getText(), expenseCategoryField.getText(), Double.parseDouble(
              expenseAmountField.getText()), expenseDatePicker.getValue()));
        } else {
          accountingExpense.add(new Expense(
              expenseDescriptionField.getText(), expenseCategoryField.getText(), Double.parseDouble(
              expenseAmountField.getText()), expenseDatePicker.getValue()));
        }
      } else {
        if (selectedTransaction != null) {
          budgetingExpense.remove((Expense) selectedTransaction);
          budgetingExpense.add(new Expense(
              expenseDescriptionField.getText(), expenseCategoryField.getText(), Double.parseDouble(
              expenseAmountField.getText()), expenseDatePicker.getValue()));
        } else {
          budgetingExpense.add(new Expense(
              expenseDescriptionField.getText(), expenseCategoryField.getText(), Double.parseDouble(
              expenseAmountField.getText()), expenseDatePicker.getValue()));
        }
      }
      
      refreshLocalOverview();
      resetExpenseFields();
      
    } catch (NumberFormatException e) {
      warningLabel.setText("Please enter a valid amount");
      warningLabel.setVisible(true);
    } catch (IllegalArgumentException e) {
      warningLabel.setText(e.getMessage());
      warningLabel.setVisible(true);
    }
  }
  
  /**
   * Deletes the selected income from the local overview.
   */
  public void deleteIncome() {
    if (selectedTransaction != null) {
      if (isAccounting) {
        accountingIncome.remove((Income) selectedTransaction);
      } else {
        budgetingIncome.remove((Income) selectedTransaction);
      }
      refreshLocalOverview();
      resetIncomeFields();
    }
  }
  
  /**
   * Deletes the selected expense from the local overview.
   */
  public void deleteExpense() {
    if (selectedTransaction != null) {
      if (isAccounting) {
        accountingExpense.remove((Expense) selectedTransaction);
      } else {
        budgetingExpense.remove((Expense) selectedTransaction);
      }
      refreshLocalOverview();
      resetExpenseFields();
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
    warningLabel.setVisible(false);
    warningLabel.setText("");

    updatePieCharts();
  }

  private void updatePieCharts() {
    // Update pieChart income
    ObservableList<PieChart.Data> pieChartDataIncome = FXCollections.observableArrayList();
    HashMap<String, Double> categoriesIncome = new HashMap<>();

    for (int i = 0; i < incomeTable.getItems().size(); i++) {
      String categoryIncome = incomeTable.getItems().get(i).getCategory();
      Double amountIncome = incomeTable.getItems().get(i).getAmount();

      if(categoriesIncome.containsKey(categoryIncome)){
        Double currentAmount = categoriesIncome.get(categoryIncome);
        categoriesIncome.put(categoryIncome, currentAmount + amountIncome);
      }else{
        categoriesIncome.put(categoryIncome, amountIncome);
      }
    }

    for (Map.Entry<String, Double> entry : categoriesIncome.entrySet()) {
      String categoryIncome = entry.getKey();
      Double amountIncome = entry.getValue();

      pieChartDataIncome.add(new PieChart.Data(categoryIncome, amountIncome));
    }


    // Update pieChart Expense
    ObservableList<PieChart.Data> pieChartDataExpense = FXCollections.observableArrayList();
    HashMap<String, Double> categoriesExpense = new HashMap<>();

    for (int i = 0; i < expenseTable.getItems().size(); i++) {
      String categoryExpense = expenseTable.getItems().get(i).getCategory();
      Double amountExpense = expenseTable.getItems().get(i).getAmount();

      if(categoriesExpense.containsKey(categoryExpense)){
        Double currentAmount = categoriesExpense.get(categoryExpense);
        categoriesExpense.put(categoryExpense, currentAmount + amountExpense);
      }else{
        categoriesExpense.put(categoryExpense, amountExpense);
      }
    }

    for (Map.Entry<String, Double> entry : categoriesExpense.entrySet()) {
      String categoryExpense = entry.getKey();
      Double amountExpense = entry.getValue();

      pieChartDataExpense.add(new PieChart.Data(categoryExpense, amountExpense));
    }

    pieIncome.setData(pieChartDataIncome);
    pieExpense.setData(pieChartDataExpense);
  }
  
  // Resets the income fields
  private void resetIncomeFields() {
    incomeDatePicker.setValue(null);
    incomeDescriptionField.setText("");
    incomeCategoryField.setText("");
    incomeAmountField.setText("");
    deleteIncomeButton.setDisable(true);
  }
  
  // Resets the expense fields
  private void resetExpenseFields() {
    expenseDatePicker.setValue(null);
    expenseDescriptionField.setText("");
    expenseCategoryField.setText("");
    expenseAmountField.setText("");
    deleteExpenseButton.setDisable(true);
  }
  
  /**
   * Lets a user add an image/images from their computer to the project. The images will be
   * previewed in the imageView object
   */
  public void addImage() {
    FileChooser fileChooser = new FileChooser();
    fileChooser.setTitle("Select Image");
    File selectedFile = fileChooser.showOpenDialog(null);
    
    if (selectedFile != null) {
      images.add(selectedFile);
      imageIndex = images.size() - 1;
      
      refreshImages();
    }
  }
  
  /**
   * Lets a user look backwards through added images.
   */
  public void imageIndexBackwards() {
    if (imageIndex > 0) {
      imageIndex--;
      refreshImages();
    }
  }
  
  /**
   * Lets a user look forwards through added images.
   */
  public void imageIndexForwards() {
    if (imageIndex < images.size() - 1) {
      imageIndex++;
      refreshImages();
    }
  }
  
  /**
   * Deletes the currently selected image.
   */
  public void deleteImage() {
    if (imageIndex > 0 && imageIndex == images.size() - 1) {
      images.remove(imageIndex);
      imageIndex--;
    } else {
      images.remove(imageIndex);
    }
    
    refreshImages();
  }
  
  /**
   * Refreshes the image preview and the buttons to navigate between images. Will disable the
   */
  private void refreshImages() {
    if (images.isEmpty()) {
      imageLeft.setDisable(true);
      imageRight.setDisable(true);
      imagePreview.setImage(null);
      deleteImageButton.setDisable(true);
      return;
    }
    
    Image image = new Image(images.get(imageIndex).toURI().toString());
    imagePreview.setImage(image);
    
    deleteImageButton.setDisable(false);
    imageLeft.setDisable(imageIndex == 0);
    imageRight.setDisable(imageIndex == images.size() - 1);
  }
  
  /**
   * Updates the project with the new values and saves it to the user registry. It does this by
   * deleting the old project and adding the new one.
   */
  public void saveProject() {
    try {
      Project project = new Project(name.getText(), description.getText(), category.getText(),
          dueDate.getValue(), status.getText());
  
      accountingIncome.forEach(project.getAccounting()::addIncome);
      accountingExpense.forEach(project.getAccounting()::addExpense);
      budgetingIncome.forEach(project.getBudgeting()::addIncome);
      budgetingExpense.forEach(project.getBudgeting()::addExpense);
      
      images.forEach(project::addImage);
      
      tempUser.getProjectRegistry().removeProject(originalProject);
      tempUser.getProjectRegistry().addProject(project);
      
      try {
        FileHandling.writeUserToFile(tempUser);
        Parent root = FXMLLoader.load(Objects.requireNonNull(
            Objects.requireNonNull(getClass().getResource("/AllProjects.fxml"))));
        BudgetAndAccountingApp.setRoot(root);
      } catch (IOException e) {
        warningLabel.setVisible(true);
        warningLabel.setText("Could not save project, Error: " + e.getMessage());
      }
      
    } catch (IllegalArgumentException e) {
      warningLabel.setVisible(true);
      warningLabel.setText(e.getMessage());
    }
  }
  
  /**
   * Deletes the current project. A popup will appear prompting the user if the really want to
   * delete the project. If no, the deletion is aborted. If yes, the project is deleted from the
   * registry and the user is returned to the all projects page.
   */
  public void deleteProject() {
    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
    alert.setTitle("Delete project");
    alert.setHeaderText("Are you sure you want to delete this project?");
    alert.setContentText("This action cannot be undone.");
    
    Optional<ButtonType> result = alert.showAndWait();
    
    if (result.isPresent() && result.get() == ButtonType.OK) {
      tempUser.getProjectRegistry().removeProject(originalProject);
      try {
        
        FileHandling.writeUserToFile(tempUser);
        Parent root = FXMLLoader.load(
            Objects.requireNonNull(getClass().getResource("/AllProjects.fxml")));
        BudgetAndAccountingApp.setRoot(root);
        
      } catch (IOException e) {
        warningLabel.setVisible(true);
        warningLabel.setText("Could not delete project, Error: " + e.getMessage());
      }
    }
  }

  public void switchTheme() {
    BudgetAndAccountingApp.setTheme();
  }
}
