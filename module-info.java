module com.example.algorithm {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;

    opens com.example.algorithm to javafx.fxml;
    exports com.example.algorithm;
}