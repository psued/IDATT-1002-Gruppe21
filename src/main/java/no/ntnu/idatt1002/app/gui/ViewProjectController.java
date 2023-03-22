package no.ntnu.idatt1002.app.gui;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import no.ntnu.idatt1002.app.data.Expense;
import no.ntnu.idatt1002.app.data.Income;

import java.time.LocalDate;

public class ViewProjectController {

  @FXML private Text projectName;
  @FXML private Text projectCategory;
  @FXML private Text projectDate;
  @FXML private Text projectDescription;
  @FXML private Text totalPrice;
  @FXML private Button accounting;
  @FXML private Button budgeting;
  @FXML private Button edit;

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
}
