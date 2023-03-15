package no.ntnu.idatt1002.app.gui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.util.Builder;

public class ScreenBuilder implements Builder<Region> {

    private GUIController model;
    private Runnable saveAction;


    public ScreenBuilder(GUIController model, Runnable saveAction) {
        this.model = model;
        this.saveAction = saveAction;
    }

    public Region build() {
        BorderPane results = new BorderPane();
        Node headingBox = createHeadingBox();
        BorderPane.setMargin(headingBox, new Insets(4));
        results.setTop(headingBox);
        results.setCenter(createDataEntrySection());
        results.setBottom(createButtonBox());
        return results;
    }

    private Node createHeadingBox() {
        HBox results = new HBox();
        Text headingText = new Text("This is the Screen Title");
        headingText.getStyleClass().add("heading-text");
        results.getStyleClass().add("standard-border");
        results.getChildren().add(headingText);
        results.setPadding(new Insets(6));
        return results;
    }

    private Node createDataEntrySection() {
        HBox results = new HBox(10);
        results.setPadding(new Insets(2, 10, 4, 2));
        results.getChildren().addAll(createGridPane(), createBioBox());
        return results;
    }

    private Node createBioBox() {
        TextArea bioTextArea = new TextArea();
        bioTextArea.setWrapText(true);
        bioTextArea.setMaxWidth(200);
        bioTextArea.setMaxHeight(200);
        bioTextArea.textProperty().bindBidirectional(model.bioProperty());
        return new VBox(4, Widget.createPromptText("Short Bio:"), bioTextArea);
    }

    private Node createGridPane() {
        GridPane results = Widget.createTwoColumnGridPane();
        results.add(Widget.createPromptText("First Name:"), 0, 0);
        results.add(Widget.createInputField(model.firstNameProperty()), 1, 0);
        results.add(Widget.createPromptText("Last Name:"), 0, 1);
        results.add(Widget.createInputField(model.lastNameProperty()), 1, 1);
        results.add(Widget.createPromptText("Email:"), 0, 2);
        results.add(Widget.createInputField(model.emailProperty()), 1, 2);
        results.add(Widget.createPromptText("Phone Number:"), 0, 3);
        results.add(Widget.createInputField(model.phoneProperty()), 1, 3);
        return results;
    }

    private Node createButtonBox() {
        HBox results = new HBox();
        results.setAlignment(Pos.CENTER_RIGHT);
        results.setPadding(new Insets(8));
        Button button = new Button("Save");
        button.setOnAction(evt -> saveAction.run());
        results.getChildren().add(button);
        return results;
    }
 }