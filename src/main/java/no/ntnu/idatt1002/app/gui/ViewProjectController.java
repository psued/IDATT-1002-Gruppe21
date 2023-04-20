package no.ntnu.idatt1002.app.gui;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import no.ntnu.idatt1002.app.BudgetAndAccountingApp;
import no.ntnu.idatt1002.app.User;
import no.ntnu.idatt1002.app.filehandling.FileHandling;
import no.ntnu.idatt1002.app.registers.Project;
import no.ntnu.idatt1002.app.transactions.Expense;
import no.ntnu.idatt1002.app.transactions.Income;

/**
 * Controller for the view project view. Just displays the project data and allows the user to
 * either edit the project or go back to the all projects view.
 */
public class ViewProjectController {

  private Project project;

  private ArrayList<Income> accountingIncome = new ArrayList<>();
  private ArrayList<Expense> accountingExpense = new ArrayList<>();

  // Local Budgeting overview
  private ArrayList<Income> budgetingIncome = new ArrayList<>();
  private ArrayList<Expense> budgetingExpense = new ArrayList<>();
  @FXML private Label viewTitle;
  @FXML private Label name;
  @FXML private Label category;
  @FXML private Label dueDate;
  @FXML private Label status;
  @FXML private Label description;

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

  @FXML private ImageView iconLeft;
  @FXML private ImageView iconRight;
  @FXML private VBox previousProjectBox;
  @FXML private VBox nextProjectBox;
  @FXML private Button imageLeft;
  @FXML private Button imageRight;
  @FXML private ImageView imagePreview;
  
  private int imageIndex;
  private List<File> images;
  
  @FXML private Label totalIncome;
  @FXML private Label totalExpense;
  @FXML private Label totalAmount;
  
  @FXML private Label warningLabel;

  /**
   * Initialize the view project controller. Sets all text objects to match with the project data
   * and sets up the tables.
   */
  public void initializeWithData(Project selectedProject) {
    if (selectedProject == null) {
      throw new NullPointerException("Please select a project to view");
    }

    User tempUser = null;
    try {
      tempUser = FileHandling.readUserFromFile();
    } catch (IOException e) {
      e.printStackTrace();
    } catch (ClassNotFoundException e) {
      throw new RuntimeException(e);
    }
    
    project = selectedProject;
    images = project.getImages();
    imageIndex = 0;
    
    viewTitle.setText("View " + project.getName());
    name.setText(project.getName());
    category.setText(project.getCategory());
    status.setText(project.getStatus());

    String dueDateString =
            project.getDueDate() == null ? "No due date" : project.getDueDate().toString();
    dueDate.setText(dueDateString);

    description.setText(project.getDescription());

    accountingIncome = project.getAccounting().getIncomeList();
    accountingExpense = project.getAccounting().getExpenseList();
    budgetingIncome = project.getBudgeting().getIncomeList();
    budgetingExpense = project.getBudgeting().getExpenseList();

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

    if (selectedProject.equals(tempUser.getProjectRegistry().getProjects().get(0))) {
      iconLeft.setOpacity(0);
      previousProjectBox.setDisable(true);
    } else {
      iconLeft.setOpacity(1);
      previousProjectBox.setDisable(false);
    }

    Project lastProject = tempUser.getProjectRegistry().getProjects()
        .get(tempUser.getProjectRegistry().getProjects().size() - 1);
    
    if (selectedProject.equals(lastProject)) {
      iconRight.setOpacity(0);
      nextProjectBox.setDisable(true);
    } else {
      iconRight.setOpacity(1);
      nextProjectBox.setDisable(false);
     }
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
    
    refreshImages();
    
    warningLabel.setVisible(false);
  }

  /**
   * Opens the edit project view of the current project.
   */
  public void editProject() {
    try {
      FXMLLoader loader = new FXMLLoader(getClass().getResource("/EditProject.fxml"));
      Parent root = loader.load();

      EditProjectController controller = loader.getController();
      controller.initializeWithData(project);

      BudgetAndAccountingApp.setRoot(root);
    } catch (IOException | NullPointerException e) {
      e.printStackTrace();
    }
  }

  /**
   * Opens the all projects view.
   */
  public void goToAllProjects() {
    try {
      Parent root = FXMLLoader.load(
              Objects.requireNonNull(getClass().getResource("/AllProjects.fxml")));
      BudgetAndAccountingApp.setRoot(root);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public void nextProject() {
    User tempUser = null;
    try {
      tempUser = FileHandling.readUserFromFile();
    } catch (IOException e) {
      e.printStackTrace();
    } catch (ClassNotFoundException e) {
      throw new RuntimeException(e);
    }

    int index = tempUser.getProjectRegistry().getProjects().indexOf(project);
    Project nextProject = tempUser.getProjectRegistry().getProjects().get(index + 1);

    initializeWithData(nextProject);
  }
  
  /**
   * Refreshes the image preview and the buttons to navigate between images. Will disable the
   */
  private void refreshImages() {
    if (images == null || images.isEmpty()) {
      imageLeft.setDisable(true);
      imageRight.setDisable(true);
      imagePreview.setImage(null);
      return;
    }
    
    Image image = new Image(images.get(imageIndex).toURI().toString());
    imagePreview.setImage(image);
    
    imageLeft.setDisable(imageIndex == 0);
    imageRight.setDisable(imageIndex == images.size() - 1);
  }
  
  /**
   * Lets a user look backwards through added images.
   */
  public void imageIndexLeft() {
    if (imageIndex > 0) {
      imageIndex--;
      refreshImages();
    }
  }
  
  /**
   * Lets a user look forwards through added images.
   */
  public void imageIndexRight() {
    if (imageIndex < images.size() - 1) {
      imageIndex++;
      refreshImages();
    }
  }

  public void previousProject() {
    User tempUser = null;
    try {
      tempUser = FileHandling.readUserFromFile();
    } catch (IOException e) {
      e.printStackTrace();
    } catch (ClassNotFoundException e) {
      throw new RuntimeException(e);
    }

    int index = tempUser.getProjectRegistry().getProjects().indexOf(project);
    Project previousProject = tempUser.getProjectRegistry().getProjects().get(index - 1);

    initializeWithData(previousProject);
  }

  public void switchTheme() {
    BudgetAndAccountingApp.setTheme();
  }
}
