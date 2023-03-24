package no.ntnu.idatt1002.app;

import java.io.IOException;
import java.util.Objects;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import no.ntnu.idatt1002.app.data.User;

public class BudgetAndAccountingApp extends Application {
    
    private final User user = new User();

    private static Scene scene;
    
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        Parent root =  FXMLLoader.load(Objects.requireNonNull(getClass().getResource(
            "/AllProjects.fxml")));
        scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
    public static void setRoot(Parent root) {
        scene.setRoot(root);
    }
}
