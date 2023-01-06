package GUIController;

import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.util.converter.DoubleStringConverter;
import models.Combattant;

import java.util.Arrays;

public class UtilsGUI {

    public static Slider stratSlider(String value) {
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
    public static void buildDrop(ScrollPane target) {
        FlowPane pane = (FlowPane) target.getContent();
        pane.getChildren().clear();
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
                    FlowPane flowPane = (FlowPane) target.getContent();
                    flowPane.getChildren().add((HBox)event.getGestureSource());
                    success = true;
                }
                /* let the source know whether the string was successfully
                 * transferred and used */
                event.setDropCompleted(success);

                event.consume();
            }
        });
    }


    public static HBox buildDraggable(Combattant combattant, int id) {
        HBox source = buildNonDraggable(combattant);
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
                }
                event.consume();
            }
        });

        return source;
    }


    public static HBox buildNonDraggable(Combattant combattant){
        HBox source = new HBox();
        source.setId(null);
        source.setMinWidth(165);
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
        Image img = new Image(Main.class.getResource(combattant.getRole() + "/" + strategie + ".png").toExternalForm());
        ImageView imgView = new ImageView(img);
        imgView.setFitHeight(70);
        imgView.setFitWidth(45);

        source.getChildren().addAll(imgView, vbox1, vbox2);
        String[] icones = {"dex", "for", "res", "cons", "ini", "cred"};
        int[] values = combattant.getStats();
        for (int i = 0; i < 6; i++) {
            HBox hbox = new HBox();
            hbox.getChildren().add(new ImageView(new Image(Main.class.getResource("icones/" + icones[i] + ".png").toExternalForm())));
            hbox.getChildren().add(new Label(" " + String.valueOf(values[i])));
            hbox.getStyleClass().add("iconesRow");
            VBox vbox = (VBox) source.getChildren().get((i / 3) + 1);
            vbox.getChildren().add(hbox);
        }
        return source;
    }
}
