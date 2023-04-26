package no.ntnu.idatt1002.app.gui;

import java.io.File;
import java.time.LocalDate;
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
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import no.ntnu.idatt1002.app.BudgetAndAccountingApp;
import no.ntnu.idatt1002.app.User;
import no.ntnu.idatt1002.app.bookkeeping.Bookkeeping;
import no.ntnu.idatt1002.app.registers.Project;
import no.ntnu.idatt1002.app.transactions.Expense;
import no.ntnu.idatt1002.app.transactions.Income;

/**
 * Controller for the ViewProject.fxml file. Takes a project as input and displays the data. Has
 * options for the user to edit the project or go back to all projects.
 */
public class ViewProjectController {
  
  // The project that is being viewed
  private Project chosenProject;
  
  // The project information
  @FXML
  private Label viewTitle;
  @FXML
  private Label name;
  @FXML
  private Label category;
  @FXML
  private Label dueDate;
  @FXML
  private Label status;
  @FXML
  private Label description;
  
  //Accounting and Budgeting toggle button
  @FXML
  private ToggleButton toggleButton;
  @FXML
  private Label toggleLabel;
  
  //Income Table
  @FXML
  private TableView<Income> incomeTable;
  @FXML
  private TableColumn<Income, LocalDate> incomeDate;
  @FXML
  private TableColumn<Income, String> incomeDescription;
  @FXML
  private TableColumn<Income, String> incomeCategory;
  @FXML
  private TableColumn<Income, Double> incomeAmount;
  
  //Expense Table
  @FXML
  private TableView<Expense> expenseTable;
  @FXML
  private TableColumn<Expense, LocalDate> expenseDate;
  @FXML
  private TableColumn<Expense, String> expenseDescription;
  @FXML
  private TableColumn<Expense, String> expenseCategory;
  @FXML
  private TableColumn<Expense, Double> expenseAmount;
  
  //Navigation icons and boxes
  @FXML
  private ImageView iconLeft;
  @FXML
  private ImageView iconRight;
  @FXML
  private VBox previousProjectBox;
  @FXML
  private VBox nextProjectBox;
  
  //Image preview
  @FXML
  private Button imageLeft;
  @FXML
  private Button imageRight;
  @FXML
  private ImageView imagePreview;
  
  //Total income, expense and amount overview
  @FXML
  private Label totalIncome;
  @FXML
  private Label totalExpense;
  @FXML
  private Label totalAmount;
  
  //Pie charts
  @FXML
  private PieChart pieIncome;
  @FXML
  private PieChart pieExpense;
  
  //Warning message
  @SuppressWarnings("CanBeFinal")
  @FXML
  private Label warningLabel = new Label();
  
  /**
   * Initialize the view project controller. Sets all information to the project that is being
   * viewed.
   *
   * @param selectedProject The project that is being viewed
   * @throws IllegalArgumentException If the project is null
   */
  public void initializeWithData(Project selectedProject) throws IllegalArgumentException {
    // Check if the project is null
    if (selectedProject == null) {
      throw new IllegalArgumentException("Please select a project to view");
    }
    chosenProject = selectedProject;
    
    // Set up the view
    viewTitle.setText("View " + chosenProject.getName());
    name.setText(chosenProject.getName());
    category.setText(chosenProject.getCategory());
    status.setText(chosenProject.getStatus());
    dueDate.setText(
        chosenProject.getDueDate() == null ? "No due date" : chosenProject.getDueDate().toString());
    description.setText(chosenProject.getDescription());
    
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
    
    // Set up the image preview
    List<File> images = getProject().getImages();
    imagePreview.setImage(images.isEmpty() ? null : new Image(images.get(0).toURI().toString()));
    
    // Set up navigation icons
    int indexOfProject =
        User.getInstance().getProjectRegistry().getProjects().indexOf(getProject());
    iconLeft.setOpacity(indexOfProject == 0 ? 0 : 1);
    iconRight.setOpacity(
        indexOfProject == User.getInstance().getProjectRegistry().getProjects().size() - 1 ? 0 : 1);
    
    previousProjectBox.setDisable(indexOfProject == 0);
    nextProjectBox.setDisable(
        indexOfProject == User.getInstance().getProjectRegistry().getProjects().size() - 1);
    
    // Set up the tables to display the transactions of the project that is being edited
    refreshOverview();
    refreshImages();
    
  }
  
  
  /**
   * Refreshes the local overview tables and totals. Updates the tables with the correct data
   * depending on the isAccounting boolean.
   */
  @FXML
  public void refreshOverview() {
    // Clear the tables
    incomeTable.getItems().clear();
    expenseTable.getItems().clear();
    
    // Get the correct bookkeeping
    boolean isAccounting = toggleButton.isSelected();
    toggleLabel.setText(isAccounting ? "Accounting - " : "Budgeting - ");
    
    // Update the tables
    Bookkeeping currentBookkeeping =
        isAccounting ? getProject().getAccounting() : getProject().getBudgeting();
    
    incomeTable.getItems().addAll(currentBookkeeping.getIncomeList());
    expenseTable.getItems().addAll(currentBookkeeping.getExpenseList());
    
    incomeTable.refresh();
    expenseTable.refresh();
    
    // Set total amounts
    totalIncome.setText(String.format("%.2f kr", currentBookkeeping.getTotalIncome()));
    totalExpense.setText(String.format("- %.2f kr", currentBookkeeping.getTotalExpense()));
    totalAmount.setText(String.format("%.2f kr",
        currentBookkeeping.getTotalIncome() - currentBookkeeping.getTotalExpense()));
    
    clearWarning();
    updatePieCharts();
  }
  
  /**
   * Update the pie charts with the correct data.
   *
   * <p>The pie charts will display the total income and expense amounts for each category.
   */
  private void updatePieCharts() {
    // Update pieChart income
    ObservableList<PieChart.Data> pieChartDataIncome = FXCollections.observableArrayList();
    HashMap<String, Double> categoriesIncome = new HashMap<>();
    
    for (int i = 0; i < incomeTable.getItems().size(); i++) {
      String categoryIncome = incomeTable.getItems().get(i).getCategory();
      Double amountIncome = incomeTable.getItems().get(i).getAmount();
      
      if (categoriesIncome.containsKey(categoryIncome)) {
        Double currentAmount = categoriesIncome.get(categoryIncome);
        categoriesIncome.put(categoryIncome, currentAmount + amountIncome);
      } else {
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
      
      if (categoriesExpense.containsKey(categoryExpense)) {
        Double currentAmount = categoriesExpense.get(categoryExpense);
        categoriesExpense.put(categoryExpense, currentAmount + amountExpense);
      } else {
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
  
  /**
   * Opens the edit project view of the current project.
   *
   * <p>In the unlikely event that an exception is thrown, a warning will be displayed with
   * the given warning message.
   */
  @FXML
  public void editProject() {
    try {
      FXMLLoader loader = new FXMLLoader(getClass().getResource("/EditProject.fxml"));
      Parent root = loader.load();
      
      EditProjectController controller = loader.getController();
      controller.initializeWithData(chosenProject);
      
      BudgetAndAccountingApp.setRoot(root);
    } catch (Exception e) {
      setWarning("Could not edit project, error: " + e.getMessage());
    }
  }
  
  /**
   * Opens the all projects page.
   *
   * <p>In the unlikely event that an exception is thrown, a warning will be displayed prompting
   * the user to restart the application as no other solution is available.
   */
  @FXML
  public void allProjects() {
    try {
      Parent root =
          FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/AllProjects.fxml")));
      BudgetAndAccountingApp.setRoot(root);
    } catch (Exception e) {
      setWarning("Could not load projects, please restart the application");
    }
  }
  
  /**
   * Reloads the page to display the next project in the project registry.
   */
  @FXML
  public void nextProject() {
    int index = User.getInstance().getProjectRegistry().getProjects().indexOf(chosenProject);
    Project nextProject = User.getInstance().getProjectRegistry().getProjects().get(index + 1);
    
    try {
      initializeWithData(nextProject);
    } catch (Exception e) {
      setWarning("Could not load next project, error: " + e.getMessage());
    }
  }
  
  /**
   * Reloads the page to display the previous project in the project registry.
   */
  @FXML
  public void previousProject() {
    int index = User.getInstance().getProjectRegistry().getProjects().indexOf(chosenProject);
    Project previousProject = User.getInstance().getProjectRegistry().getProjects().get(index - 1);
    
    try {
      initializeWithData(previousProject);
    } catch (Exception e) {
      setWarning("Could not load previous project, error: " + e.getMessage());
    }
  }
  
  /**
   * Lets a user look backwards through added images.
   */
  @FXML
  public void imageIndexLeft() {
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
  @FXML
  public void imageIndexRight() {
    int imageIndex = getProject().getImageIndex(imagePreview.getImage());
    List<File> images = getProject().getImages();
    
    if (imageIndex == images.size() - 1) {
      imagePreview.setImage(new Image(images.get(0).toURI().toString()));
    } else {
      imagePreview.setImage(new Image(images.get(imageIndex + 1).toURI().toString()));
    }
    
    refreshImages();
  }
  
  /**
   * Refreshes the image preview and the buttons to navigate between images. Will disable the
   * buttons if there are no images to navigate between.
   */
  private void refreshImages() {
    List<File> images = getProject().getImages();
    
    imageLeft.setDisable(images.size() < 2);
    imageRight.setDisable(images.size() < 2);
  }
  
  /**
   * Get the last project in the singleton user's project registry, which is the current project.
   *
   * @return The last project in the singleton user's project registry.
   */
  private Project getProject() {
    return new Project(chosenProject);
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
  @FXML
  public void switchTheme() {
    BudgetAndAccountingApp.switchTheme();
  }
}