package no.ntnu.idatt1002.app.gui;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.MenuButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import no.ntnu.idatt1002.app.data.Project;

public class NewProjectController {
  
  @FXML
  private TextField name;
  @FXML
  private MenuButton category;
  @FXML
  private TextArea description;
  @FXML
  private DatePicker date;
  @FXML
  private Button accounting;
  @FXML
  private Button budgeting;
  @FXML
  private Button save;
  @FXML
  private Button delete;
  
  public Project saveProject() {
    return new Project(name.getText(), description.getText(), category.getText());
  }
}
