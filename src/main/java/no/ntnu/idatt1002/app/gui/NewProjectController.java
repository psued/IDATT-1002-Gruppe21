package no.ntnu.idatt1002.app.gui;

import java.time.LocalDate;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import no.ntnu.idatt1002.app.data.Expense;
import no.ntnu.idatt1002.app.data.Equity;
import no.ntnu.idatt1002.app.data.Project;
import no.ntnu.idatt1002.app.data.ProjectRegistry;
import no.ntnu.idatt1002.app.data.User;

public class NewProjectController {
  
  private User user = new User();
  
  private ProjectRegistry projectRegistry = user.getProjectRegistry();
  
  @FXML private TextField name;
  @FXML private MenuButton category;
  @FXML private TextArea description;
  @FXML private DatePicker date;
  @FXML private Button accounting;
  @FXML private Button budgeting;
  
  @FXML private TableView<Equity> incomeTable;
  @FXML private TableColumn<Equity, LocalDate> incomeDate;
  @FXML private TableColumn<Equity, String> incomeDescription;
  @FXML private TableColumn<Equity, String> incomeCategory;
  @FXML private TableColumn<Equity, Double> incomeAmount;
  
  @FXML private TableView<Expense> expenseTable;
  @FXML private TableColumn<Expense, LocalDate> expenseDate;
  @FXML private TableColumn<Expense, String> expenseDescription;
  @FXML private TableColumn<Expense, String> expenseCategory;
  @FXML private TableColumn<Expense, Double> expenseAmount;
  
  
  @FXML private Button save;
  @FXML private Button delete;
  @FXML private Label nameError = new Label();
  
  /**
   * Initializes the controller class.
   */
  public void initialize() {
    category.getItems().clear();
  
    for (String category : projectRegistry.getCategories()) {
      this.category.getItems().add(new MenuItem(category));
    }
  }
  
  /**
   * Sets the chosen category when the menu item is clicked.
   */
  public void setChosenCategory() {
    for (MenuItem menuItem : category.getItems()) {
      menuItem.setOnAction(event -> {
        description.setText(menuItem.getText());
      });
    }
  }
  
  public void saveProject() {
    try {
      Project project = new Project(name.getText(), description.getText(), category.getText());
      user.addProject(project);
      nameError.setText(project.toString());
    } catch (IllegalArgumentException e) {
      nameError.setText(e.getMessage());
    }
  }
}
