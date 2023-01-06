module GUIController {
    requires javafx.controls;
    requires javafx.fxml;


    opens GUIController to javafx.fxml;
    exports GUIController;
}