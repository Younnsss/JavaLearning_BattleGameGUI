package com.example.javalearningbattlegame;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class HelloController implements Initializable {
    @FXML
    private Label welcomeText;
    @FXML
    private TextField pseudoP1;
    @FXML
    private TextField pseudoP2;
    @FXML
    private ChoiceBox<String> filiereP1;
    @FXML
    private ChoiceBox<String> filiereP2;

    private String[] filieres = new String[] {"ISI", "RT", "GM", "GI", "MTE"};

    @FXML
    protected void onHelloButtonClick() throws IOException {
        HelloApplication.initGame(pseudoP1.getText().toString(), filiereP1.getValue().toString() ,pseudoP2.getText().toString(), filiereP2.getValue().toString());
        HelloApplication.setScene("hello-view2.fxml");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if(filiereP1!=null && filiereP2!=null) {
            filiereP1.getItems().addAll(filieres);
            filiereP2.getItems().addAll(filieres);
        }

    }
}