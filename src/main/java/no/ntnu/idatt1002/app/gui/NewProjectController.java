package no.ntnu.idatt1002.app.gui;

import java.io.File;
import java.time.LocalDate;
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
import javafx.scene.control.ToggleButton;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import no.ntnu.idatt1002.app.BudgetAndAccountingApp;
import no.ntnu.idatt1002.app.User;
import no.ntnu.idatt1002.app.bookkeeping.Bookkeeping;
import no.ntnu.idatt1002.app.filehandling.FileHandling;
import no.ntnu.idatt1002.app.registers.Project;
import no.ntnu.idatt1002.app.transactions.Expense;
import no.ntnu.idatt1002.app.transactions.Income;

/**
 * FXML Controller class for the New Project page. Only mandatory field is the name of the project.
 */
public class NewProjectController {
  // Fundamental project information
  @FXML private TextField name;
  @FXML private MenuButton category;
  @FXML private TextArea description;
  @FXML private DatePicker dueDate;
  @FXML private MenuButton status;
  
  //Accounting and Budgeting toggle button
  @FXML private ToggleButton toggleButton;
  @FXML private Label toggleLabel;
  
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
  
  //Total income, expense and amount overview
  @FXML private Label totalIncome;
  @FXML private Label totalExpense;
  @FXML private Label totalAmount;

  //Pie charts
  @FXML private PieChart pieIncome;
  @FXML private PieChart pieExpense;

  //Error message
  @FXML private Label warningLabel = new Label();
  
  /**
   * Initializes the controller class by reading the user from file and adding already existing
   * categories and statuses to the menu buttons.
   */
  public void initialize() {
    User.getInstance().getProjectRegistry().addProject(new Project("New Project", null,
        User.getInstance().getProjectRegistry().getCategories().get(0), null, "Not started"));
    
    // Add categories to category menu
    for (String category : User.getInstance().getProjectRegistry().getCategories()) {
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
    
    //Add statuses to status menu
    for (String status : User.getInstance().getProjectRegistry().getStatuses()) {
      MenuItem menuItem = new MenuItem(status);
      menuItem.setOnAction(event -> this.status.setText(menuItem.getText()));
      this.status.getItems().add(menuItem);
    }
    
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
    
    //Set default values
    name.setPromptText("New project");
    category.setText(User.getInstance().getProjectRegistry().getCategories().get(0));
    status.setText("Not started");
    
    refreshLocalOverview();
    refreshImages();
    
    resetIncomeFields();
    resetExpenseFields();
    
    clearWarning();
  }
  
  /**
   * Deletes a category from the user's project registry if it is not used by any projects.
   */
  public void deleteCategory() {
    //Get the category that is selected
    MenuItem chosenCategory = category.getItems().stream()
        .filter(item -> item.getText().equals(category.getText())).findFirst().orElse(null);
    
    try {
      User.getInstance().getProjectRegistry().removeCategory(category.getText());
      category.getItems().remove(chosenCategory);
      category.setText("");
      
      clearWarning();
    } catch (IllegalArgumentException e) {
      setWarning(e.getMessage());
    }
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
    Income selectedIncome = incomeTable.getSelectionModel().getSelectedItem();
    
    if (selectedIncome != null) {
      incomeDatePicker.setValue(selectedIncome.getDate() == null
          ? null : selectedIncome.getDate());
      incomeDescriptionField.setText(selectedIncome.getDescription());
      incomeCategoryField.setText(selectedIncome.getCategory());
      incomeAmountField.setText(String.valueOf(selectedIncome.getAmount()));
      
      deleteIncomeButton.setDisable(false);
    } else {
      incomeTable.getSelectionModel().clearSelection();
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
    Expense selectedExpense = expenseTable.getSelectionModel().getSelectedItem();
    if (selectedExpense != null) {
      expenseDatePicker.setValue(selectedExpense.getDate() == null
          ? null : selectedExpense.getDate());
      expenseDescriptionField.setText(selectedExpense.getDescription());
      expenseCategoryField.setText(selectedExpense.getCategory());
      expenseAmountField.setText(String.valueOf(selectedExpense.getAmount()));
      
      deleteExpenseButton.setDisable(false);
    } else {
      expenseTable.getSelectionModel().clearSelection();
      resetExpenseFields();
    }
  }
  
  /**
   * When pressing the add button, the addIncomeToLocal method will be called and depending on
   * whether a row is selected or not, the income will be added or updated.
   */
  public void addIncomeToLocal() {
    try {
      boolean isAccounting = toggleButton.isSelected();
      
      Income selectedIncome = incomeTable.getSelectionModel().getSelectedItem();
      Income newIncome = new Income(incomeDescriptionField.getText(),
          incomeCategoryField.getText(), Double.parseDouble(incomeAmountField.getText()),
          incomeDatePicker.getValue());
      
      Project newProject = getProject();
      
      if (selectedIncome != null) {
        (isAccounting ? newProject.getAccounting() : newProject.getBudgeting())
            .updateTransaction(selectedIncome, newIncome);
      } else {
        (isAccounting ? newProject.getAccounting() : newProject.getBudgeting())
            .addTransaction(newIncome);
      }
      
      updateProject(newProject);
      
      refreshLocalOverview();
      resetIncomeFields();
    } catch (NumberFormatException e) {
      setWarning("Please enter a valid amount that is greater than 0");
    } catch (IllegalArgumentException e) {
      setWarning(e.getMessage());
    }
  }
  
  /**
   * When pressing the add button, the addExpenseToLocal method will be called and depending on
   * whether a row is selected or not, the expense will be added or updated.
   */
  public void addExpenseToLocal() {
    try {
      boolean isAccounting = toggleButton.isSelected();
      
      Expense selectedExpense = expenseTable.getSelectionModel().getSelectedItem();
      Expense newExpense = new Expense(expenseDescriptionField.getText(),
          expenseCategoryField.getText(), Double.parseDouble(expenseAmountField.getText()),
          expenseDatePicker.getValue());
      
      Project newProject = getProject();
      
      if (selectedExpense != null) {
        (isAccounting ? newProject.getAccounting() : newProject.getBudgeting())
            .updateTransaction(selectedExpense, newExpense);
      } else {
        (isAccounting ? newProject.getAccounting() : newProject.getBudgeting())
            .addTransaction(newExpense);
      }
      
      updateProject(newProject);
      
      refreshLocalOverview();
      resetExpenseFields();
    } catch (NumberFormatException e) {
      setWarning("Please enter a valid amount that is greater than 0");
    } catch (IllegalArgumentException e) {
      setWarning(e.getMessage());
    }
  }
  
  /**
   * Deletes the selected income from the local overview.
   */
  public void deleteIncome() {
    boolean isAccounting = toggleButton.isSelected();
    Project newProject = getProject();
    
    (isAccounting ? newProject.getAccounting() : newProject.getBudgeting()).removeTransaction(
        incomeTable.getSelectionModel().getSelectedItem());
    
    updateProject(newProject);
    
    refreshLocalOverview();
    resetIncomeFields();
  }
  
  /**
   * Deletes the selected expense from the local overview.
   */
  public void deleteExpense() {
    boolean isAccounting = toggleButton.isSelected();
    Project newProject = getProject();
    
    (isAccounting ? newProject.getAccounting() : newProject.getBudgeting()).removeTransaction(
        expenseTable.getSelectionModel().getSelectedItem());
    
    updateProject(newProject);
    
    refreshLocalOverview();
    resetExpenseFields();
  }
  
  /**
   * Refreshes the local overview by updating the tables and totals, resetting the selected row
   * and resets the error message.
   */
  public void refreshLocalOverview() {
    // Update tables
    incomeTable.getItems().clear();
    expenseTable.getItems().clear();
    
    boolean isAccounting = toggleButton.isSelected();
    toggleLabel.setText(isAccounting ? "Accounting - " : "Budgeting - ");
    
    Bookkeeping currentBookkeeping = isAccounting ? getProject().getAccounting() :
        getProject().getBudgeting();
    
    incomeTable.getItems().addAll(currentBookkeeping.getIncomeList());
    expenseTable.getItems().addAll(currentBookkeeping.getExpenseList());
    
    incomeTable.refresh();
    expenseTable.refresh();
  
    totalIncome.setText(String.format("%.2f kr", currentBookkeeping.getTotalIncome()));
    totalExpense.setText(String.format("- %.2f kr", currentBookkeeping.getTotalExpense()));
    totalAmount.setText(String.format("%.2f kr",
        currentBookkeeping.getTotalIncome() - currentBookkeeping.getTotalExpense()));

    deleteIncomeButton.setDisable(incomeTable.getSelectionModel().getSelectedItem() == null);
    deleteExpenseButton.setDisable(expenseTable.getSelectionModel().getSelectedItem() == null);
    
    clearWarning();
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
  public void resetIncomeFields() {
    incomeDatePicker.setValue(null);
    incomeDescriptionField.setText("");
    incomeCategoryField.setText("");
    incomeAmountField.setText("");
    deleteIncomeButton.setDisable(true);
    incomeTable.getSelectionModel().clearSelection();
  }
  
  // Resets the expense fields
  public void resetExpenseFields() {
    expenseDatePicker.setValue(null);
    expenseDescriptionField.setText("");
    expenseCategoryField.setText("");
    expenseAmountField.setText("");
    deleteExpenseButton.setDisable(true);
    expenseTable.getSelectionModel().clearSelection();
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
      Project newProject = getProject();
      newProject.addImage(selectedFile);
      imagePreview.setImage(new Image(selectedFile.toURI().toString()));
      updateProject(newProject);
      
      refreshImages();
    }
  }
  
  /**
   * Deletes the currently selected image.
   */
  public void deleteImage() {
    Project newProject = getProject();
    
    int imageIndex = newProject.getImageIndex(imagePreview.getImage());
    newProject.removeImage(newProject.getImages().get(imageIndex));
    
    updateProject(newProject);

    if (newProject.getImages().size() == 0) {
      imagePreview.setImage(null);
    } else {
      imagePreview.setImage(new Image(newProject.getImages().get(0).toString()));
    }
    
    refreshImages();
  }
  
  /**
   * Lets a user look backwards through added images.
   */
  public void imageIndexBackwards() {
    int imageIndex = getProject().getImageIndex(imagePreview.getImage());
    List<File> images = getProject().getImages();
    
    if (imageIndex == 0) {
      imagePreview.setImage(new Image(images.get(images.size() - 1).toURI().toString()));
    } else {
      imagePreview.setImage(new Image(images.get(imageIndex - 1).toURI().toString()));
    }
    
    refreshImages();
  }
  
  /**
   * Lets a user look forwards through added images.
   */
  public void imageIndexForwards() {
    int imageIndex = getProject().getImageIndex(imagePreview.getImage());
    List<File> images = getProject().getImages();
    imagePreview.setImage(null);
    
    if (imageIndex == images.size() - 1) {
      imagePreview.setImage(new Image(images.get(0).toURI().toString()));
    } else {
      imagePreview.setImage(new Image(images.get(imageIndex + 1).toURI().toString()));
    }

    refreshImages();
  }
  
  /**
   * Refreshes the image preview and the buttons to navigate between images. Will disable the
   */
  private void refreshImages() {
    List<File> images = getProject().getImages();
    
    imageLeft.setDisable(images.size() < 2);
    imageRight.setDisable(images.size() < 2);
    deleteImageButton.setDisable(images.size() < 1);
  }
  
  /**
   * Creates a new project and adds it to the user's project registry. It will display an error
   * message if the project name is invalid.
   */
  public void saveProject() {
    try {
      Project newProject = getProject();
      
      if (name.getText().isEmpty()) {
        throw new IllegalArgumentException("Project name cannot be empty");
      }
      
      newProject.setName(name.getText());
      newProject.setCategory(category.getText());
      newProject.setDueDate(dueDate.getValue());
      newProject.setDescription(description.getText());
      newProject.setStatus(status.getText());
      
      updateProject(newProject);
      
      FileHandling.writeUserToFile(User.getInstance());
      
      Parent root = FXMLLoader.load(
          Objects.requireNonNull(getClass().getResource("/AllProjects.fxml")));
      BudgetAndAccountingApp.setRoot(root);
    } catch (Exception e) {
      setWarning("Could not save project, Error: " + e.getMessage());
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
        User.getInstance().loadUser(FileHandling.readUserFromFile());
        Parent root = FXMLLoader.load(
            Objects.requireNonNull(getClass().getResource("/AllProjects.fxml")));
        BudgetAndAccountingApp.setRoot(root);
      } catch (Exception e) {
        warningLabel.setVisible(true);
        warningLabel.setText("Could not delete project, Error: " + e.getMessage());
      }
    }
  }
  
  /**
   * Get the last project in the singleton user's project registry, which is the current project.
   * 
   * @return The last project in the singleton user's project registry.
   */
  private Project getProject() {
    return new Project(User.getInstance().getProjectRegistry().getProjects().get(
        User.getInstance().getProjectRegistry().getProjects().size() - 1));
  }
  
  /**
   * Update the last project in the singleton user's project registry, which is the current project.
   * 
   * @param newProject The edited project to update the singleton with.
   */
  private void updateProject(Project newProject) {
    User.getInstance().getProjectRegistry().updateProject(getProject(), newProject);
  }
  
  
  /**
   * Sets the warning label to display the given warning.
   *
   * @param warning The warning to display.
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
   * Switches the theme of the application.
   */
  public void switchTheme() {
    BudgetAndAccountingApp.setTheme();
  }
}