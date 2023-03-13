package no.ntnu.idatt1002.app;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Region;
import javafx.stage.Stage;
import no.ntnu.idatt1002.app.gui.ModelViewController;
import no.ntnu.idatt1002.app.gui.ScreenBuilder;

public class BudgetAndAccountingApp extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        Region sceneRoot = new ScreenBuilder(new ModelViewController(), () -> {
        }).build();
        Scene scene = new Scene(sceneRoot);
        primaryStage.setScene(scene);
        scene.getStylesheets().add("/css/default.css");
        primaryStage.show();
    }

}
