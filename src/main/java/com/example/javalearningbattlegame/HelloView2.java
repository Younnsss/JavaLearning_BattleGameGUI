package com.example.javalearningbattlegame;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import models.Combattant;
import models.Player;

import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

public class HelloView2 implements Initializable {
    @FXML
    private HBox hboxP1;
    @FXML
    private Button valid;
    @FXML
    private HBox hboxP2;
    @FXML
    private VBox customBoxP1;
    @FXML
    private VBox customBoxP2;
    @FXML
    private Label credP1;
    @FXML
    private Label credP2;
    private List<Combattant> combattantsP1 = HelloApplication.game.getPlayers()[0].getCombattant();
    private List<Combattant> combattantsP2 = HelloApplication.game.getPlayers()[1].getCombattant();
    private Player player1 = HelloApplication.game.getPlayers()[0];
    private Player player2 = HelloApplication.game.getPlayers()[1];

    private Slider forSliderP1;
    private Slider dexSliderP1;
    private Slider iniSliderP1;
    private Slider resSliderP1;
    private Slider consSliderP1;

    private Slider forSliderP2;
    private Slider dexSliderP2;
    private Slider iniSliderP2;
    private Slider resSliderP2;
    private Slider consSliderP2;

    public void validate() throws IOException {
        HelloApplication.setScene("deploy.fxml");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        credP1.setText("Crédits : " + player1.getCreditECTS());
        credP2.setText("Crédits : " + player2.getCreditECTS());
        setup();
    }

    public void setup() {
        for (int i = 0; i < combattantsP1.size(); i++) {
            VBox vbx = (VBox) hboxP1.getChildren().get(i / 3);
            if ((i / 3) != ((i - 1) / 3) || i == 0) {
                vbx.getChildren().clear();
            }
            vbx.getChildren().add(buildCombP1(combattantsP1.get(i)));
        }
        for (int i = 0; i < combattantsP2.size(); i++) {
            VBox vbx = (VBox) hboxP2.getChildren().get(i / 3);
            if ((i / 3) != ((i - 1) / 3) || i == 0) {
                vbx.getChildren().clear();
            }
            vbx.getChildren().add(buildCombP2(combattantsP2.get(i)));
        }
    }


    public void buildCustomBoxP1(Combattant combattant, Button btn) {

        forSliderP1 = buildSliderP1(combattant.getForce(), combattant.getRole().getForce());
        forSliderP1.valueProperty().addListener((observable, oldValue, newValue) -> {
                combattant.setForce(combattant.getForce() + newValue.intValue() - oldValue.intValue());
                updateP1(btn, combattant, oldValue.intValue() - newValue.intValue());
        });

        dexSliderP1 = buildSliderP1(combattant.getDexterite(), combattant.getRole().getDexterite());
        dexSliderP1.valueProperty().addListener((observable, oldValue, newValue) -> {
                combattant.setDexterite(combattant.getDexterite() + newValue.intValue() - oldValue.intValue());
                updateP1(btn, combattant, oldValue.intValue() - newValue.intValue());
        });

        iniSliderP1 = buildSliderP1(combattant.getInitiative(), combattant.getRole().getInitiative());
        iniSliderP1.valueProperty().addListener((observable, oldValue, newValue) -> {
                combattant.setInitiative(combattant.getInitiative() + newValue.intValue() - oldValue.intValue());
                updateP1(btn, combattant, oldValue.intValue() - newValue.intValue());
        });

        consSliderP1 = buildSliderP1(combattant.getConstitution(), combattant.getRole().getConstitution());
        consSliderP1.valueProperty().addListener((observable, oldValue, newValue) -> {
                combattant.setConstitution(combattant.getConstitution() + newValue.intValue() - oldValue.intValue());
                updateP1(btn, combattant, oldValue.intValue() - newValue.intValue());
        });

        resSliderP1 = buildSliderP1(combattant.getResistance(), combattant.getRole().getResistance());
        resSliderP1.valueProperty().addListener((observable, oldValue, newValue) -> {
                combattant.setResistance(combattant.getResistance() + newValue.intValue() - oldValue.intValue());
                updateP1(btn, combattant, oldValue.intValue() - newValue.intValue());
        });
        Button btn2 = new Button("Random");
        btn2.setOnAction((ActionEvent event) -> {
            HelloApplication.game.getPlayers()[0].setCreditECTS(400);
            HelloApplication.game.initAutoComb(0);
            this.combattantsP1 = HelloApplication.game.getPlayers()[0].getCombattant();
            setup();
            credP1.setText("Crédits : " + player1.getCreditECTS());
            customBoxP1.getChildren().clear();
        });

        customBoxP1.getChildren().clear();
        customBoxP1.getChildren().addAll(forSliderP1, dexSliderP1, iniSliderP1, consSliderP1, resSliderP1, btn2);


    }

    public void buildCustomBoxP2(Combattant combattant, Button btn) {

        forSliderP2 = buildSliderP2(combattant.getForce(), combattant.getRole().getForce());
        forSliderP2.valueProperty().addListener((observable, oldValue, newValue) -> {
            combattant.setForce(combattant.getForce() + newValue.intValue() - oldValue.intValue());
            updateP2(btn, combattant, oldValue.intValue() - newValue.intValue());
        });

        dexSliderP2 = buildSliderP2(combattant.getDexterite(), combattant.getRole().getDexterite());
        dexSliderP2.valueProperty().addListener((observable, oldValue, newValue) -> {
            combattant.setDexterite(combattant.getDexterite() + newValue.intValue() - oldValue.intValue());
            updateP2(btn, combattant, oldValue.intValue() - newValue.intValue());
        });

        iniSliderP2 = buildSliderP2(combattant.getInitiative(), combattant.getRole().getInitiative());
        iniSliderP2.valueProperty().addListener((observable, oldValue, newValue) -> {
            combattant.setInitiative(combattant.getInitiative() + newValue.intValue() - oldValue.intValue());
            updateP2(btn, combattant, oldValue.intValue() - newValue.intValue());
        });

        consSliderP2 = buildSliderP2(combattant.getConstitution(), combattant.getRole().getConstitution());
        consSliderP2.valueProperty().addListener((observable, oldValue, newValue) -> {
            combattant.setConstitution(combattant.getConstitution() + newValue.intValue() - oldValue.intValue());
            updateP2(btn, combattant, oldValue.intValue() - newValue.intValue());
        });

        resSliderP2 = buildSliderP2(combattant.getResistance(), combattant.getRole().getResistance());
        resSliderP2.valueProperty().addListener((observable, oldValue, newValue) -> {
            combattant.setResistance(combattant.getResistance() + newValue.intValue() - oldValue.intValue());
            updateP2(btn, combattant, oldValue.intValue() - newValue.intValue());
        });
        Button btn2 = new Button("Random");
        btn2.setOnAction((ActionEvent event) -> {
            HelloApplication.game.getPlayers()[1].setCreditECTS(400);
            HelloApplication.game.initAutoComb(1);
            this.combattantsP2 = HelloApplication.game.getPlayers()[1].getCombattant();
            setup();
            credP2.setText("Crédits : " + player2.getCreditECTS());
            customBoxP2.getChildren().clear();
        });

        customBoxP2.getChildren().clear();
        customBoxP2.getChildren().addAll(forSliderP2, dexSliderP2, iniSliderP2, consSliderP2, resSliderP2, btn2);


    }

    public void updateP1(Button btn, Combattant combattant, int value) {
        btn.setText(Arrays.toString(combattant.getStats()));
        player1.setCreditECTS(player1.getCreditECTS() + value);
        credP1.setText("Crédits restants : " + player1.getCreditECTS());
        resSliderP1.setMax(Math.min(player1.getCreditECTS() + combattant.getResistance(), 10));
        consSliderP1.setMax(Math.min(player1.getCreditECTS() + combattant.getConstitution(), 10));
        iniSliderP1.setMax(Math.min(player1.getCreditECTS() + combattant.getInitiative(), 10));
        dexSliderP1.setMax(Math.min(player1.getCreditECTS() + combattant.getDexterite(), 10));
        forSliderP1.setMax(Math.min(player1.getCreditECTS() + combattant.getForce(), 10));
    }
    public void updateP2(Button btn, Combattant combattant, int value) {
        btn.setText(Arrays.toString(combattant.getStats()));
        player2.setCreditECTS(player2.getCreditECTS() + value);
        credP2.setText("Crédits restants : " + player2.getCreditECTS());
        resSliderP2.setMax(Math.min(player2.getCreditECTS() + combattant.getResistance(), 10));
        consSliderP2.setMax(Math.min(player2.getCreditECTS() + combattant.getConstitution(), 10));
        iniSliderP2.setMax(Math.min(player2.getCreditECTS() + combattant.getInitiative(), 10));
        dexSliderP2.setMax(Math.min(player2.getCreditECTS() + combattant.getDexterite(), 10));
        forSliderP2.setMax(Math.min(player2.getCreditECTS() + combattant.getForce(), 10));
    }

    public Slider buildSliderP1(int value, int min) {
        Slider slider = new Slider(0, 10, 0);
        slider.setValue(value);
        slider.setMin(min);
        slider.setMax(Math.min(player1.getCreditECTS() + value, 10));
        slider.setShowTickLabels(true);
        slider.setShowTickMarks(true);
        slider.setMajorTickUnit(1);
        slider.setMinorTickCount(0);
        slider.setBlockIncrement(1);
        slider.setSnapToTicks(true);
        slider.setPrefWidth(200);
        return slider;
    }

    public Slider buildSliderP2(int value, int min) {
        Slider slider = new Slider(0, 10, 0);
        slider.setValue(value);
        slider.setMin(min);
        slider.setMax(Math.min(player2.getCreditECTS() + value, 10));
        slider.setShowTickLabels(true);
        slider.setShowTickMarks(true);
        slider.setMajorTickUnit(1);
        slider.setMinorTickCount(0);
        slider.setBlockIncrement(1);
        slider.setSnapToTicks(true);
        slider.setPrefWidth(200);
        return slider;
    }


    public Button buildCombP1(Combattant combattant) {
        Button btn = new Button();
        btn.setText(Arrays.toString(combattant.getStats()));
        btn.setOnAction(event -> {
            buildCustomBoxP1(combattant, btn);
        });
        return btn;
    }
    public Button buildCombP2(Combattant combattant) {
        Button btn = new Button();
        btn.setText(Arrays.toString(combattant.getStats()));
        btn.setOnAction(event -> {
            buildCustomBoxP2(combattant, btn);
        });
        return btn;
    }


}