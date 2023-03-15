package no.ntnu.idatt1002.app.gui;

import javafx.beans.property.StringProperty;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

public class Widget {
    
    static GridPane createTwoColumnGridPane() {
        GridPane results = new GridPane();
        results.getColumnConstraints().addAll(createJustifiedConstraint(HPos.RIGHT), createJustifiedConstraint(HPos.LEFT));
        results.setHgap(6);
        results.setVgap(4);
        results.setPadding(new Insets(4));
        return results;
    }

    private static ColumnConstraints createJustifiedConstraint(HPos alignment) {
        ColumnConstraints results = new ColumnConstraints();
        results.setHalignment(alignment);
        return results;
    }

    static Node createInputField(StringProperty boundProperty) {
        TextField results = new TextField();
        results.setMinWidth(100);
        results.textProperty().bindBidirectional(boundProperty);
        return results;
    }

    static Node createPromptText(String prompt) {
        Text results = new Text(prompt);
        results.getStyleClass().add("label-text");
        return results;
    }
}
