package no.ntnu.idatt1002.app.gui;

import java.io.File;
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
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import no.ntnu.idatt1002.app.BudgetAndAccountingApp;
import no.ntnu.idatt1002.app.data.Expense;
import no.ntnu.idatt1002.app.data.Income;
import no.ntnu.idatt1002.app.data.Project;
import no.ntnu.idatt1002.app.data.Transaction;
import no.ntnu.idatt1002.app.data.User;
import no.ntnu.idatt1002.app.filehandling.FileHandling;

/**
 * FXML Controller class for the New Project page. Only mandatory field is the name of the project.
 */
public class NewProjectController {
  
  private User tempUser;
  
  // Local Accounting overview
  private final ArrayList<Income> accountingIncome = new ArrayList<>();
  private final ArrayList<Expense> accountingExpense = new ArrayList<>();
  
  // Local Budgeting overview
  private final ArrayList<Income> budgetingIncome = new ArrayList<>();
  private final ArrayList<Expense> budgetingExpense = new ArrayList<>();
  
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
  @FXML private Text totalIncome;
  @FXML private Text totalExpense;
  @FXML private Text totalAmount;
  
  //Error message
  @FXML private Label warningLabel = new Label();
  
  /**
   * Initializes the controller class.
   */
  public void initialize() {
    try {
      tempUser = FileHandling.readUserFromFile();
    } catch (IOException e) {
      e.printStackTrace();
    } catch (ClassNotFoundException e) {
      throw new RuntimeException(e);
    }
    
    category.getItems().clear();
  
    // Add categories to category menu
    for (String category : tempUser.getProjectRegistry().getCategories()) {
      MenuItem menuItem = new MenuItem(category);
      menuItem.setOnAction(event -> this.category.setText(menuItem.getText()));
      this.category.getItems().add(menuItem);
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
    
    // Set default category
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
      List<Income> incomeList = isAccounting ? accountingIncome : budgetingIncome;
      if (selectedTransaction != null) {
        incomeList.remove(((Income) selectedTransaction));
      }
      incomeList.add(new Income(incomeDescriptionField.getText(), incomeCategoryField.getText(),
          Double.parseDouble(incomeAmountField.getText()), incomeDatePicker.getValue()));
      
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
      List<Expense> expenseList = isAccounting ? accountingExpense : budgetingExpense;
      if (selectedTransaction != null) {
        expenseList.remove((Expense) selectedTransaction);
      }
      expenseList.add(new Expense(expenseDescriptionField.getText(), expenseCategoryField.getText(),
          Double.parseDouble(expenseAmountField.getText()), expenseDatePicker.getValue()));
      
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
   * Creates a new project and adds it to the user's project registry. It will display an error
   * message if the project name is invalid.
   */
  public void saveProject() {
    try {
      Project project = new Project(name.getText(), description.getText(), category.getText(),
          dueDate.getValue());
      
      accountingIncome.forEach(project.getAccounting()::addIncome);
      accountingExpense.forEach(project.getAccounting()::addExpense);
      budgetingIncome.forEach(project.getBudgeting()::addIncome);
      budgetingExpense.forEach(project.getBudgeting()::addExpense);
      
      images.forEach(project::addImage);
      
      tempUser.addProject(project);
      
     
      try {
        FileHandling.writeUserToFile(tempUser);
        
        Parent root = FXMLLoader.load(
            Objects.requireNonNull(getClass().getResource("/AllProjects.fxml")));
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
   * Goes to the all projects page without saving the current project. A confirmation popup when
   * the delete button is pressed.
   */
  public void deleteProject() {
    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
    alert.setTitle("Delete project");
    alert.setHeaderText("Are you sure you want to delete this project?");
    alert.setContentText("This action cannot be undone.");
    
    Optional<ButtonType> result = alert.showAndWait();
    
    if (result.isPresent() && result.get() == ButtonType.OK) {
      try {
        System.out.println("Deleting project");
        Parent root = FXMLLoader.load(
            Objects.requireNonNull(getClass().getResource("/AllProjects.fxml")));
        BudgetAndAccountingApp.setRoot(root);
      } catch (IOException e) {
        warningLabel.setVisible(true);
        warningLabel.setText("Could not delete project, Error: " + e.getMessage());
      }
    }
  }
}