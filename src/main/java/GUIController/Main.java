package GUIController;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import models.Partie;

import java.io.IOException;

public class Main extends Application {
    public static Partie game = new Partie();
    private static Stage stage;
    @Override
    public void start(Stage primaryStage) throws IOException {
        stage=primaryStage;
        stage.setResizable(false);
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("login.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1200, 700);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setScene(scene);
        stage.show();
    }



    public static void setScene(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource(fxml));
        Scene scene = new Scene(fxmlLoader.load(), 1200, 700);
        scene.getStylesheets().add(Main.class.getResource("style.css").toExternalForm());
        stage.setScene(scene);
    }

    public static void initGame(String pseudoP1,String filiereP1, String pseudoP2, String filiereP2) {
        game.initZones(new String[] {"Bibliothèque", "Bureau des Etudiants",
                    "Quartier administratif", "Halle industrielle", "Halle sportive"});
        game.initPlayers(pseudoP1, filiereP1, pseudoP2, filiereP2);
        game.initCombs();
    }

    public static void main(String[] args) {
        launch();
    }
}