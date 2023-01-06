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

public class Truce implements Initializable {

    private Slider straSlider;
    @FXML
    private VBox customBox;
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
    private HBox lastSelected=null;

    private boolean done = false;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        gameManagement();
        guiSetup();
    }

    public void gameManagement() {
        int zone = Main.game.chooseZone();
        Main.game.getZones()[zone].battle();
        Main.game.getZones()[zone].results();
        this.currentZone = Main.game.getZones()[zone];
        done = false;
        score1.setText(String.valueOf(Main.game.getPlayers()[0].getScore()));
        score2.setText(String.valueOf(Main.game.getPlayers()[1].getScore()));
        if (Main.game.getPlayers()[0].getScore() == 3 || Main.game.getPlayers()[1].getScore() == 3) {
            try {
                Main.setScene("results.fxml");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void guiSetup() {
        error.setText("");
        img.setImage( new Image(Main.class.getResource(done?"soldat.png":"captain.png").toExternalForm()));
        player.setText(Main.game.getPlayers()[done?1:0].getPseudo());
        ScrollPane[] zones = {biblio, bureau, quartier, halleI, halleS};
        FlowPane[] zonesName = {bib, bde, qa, hi, hs};
        List<Combattant> combs = done ? Main.game.getPlayers()[1].getReserviste() : Main.game.getPlayers()[0].getReserviste();
        draggableZone(reserve, combs);
        for (Zone zone : Main.game.getZones()) {
            combs = done ? zone.getCombattantP2() : zone.getCombattantP1();
            resetZone(zones[Main.game.getZoneIndex(zone)]);
            if (zone.getIsFinish()) {
                customColor(zone, zones[Main.game.getZoneIndex(zone)], zonesName[Main.game.getZoneIndex(zone)]);
                if (done ? zone.getCombattantP2().size() > 1 : zone.getCombattantP1().size() > 1) {
                    dropDragZone(zones[Main.game.getZoneIndex(zone)], combs);
                } else {
                    nonDroppableZone(zones[Main.game.getZoneIndex(zone)], combs);
                }
            } else {
                nonDraggableZone(zones[Main.game.getZoneIndex(zone)], combs);
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

    public void update(HBox box, Combattant combattant) {
        VBox vbx1 = (VBox) box.getChildren().get(1);
        VBox vbx2 = (VBox) box.getChildren().get(2);
        box.getChildren().clear();
        String strategie = "";
        if (combattant.getStrategie().equals("Offensive")) {
            strategie = "attaque";
        } else if (combattant.getStrategie().equals("Defensive")) {
            strategie = "defense";
        } else if (combattant.getStrategie().equals("Aleatoire")) {
            strategie = "random";
        }
        ImageView imgView = new ImageView(new Image(Main.class.getResource(combattant.getRole() + "/" + strategie + ".png").toExternalForm()));
        imgView.setFitHeight(70);
        imgView.setFitWidth(45);
        box.getChildren().addAll(imgView, vbx1, vbx2);
        box.setBackground(new Background(new BackgroundFill(Color.GREY, new CornerRadii(10), Insets.EMPTY)));
    }

    public void buildCustomBox(Combattant combattant, HBox box) {
        straSlider = UtilsGUI.stratSlider(combattant.getStrategie());
        straSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.intValue() == 0) {
                combattant.setStrategie("a");
            } else if (newValue.intValue() == 1) {
                combattant.setStrategie("r");
            } else {
                combattant.setStrategie("s");
            }
            update(box, combattant);
        });

        customBox.getChildren().clear();
        customBox.getChildren().addAll(straSlider);
    }

    public void dropDragZone(ScrollPane scrollPane, List<Combattant> combattants) {
        buildDrop(scrollPane);
        FlowPane flowPane = (FlowPane) scrollPane.getContent();
        flowPane.getChildren().clear();
        for (Combattant combattant : combattants) {
            HBox box = UtilsGUI.buildDraggable(combattant, 5 + combattants.indexOf(combattant));
            box.setOnMouseClicked((MouseEvent event) -> {
                buildCustomBox(combattant, box);
                if(lastSelected != null && lastSelected != box){
                    System.out.println("lastSelected != box");
                    lastSelected.setBackground(new Background(new BackgroundFill(Color.WHITE, new CornerRadii(10), Insets.EMPTY)));
                }
                box.setBackground(new Background(new BackgroundFill(Color.GREY, new CornerRadii(10), Insets.EMPTY)));
                lastSelected = box;
            });
            flowPane.getChildren().add(box);
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
        for (Zone zone : Main.game.getZones()) {
            FlowPane pane = (FlowPane) zones[Main.game.getZoneIndex(zone)].getContent();
            for (int i = 0; i < pane.getChildren().size(); i++) {
                HBox hBox = (HBox) pane.getChildren().get(i);
                if (hBox.getId() != null) {
                    deploy(hBox.getId(), zone);
                }
            }
        }
        if (done) {
            Main.game.getPlayers()[1].getReserviste().removeAll(resDep);
            currentZone.combattantP2.removeAll(zoneDep);
        } else {
            Main.game.getPlayers()[0].getReserviste().removeAll(resDep);
            currentZone.combattantP1.removeAll(zoneDep);
        }
    }

    public void deploy(String id, Zone zone) {
        int index = Integer.parseInt(id) < 5 ? Integer.parseInt(id) : Integer.parseInt(id) - 5;
        if (Integer.parseInt(id) < 5) {
            if (done) {
                Combattant combattant = Main.game.getPlayers()[1].getReserviste().get(index);
                this.resDep.add(combattant);
                zone.getCombattantP2().add(combattant);
            } else {
                Combattant combattant = Main.game.getPlayers()[0].getReserviste().get(index);
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
        for (Zone zone : Main.game.getZones()) {
            if (zone.getIsFinish() && (done ? zone.getCombattantP2().size() > 0 : zone.getCombattantP1().size() > 0)) {
                FlowPane pane = (FlowPane) zones[Main.game.getZoneIndex(zone)].getContent();
                if (pane.getChildren().size() != 1) {
                    error.setText("Vous devez conserver au minimum un combattant dans chaque zone controlée");
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
