package GUIController;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import models.Combattant;
import models.Zone;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import static GUIController.UtilsGUI.buildDrop;

public class Battle implements Initializable {



    @FXML
    private Label title;
    @FXML
    private Label score1;
    @FXML
    private Label score2;
    @FXML
    private Label player1;
    @FXML
    private Label player2;

    @FXML
    private ScrollPane zoneP1;
    @FXML
    private ScrollPane zoneP2;


    private boolean done = false;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        gameManagement();
        guiSetup();
    }

    public void gameManagement() {
        Main.currentZone = Main.game.chooseZone();
        done = false;
    }

    public void guiSetup() {
        player1.setText(Main.game.getPlayers()[0].getPseudo());
        player2.setText(Main.game.getPlayers()[1].getPseudo());
        title.setText(Main.game.getZones()[Main.currentZone].getName());
        score1.setText(String.valueOf(Main.game.getPlayers()[0].getScore()));
        score2.setText(String.valueOf(Main.game.getPlayers()[1].getScore()));
        nonDroppableZone(zoneP1, Main.game.getZones()[Main.currentZone].getCombattantP1());
        nonDroppableZone(zoneP2, Main.game.getZones()[Main.currentZone].getCombattantP2());
    }

    public void customColor(ScrollPane pane1, ScrollPane pane2) {
        FlowPane flowPane1 = (FlowPane) pane1.getContent();
        if(Main.game.getZones()[Main.currentZone].getCombattantP1().size() > 0){
            flowPane1.setStyle("-fx-background-color: #7F9183; -fx-max-width: 400; -fx-orientation: HORIZONTAL; -fx-padding: 10; -fx-hgap: 10; ");
        } else{
            flowPane1.setStyle("-fx-background-color: #ce4257; -fx-max-width: 400; -fx-orientation: HORIZONTAL; -fx-padding: 10; -fx-hgap: 10;");
        }
        FlowPane flowPane2 = (FlowPane) pane2.getContent();
        if(Main.game.getZones()[Main.currentZone].getCombattantP2().size() > 0){
            flowPane2.setStyle("-fx-background-color: #7F9183; -fx-max-width: 400; -fx-orientation: HORIZONTAL; -fx-padding: 10; -fx-hgap: 10; ");
        } else{
            flowPane2.setStyle("-fx-background-color: #ce4257; -fx-max-width: 400; -fx-orientation: HORIZONTAL; -fx-padding: 10; -fx-hgap: 10;");
        }
    }

    public void nonDroppableZone(ScrollPane scrollPane, List<Combattant> combattants) {
        FlowPane flowPane = (FlowPane) scrollPane.getContent();
        flowPane.getChildren().clear();
        for (Combattant combattant : combattants) {
            flowPane.getChildren().add(UtilsGUI.buildNonDraggable(combattant));
        }
    }



    public void next() throws IOException {
        if (done) {
            if (Main.game.getPlayers()[0].getScore() == 3 || Main.game.getPlayers()[1].getScore() == 3) {
                Main.setScene("results.fxml");
            }else{
                Main.setScene("truce.fxml");
            }
        }
    }

    public void start() {
        if(!done){
            Main.game.getZones()[Main.currentZone].battle();
            Main.game.getZones()[Main.currentZone].results();
            guiSetup();
            customColor(zoneP1, zoneP2);
            done=true;
        }
    }


}
