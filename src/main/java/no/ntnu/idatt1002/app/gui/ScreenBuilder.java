package no.ntnu.idatt1002.app.gui;

import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.text.Text;
import javafx.util.Builder;

public class ScreenBuilder implements Builder<Region> {

    @Override
    public Region build() {
        BorderPane results = new BorderPane();
        Node headingBox = createHeadingBox();
        BorderPane.setMargin(headingBox, new Insets(40));
        results.setTop(headingBox);
        return results;
    }

    private Node createHeadingBox() {
        HBox results = new HBox();
        Text headingText = new Text("This is the Screen Title");
        headingText.getStyleClass().add("heading-text");
        results.getStyleClass().add("standard-border");
        results.getChildren().add(headingText);
        results.setPadding(new Insets(60));
        return results;
    }

}
