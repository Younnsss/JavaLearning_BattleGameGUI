package GUIController;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.ResourceBundle;

public class Results implements Initializable {
    @FXML
    private Label title1;
    @FXML
    private Label title2;
    @FXML
    private Label score1;
    @FXML
    private Label score2;
    @FXML
    private Label titleW;
    @FXML
    private ImageView imgW;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        title1.setText(Main.game.getPlayers()[0].getPseudo());
        title2.setText(Main.game.getPlayers()[1].getPseudo());
        score1.setText(String.valueOf(Main.game.getPlayers()[0].getScore()));
        score2.setText(String.valueOf(Main.game.getPlayers()[1].getScore()));
        if(Main.game.getPlayers()[0].getScore() > Main.game.getPlayers()[1].getScore()){
            titleW.setText(Main.game.getPlayers()[0].getPseudo());
            imgW.setImage(new Image(Main.class.getResource("captain.png").toExternalForm()));
        }else if(Main.game.getPlayers()[0].getScore() < Main.game.getPlayers()[1].getScore()){
            titleW.setText(Main.game.getPlayers()[1].getPseudo());
            imgW.setImage(new Image(Main.class.getResource("soldat.png").toExternalForm()));
        }
    }
}
