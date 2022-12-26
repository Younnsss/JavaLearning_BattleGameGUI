package com.example.javalearningbattlegame;

import javafx.css.converter.StringConverter;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.util.converter.DoubleStringConverter;
import models.*;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class HelloView2 implements Initializable {
    @FXML
    private FlowPane basique;

    @FXML
    private FlowPane maitreGobi;
    @FXML
    private FlowPane  elite;
    @FXML
    private VBox customBox;
    @FXML
    private Label cred;
    @FXML
    private Label title;
    @FXML
    private ImageView img;
    @FXML
    private Label error;
    private Slider forSlider;
    private Slider dexSlider;
    private Slider iniSlider;
    private Slider resSlider;
    private Slider consSlider;
    private Slider straSlider;
    private boolean done = false;


    public void validate() throws IOException {
        if (done) {
            if (HelloApplication.game.getPlayers()[1].getCreditECTS() != 0) {
                error.setText("Vous n'avez pas utilisé tous vos crédits");
            } else {
                HelloApplication.setScene("deploy.fxml");
            }
        } else {
            if (HelloApplication.game.getPlayers()[0].getCreditECTS() != 0) {
                error.setText("Vous n'avez pas utilisé tous vos crédits");
            } else {
                done = true;
                setup();
            }
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setup();
    }

    public void setup() {
        Player player = done ? HelloApplication.game.getPlayers()[1] : HelloApplication.game.getPlayers()[0];
        title.setText(player.getPseudo());
        img.setImage( new Image(HelloApplication.class.getResource(done?"soldat.png":"captain.png").toExternalForm()));
        cred.setText("Crédits Restants: " + player.getCreditECTS());
        maitreGobi.getChildren().clear();
        maitreGobi.getChildren().add(buildComb(player.getCombattant().get(0), player));
        elite.getChildren().clear();
        for (int i = 1; i < 5; i++) {
            elite.getChildren().add(buildComb(player.getCombattant().get(i), player));
        }
        basique.getChildren().clear();
        for (int j = 5; j < 20; j++) {
            basique.getChildren().add(buildComb(player.getCombattant().get(j), player));
        }
    }

    public void randomize() {
        if (done) {
            HelloApplication.game.getPlayers()[1].setCreditECTS(400);
            HelloApplication.game.initAutoComb(1);
        } else {
            HelloApplication.game.getPlayers()[0].setCreditECTS(400);
            HelloApplication.game.initAutoComb(0);
        }
        setup();
        customBox.getChildren().clear();
    }


    public void buildCustomBox(Combattant combattant, HBox box, Player player) {

        forSlider = buildSlider(combattant.getForce(), combattant.getRole().getForce(), player.getCreditECTS());
        forSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            combattant.setForce(combattant.getForce() + newValue.intValue() - oldValue.intValue());
            update(box, combattant, oldValue.intValue() - newValue.intValue(), player);
        });

        dexSlider = buildSlider(combattant.getDexterite(), combattant.getRole().getDexterite(), player.getCreditECTS());
        dexSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            combattant.setDexterite(combattant.getDexterite() + newValue.intValue() - oldValue.intValue());
            update(box, combattant, oldValue.intValue() - newValue.intValue(), player);
        });

        iniSlider = buildSlider(combattant.getInitiative(), combattant.getRole().getInitiative(), player.getCreditECTS());
        iniSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            combattant.setInitiative(combattant.getInitiative() + newValue.intValue() - oldValue.intValue());
            update(box, combattant, oldValue.intValue() - newValue.intValue(), player);
        });

        consSlider = buildSlider(combattant.getConstitution(), combattant.getRole().getConstitution(), player.getCreditECTS());
        consSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            combattant.setConstitution(combattant.getConstitution() + newValue.intValue() - oldValue.intValue());
            update(box, combattant, oldValue.intValue() - newValue.intValue(), player);
        });

        resSlider = buildSlider(combattant.getResistance(), combattant.getRole().getResistance(), player.getCreditECTS());
        resSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            combattant.setResistance(combattant.getResistance() + newValue.intValue() - oldValue.intValue());
            update(box, combattant, oldValue.intValue() - newValue.intValue(), player);
        });
        straSlider = stratSlider(combattant.getStrategie());
        straSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.intValue() == 0) {
                combattant.setStrategie("a");
            } else if (newValue.intValue() == 1) {
                combattant.setStrategie("r");
            } else {
                combattant.setStrategie("s");
            }
            update(box, combattant, 0, player);
        });

        customBox.getChildren().clear();
        customBox.getChildren().addAll(forSlider, dexSlider, iniSlider, consSlider, resSlider, straSlider);


    }


    public void update(HBox box, Combattant combattant, int value, Player player) {
        box.getChildren().clear();
        box.getChildren().add(buildComb(combattant, player));
        player.setCreditECTS(player.getCreditECTS() + value);
        cred.setText("Crédits restants : " + player.getCreditECTS());
        error.setText("");
        resSlider.setMax(Math.min(player.getCreditECTS() + combattant.getResistance(), 10));
        consSlider.setMax(Math.min(player.getCreditECTS() + combattant.getConstitution(), 10));
        iniSlider.setMax(Math.min(player.getCreditECTS() + combattant.getInitiative(), 10));
        dexSlider.setMax(Math.min(player.getCreditECTS() + combattant.getDexterite(), 10));
        forSlider.setMax(Math.min(player.getCreditECTS() + combattant.getForce(), 10));
    }

    public Slider stratSlider(String value) {
        Slider slider = new Slider(0, 2, 0);
        int val = value.equals("Offensive") ? 0 : value.equals("Aleatoire") ? 1 : 2;
        slider.setMin(0);
        slider.setMax(2);
        slider.setValue(val);
        slider.setMinorTickCount(0);
        slider.setMajorTickUnit(1);
        slider.setSnapToTicks(true);
        slider.setShowTickMarks(true);
        slider.setShowTickLabels(true);
        slider.setMaxWidth(150);
        slider.setLabelFormatter(new DoubleStringConverter() {
            @Override
            public String toString(Double n) {
                if (n < 0.5) return "Offensive";
                if (n < 1.5) return "Aleatoire";
                return "Defensive";
            }

            @Override
            public Double fromString(String s) {
                switch (s) {
                    case "Offensive":
                        return 0d;
                    case "Aleatoire":
                        return 1d;
                    default:
                        return 2d;
                }
            }
        });
        return slider;
    }

    public Slider buildSlider(int value, int min, int cred) {
        Slider slider = new Slider(0, 10, 0);
        slider.setValue(value);
        slider.setMin(min);
        slider.setMax(Math.min(cred + value, 10));
        slider.setShowTickLabels(true);
        slider.setShowTickMarks(true);
        slider.setMajorTickUnit(1);
        slider.setMinorTickCount(0);
        slider.setBlockIncrement(1);
        slider.setSnapToTicks(true);
        slider.setPrefWidth(200);
        return slider;
    }


    public HBox buildComb(Combattant combattant, Player player) {
        HBox box = new HBox();
        box.setMinWidth(165);
        box.getStyleClass().add("combattant");

        VBox vbox1 = new VBox();
        VBox vbox2 = new VBox();
        vbox1.getStyleClass().add("iconesCol");
        vbox2.getStyleClass().add("iconesCol");
        String strategie = "";
        if (combattant.getStrategie().equals("Offensive")) {
            strategie = "attaque";
        } else if (combattant.getStrategie().equals("Defensive")) {
            strategie = "defense";
        } else if (combattant.getStrategie().equals("Aleatoire")) {
            strategie = "random";
        }
        Image img = new Image(HelloApplication.class.getResource(combattant.getRole() + "/" + strategie + ".png").toExternalForm());
        ImageView imgView = new ImageView(img);
        imgView.setFitHeight(70);
        imgView.setFitWidth(45);

        box.getChildren().addAll(imgView, vbox1, vbox2);
        String[] icones = {"dex", "for", "res", "cons", "ini", "cred"};
        int[] values = combattant.getStats();
        for (int i = 0; i < 6; i++) {
            HBox hbox = new HBox();
            hbox.getChildren().add(new ImageView(new Image(HelloApplication.class.getResource("icones/" + icones[i] + ".png").toExternalForm())));
            hbox.getChildren().add(new Label(" " + String.valueOf(values[i])));
            hbox.getStyleClass().add("iconesRow");
            VBox vbox = (VBox) box.getChildren().get((i / 3) + 1);
            vbox.getChildren().add(hbox);
        }
        HBox finalBox = new HBox(box);
        box.setOnMouseClicked((MouseEvent event) -> {
            buildCustomBox(combattant, finalBox, player);
        });
        return finalBox;
    }


}