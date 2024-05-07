module com.example.algorithm {
	requires javafx.controls;
	requires javafx.fxml;

	requires org.controlsfx.controls;
	requires javafx.graphics;

	opens com.example.algorithm to javafx.fxml;
	exports algorithm; 
}
