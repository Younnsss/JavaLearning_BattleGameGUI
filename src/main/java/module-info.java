module com.example.javalearningbattlegame {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.javalearningbattlegame to javafx.fxml;
    exports com.example.javalearningbattlegame;
}