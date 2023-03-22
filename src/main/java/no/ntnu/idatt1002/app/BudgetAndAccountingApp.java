package no.ntnu.idatt1002.app;

import java.io.IOException;
import java.util.Objects;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Region;
import javafx.stage.Stage;
import no.ntnu.idatt1002.app.data.User;
import no.ntnu.idatt1002.app.gui.GUIController;
import no.ntnu.idatt1002.app.gui.ScreenBuilder;

public class BudgetAndAccountingApp extends Application {

    private final User user = new User();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        Parent root =  FXMLLoader.load(Objects.requireNonNull(getClass().getResource(
            "/NewProject.fxml")));
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        scene.getStylesheets().add("/css/default.css");
        primaryStage.show();
    }

}
