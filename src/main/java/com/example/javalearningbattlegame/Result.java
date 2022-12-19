package com.example.javalearningbattlegame;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import models.Combattant;
import models.Zone;

import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;

public class Result implements Initializable {

    @FXML
    private VBox vbox;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        for(Zone zone : HelloApplication.game.getZones()) {
            vbox.getChildren().add(new Text(zone.getName()));
            vbox.getChildren().add(new Text("PLayer 1"));
            HBox hbox = new HBox();
            for(Combattant combattant : zone.getCombattantP1()) {
                hbox.getChildren().add(new Text(Arrays.toString(combattant.getStats())));
            }
            vbox.getChildren().add(hbox);
            vbox.getChildren().add(new Text("PLayer 2"));
            hbox = new HBox();
            for(Combattant combattant : zone.getCombattantP2()) {
                hbox.getChildren().add(new Text(Arrays.toString(combattant.getStats())));
            }
            vbox.getChildren().add(hbox);
        }
        HBox hbox = new HBox();
        vbox.getChildren().add(new Text("Player 1"));
        for(Combattant combattant : HelloApplication.game.getPlayers()[0].getReserviste()) {
            hbox.getChildren().add(new Text(Arrays.toString(combattant.getStats())));
        }
        vbox.getChildren().add(hbox);
        hbox = new HBox();
        vbox.getChildren().add(new Text("Player 2"));
        for(Combattant combattant : HelloApplication.game.getPlayers()[1].getReserviste()) {
            hbox.getChildren().add(new Text(Arrays.toString(combattant.getStats())));
        }
        vbox.getChildren().add(hbox);
    }
}
