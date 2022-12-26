package com.example.javalearningbattlegame;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import models.Combattant;
import models.Zone;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import static com.example.javalearningbattlegame.UtilsGUI.buildDrop;

public class Battle implements Initializable {

    @FXML
    private Label error;
    @FXML
    private Label title;
    @FXML
    private Label score1;
    @FXML
    private Label score2;
    @FXML
    private ImageView img;
    @FXML
    private Label player;
    @FXML
    private ScrollPane reserve;
    @FXML
    private ScrollPane quartier;
    @FXML
    private ScrollPane biblio;
    @FXML
    private ScrollPane halleI;
    @FXML
    private ScrollPane halleS;
    @FXML
    private ScrollPane bureau;

    @FXML
    private FlowPane bib;
    @FXML
    private FlowPane hs;
    @FXML
    private FlowPane hi;
    @FXML
    private FlowPane qa;
    @FXML
    private FlowPane bde;

    private List<Combattant> resDep = new ArrayList<>();
    private List<Combattant> zoneDep = new ArrayList<>();

    private Zone currentZone;

    private boolean done = false;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        gameManagement();
        guiSetup();
    }

    public void gameManagement() {
        int zone = HelloApplication.game.chooseZone();
        HelloApplication.game.getZones()[zone].battle();
        HelloApplication.game.getZones()[zone].results();
        this.currentZone = HelloApplication.game.getZones()[zone];
        done = false;
        score1.setText(String.valueOf(HelloApplication.game.getPlayers()[0].getScore()));
        score2.setText(String.valueOf(HelloApplication.game.getPlayers()[1].getScore()));
        if (HelloApplication.game.getPlayers()[0].getScore() == 3 || HelloApplication.game.getPlayers()[1].getScore() == 3) {
            try {
                HelloApplication.setScene("results.fxml");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void guiSetup() {
        img.setImage( new Image(HelloApplication.class.getResource(done?"soldat.png":"captain.png").toExternalForm()));
        player.setText(HelloApplication.game.getPlayers()[done?1:0].getPseudo());
        ScrollPane[] zones = {biblio, bureau, quartier, halleI, halleS};
        FlowPane[] zonesName = {bib, bde, qa, hi, hs};
        List<Combattant> combs = done ? HelloApplication.game.getPlayers()[1].getReserviste() : HelloApplication.game.getPlayers()[0].getReserviste();
        draggableZone(reserve, combs);
        for (Zone zone : HelloApplication.game.getZones()) {
            combs = done ? zone.getCombattantP2() : zone.getCombattantP1();
            resetZone(zones[HelloApplication.game.getZoneIndex(zone)]);
            if (zone.getIsFinish()) {
                customColor(zone, zones[HelloApplication.game.getZoneIndex(zone)], zonesName[HelloApplication.game.getZoneIndex(zone)]);
                if (done ? zone.getCombattantP2().size() > 1 : zone.getCombattantP1().size() > 1) {
                    dropDragZone(zones[HelloApplication.game.getZoneIndex(zone)], combs);
                } else {
                    nonDroppableZone(zones[HelloApplication.game.getZoneIndex(zone)], combs);
                }
            } else {
                nonDraggableZone(zones[HelloApplication.game.getZoneIndex(zone)], combs);
            }
        }
    }

    public void customColor(Zone zone, ScrollPane pane, FlowPane name) {
        FlowPane flowPane = (FlowPane) pane.getContent();
        if(done ? zone.getCombattantP2().size() > 0 : zone.getCombattantP1().size() > 0){
            flowPane.setStyle("-fx-background-color: #7F9183; -fx-max-height: 130; -fx-orientation: VERTICAL; -fx-padding: 10; -fx-hgap: 10; ");
            name.setStyle("-fx-padding: 3; -fx-background-radius: 10;-fx-background-color: #7F9183");
        } else{
            flowPane.setStyle("-fx-background-color: #ce4257; -fx-max-height: 130; -fx-orientation: VERTICAL; -fx-padding: 10; -fx-hgap: 10;");
            name.setStyle("-fx-padding: 3; -fx-background-radius: 10;-fx-background-color: #ce4257");
        }
    }

    public void resetZone(ScrollPane zone) {
        zone.setOnDragEntered(null);
        zone.setOnDragExited(null);
        zone.setOnDragOver(null);
        zone.setOnDragDropped(null);
    }

    public void dropDragZone(ScrollPane scrollPane, List<Combattant> combattants) {
        buildDrop(scrollPane);
        FlowPane flowPane = (FlowPane) scrollPane.getContent();
        flowPane.getChildren().clear();
        for (Combattant combattant : combattants) {
            if (scrollPane == reserve) {
                flowPane.getChildren().add(UtilsGUI.buildDraggable(combattant, combattants.indexOf(combattant)));
            } else {
                flowPane.getChildren().add(UtilsGUI.buildDraggable(combattant, 5 + combattants.indexOf(combattant)));
            }
        }
    }

    public void draggableZone(ScrollPane scrollPane, List<Combattant> combattants) {
        FlowPane flowPane = (FlowPane) scrollPane.getContent();
        flowPane.getChildren().clear();
        for (Combattant combattant : combattants) {
            if (scrollPane == reserve) {
                flowPane.getChildren().add(UtilsGUI.buildDraggable(combattant, combattants.indexOf(combattant)));
            } else {
                flowPane.getChildren().add(UtilsGUI.buildDraggable(combattant, 5 + combattants.indexOf(combattant)));
            }
        }
    }

    public void nonDroppableZone(ScrollPane scrollPane, List<Combattant> combattants) {
        FlowPane flowPane = (FlowPane) scrollPane.getContent();
        flowPane.getChildren().clear();
        for (Combattant combattant : combattants) {
            flowPane.getChildren().add(UtilsGUI.buildNonDraggable(combattant));
        }
    }

    public void redeploy() {
        this.resDep.clear();
        this.zoneDep.clear();
        ScrollPane[] zones = {biblio, bureau, quartier, halleI, halleS};
        for (Zone zone : HelloApplication.game.getZones()) {
            FlowPane pane = (FlowPane) zones[HelloApplication.game.getZoneIndex(zone)].getContent();
            for (int i = 0; i < pane.getChildren().size(); i++) {
                HBox hBox = (HBox) pane.getChildren().get(i);
                if (hBox.getId() != null) {
                    deploy(hBox.getId(), zone);
                }
            }
        }
        if (done) {
            HelloApplication.game.getPlayers()[1].getReserviste().removeAll(resDep);
            currentZone.combattantP2.removeAll(zoneDep);
        } else {
            HelloApplication.game.getPlayers()[0].getReserviste().removeAll(resDep);
            currentZone.combattantP1.removeAll(zoneDep);
        }
    }

    public void deploy(String id, Zone zone) {
        int index = Integer.parseInt(id) < 5 ? Integer.parseInt(id) : Integer.parseInt(id) - 5;
        if (Integer.parseInt(id) < 5) {
            if (done) {
                Combattant combattant = HelloApplication.game.getPlayers()[1].getReserviste().get(index);
                this.resDep.add(combattant);
                zone.getCombattantP2().add(combattant);
            } else {
                Combattant combattant = HelloApplication.game.getPlayers()[0].getReserviste().get(index);
                this.resDep.add(combattant);
                zone.getCombattantP1().add(combattant);
            }
        } else {
            if (zone != currentZone) {
                if (done) {
                    Combattant combattant = currentZone.combattantP2.get(index);
                    this.zoneDep.add(combattant);
                    zone.getCombattantP2().add(combattant);
                } else {
                    Combattant combattant = currentZone.combattantP1.get(index);
                    this.zoneDep.add(combattant);
                    zone.getCombattantP1().add(combattant);
                }
            }

        }

    }

    public void next() {
        if (validate()) {
            redeploy();
            if (!done) {
                done = true;
                guiSetup();
            } else {
                gameManagement();
                guiSetup();
            }
        }
    }

    public boolean validate() {
        ScrollPane[] zones = {biblio, bureau, quartier, halleI, halleS};
        for (Zone zone : HelloApplication.game.getZones()) {
            if (zone.getIsFinish() && (done ? zone.getCombattantP2().size() > 0 : zone.getCombattantP1().size() > 0)) {
                FlowPane pane = (FlowPane) zones[HelloApplication.game.getZoneIndex(zone)].getContent();
                if (pane.getChildren().size() != 1) {
                    error.setText("You must keep only one combattant in each controlled zone");
                    return false;
                }
            }
        }
        return true;
    }

    public void nonDraggableZone(ScrollPane scrollPane, List<Combattant> combattants) {
        buildDrop(scrollPane);
        FlowPane flowPane = (FlowPane) scrollPane.getContent();
        flowPane.getChildren().clear();
        for (Combattant combattant : combattants) {
            flowPane.getChildren().add(UtilsGUI.buildNonDraggable(combattant));
        }
    }


}
