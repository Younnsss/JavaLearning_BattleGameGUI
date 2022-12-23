package com.example.javalearningbattlegame;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import models.Combattant;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

public class Deploy implements Initializable {
    private List<Combattant> combattantsP1 = HelloApplication.game.getPlayers()[0].getCombattant();
    private List<Combattant> combattantsP2 = HelloApplication.game.getPlayers()[1].getCombattant();

    @FXML
    private FlowPane pane;
    @FXML
    private HBox hboxD;
    @FXML
    private Label error;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        error.setText("");
        setup(combattantsP2);
    }

    public void setup(List<Combattant> combattants) {
        hboxD.getChildren().clear();
        pane.getChildren().clear();
        for (int i = 0; i < combattants.size(); i++) {
            pane.getChildren().add(buildDraggable(combattants.get(i), i));
        }
        for (int i = 0; i < 6; i++) {
            hboxD.getChildren().add(buildDrop());
        }
    }

    public void deployComb() {
        List<Combattant> combFrom = combattantsP2.size() == 0 ? combattantsP1 : combattantsP2;
        for (int i = 0; i < 5; i++) {
            ScrollPane pane = (ScrollPane) hboxD.getChildren().get(i);
            VBox vbox = (VBox) pane.getContent();
            List<Combattant> combZone = new ArrayList<Combattant>();
            for (int j = 0; j < vbox.getChildren().size(); j++) {
                HBox hbx = (HBox) vbox.getChildren().get(j);
                combZone.add(combFrom.get(Integer.parseInt(hbx.getId())));
            }
            if(combattantsP2.size() == 0) {
                HelloApplication.game.getZones()[i].setCombattantP1(combZone);
            } else {
                HelloApplication.game.getZones()[i].setCombattantP2(combZone);
            }
        }
        ScrollPane pane = (ScrollPane) hboxD.getChildren().get(5);
        VBox vbox = (VBox) pane.getContent();
        List<Combattant> combZone = new ArrayList<Combattant>();
        for (int j = 0; j < vbox.getChildren().size(); j++) {
            HBox hbx = (HBox) vbox.getChildren().get(j);
            combZone.add(combFrom.get(Integer.parseInt(hbx.getId())));
        }
        if(combattantsP2.size() == 0) {
            HelloApplication.game.getPlayers()[0].setReserviste(combZone);
            this.combattantsP1.clear();
        } else {
            HelloApplication.game.getPlayers()[1].setReserviste(combZone);
            this.combattantsP2.clear();
        }

    }

    public void next() throws Exception {
        if (verifydata()) {
            deployComb();
            if (combattantsP1.size() != 0 || combattantsP2.size() != 0) {
                setup(combattantsP1);
            } else {
                HelloApplication.setScene("result.fxml");
            }
            System.out.println("ok");
        }
    }

    public boolean verifydata() {
        ScrollPane pane = (ScrollPane) hboxD.getChildren().get(hboxD.getChildren().size() - 1);
        VBox vbox = (VBox) pane.getContent();
        for (int i = 0; i < hboxD.getChildren().size(); i++) {
            ScrollPane pane2 = (ScrollPane) hboxD.getChildren().get(i);
            VBox vbox2 = (VBox) pane2.getContent();
            if (vbox2.getChildren().size() < 1) {
                error.setText("Veuillez placer au minimum un combatant dans chaque Zone");
                return false;
            }
        }
        if (this.pane.getChildren().size() != 0) {
            error.setText("Vous devez placer tous vos combattants");
            return false;
        } else if (vbox.getChildren().size() != 5) {
            error.setText("Vous devez placer 5 réservistes");
            return false;
        }
        return true;
    }

    public ScrollPane buildDrop() {
        final ScrollPane target = new ScrollPane();
        target.setContent(new VBox());
        target.setOnDragOver(new EventHandler<DragEvent>() {
            public void handle(DragEvent event) {
                /* data is dragged over the target */
                System.out.println("onDragOver");

                /* accept it only if it is  not dragged from the same node
                 * and if it has a string data */
                if (event.getGestureSource() != target &&
                        event.getDragboard().hasString()) {
                    /* allow for both copying and moving, whatever user chooses */
                    event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
                }

                event.consume();
            }
        });

        target.setOnDragEntered(new EventHandler<DragEvent>() {
            public void handle(DragEvent event) {
                /* the drag-and-drop gesture entered the target */
                System.out.println("onDragEntered");
                /* show to the user that it is an actual gesture target */
                if (event.getGestureSource() != target &&
                        event.getDragboard().hasString()) {
                    target.setBackground(new Background(new BackgroundFill(Color.GREEN, null, null)));
                }

                event.consume();
            }
        });

        target.setOnDragExited(new EventHandler<DragEvent>() {
            public void handle(DragEvent event) {
                /* mouse moved away, remove the graphical cues */
                target.setBackground(new Background(new BackgroundFill(Color.GREEN, null, null)));

                event.consume();
            }
        });

        target.setOnDragDropped(new EventHandler<DragEvent>() {
            public void handle(DragEvent event) {
                /* data dropped */
                System.out.println("onDragDropped");
                /* if there is a string data on dragboard, read it and use it */
                Dragboard db = event.getDragboard();
                boolean success = false;
                if (db.hasString()) {
                    VBox vbx = (VBox) target.getContent();
                    vbx.getChildren().add((HBox)event.getGestureSource());
                    success = true;
                }
                /* let the source know whether the string was successfully
                 * transferred and used */
                event.setDropCompleted(success);

                event.consume();
            }
        });
        return target;
    }

    public HBox buildDraggable(Combattant combattant, int id) {
        HBox source = new HBox();
        source.getStyleClass().add("combattant");

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

        source.getChildren().addAll(imgView, vbox1, vbox2);
        String[] icones = {"dex","for", "res","cons","ini", "cred"};
        int[] values = combattant.getStats();
        for (int i = 0; i < 6; i++) {
            HBox hbox = new HBox();
            hbox.getChildren().add(new ImageView(new Image(HelloApplication.class.getResource("icones/" + icones[i] + ".png").toExternalForm())));
            hbox.getChildren().add(new Label(" "+String.valueOf(values[i])));
            hbox.getStyleClass().add("iconesRow");
            VBox vbox =(VBox) source.getChildren().get((i/3)+1);
            vbox.getChildren().add(hbox);
        }
        source.setId(String.valueOf(id));
        source.setOnDragDetected(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent event) {
                /* drag was detected, start drag-and-drop gesture*/
                System.out.println("onDragDetected");

                /* allow any transfer mode */
                Dragboard db = source.startDragAndDrop(TransferMode.ANY);

                /* put a string on dragboard */
                ClipboardContent content = new ClipboardContent();
                content.putString(Arrays.toString(combattant.getStats()));
                db.setContent(content);

                event.consume();
            }
        });

        source.setOnDragDone(new EventHandler<DragEvent>() {
            public void handle(DragEvent event) {
                /* the drag-and-drop gesture ended */
                System.out.println("onDragDone");
                /* if the data was successfully moved, clear it */
                if (event.getTransferMode() == TransferMode.MOVE) {
                    pane.getChildren().remove(source);
                }
                event.consume();
            }
        });

        return source;
    }
}
