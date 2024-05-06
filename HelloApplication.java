package com.example.algorithm;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Screen;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class HelloApplication extends Application {
    private static int numNodes = 0; // Number of nodes in the system
    private static final int K = 10; // Modify K as needed
    private static final int nodeRadius = 30; // Radius of node circles
    private Circle[] circles;
    private int[] rounds;
    public Node[] nodes;
    public Button[] buttons;
    private boolean firstRun = true; // Flag for first run
    public boolean step = false; // Flag for step-by-step execution
    public Logic logic = null; // Object to handle logic

    /**
     * Start method of JavaFX Application
     * @param stage Stage object for the application
     * @throws IOException Exception thrown in case of I/O error
     */
    @Override
    public void start(Stage stage) throws IOException {
        // Initialize the root pane
        Pane root = new Pane();
        
     // Get the screen dimensions
        Screen screen = Screen.getPrimary();
        double screenWidth = screen.getBounds().getWidth();
        double screenHeight = screen.getBounds().getHeight();
        
     // Set the scene dimensions to match the screen size
        Scene scene = new Scene(root, screenWidth-50, screenHeight-100);

        // Set up UI controls
        HBox controls = new HBox(10); // Horizontal box for control elements
        Label numNodesLabel = new Label("Enter number of Nodes:"); // Label for node count
        TextField numNodesTextField = new TextField(); // Text field for user input

        // Create condition and info labels
        Label redText = new Label("Can't get the privilege -");
        Label yellowText = new Label("Can get the privilege -");
        Label greenText = new Label("Have the turn -");
        VBox conditions = new VBox(20); // Vertical box for condition elements
        Label L1 = new Label("condition to get the turn - ");
        Label condition1 = new Label("1. if (not eq[i] and test(i)) then eq[i] = true");
        Label condition2= new Label("2.if(eq[i] and s[i]≠s[sup[i]])"+"\n"+"\t" +"then if sup[i]=0 "+"\n"+"\t"+"\t" +"then s[i]=(s[i]+1)modK"+"\n"+"\t"+"\t" +"else s[i]=s[sup[i]]"+"\n" +"else eq[i]=false");

        // Add condition elements to the box
        conditions.getChildren().addAll(L1, condition1, condition2);
        conditions.setStyle("-fx-padding: 10;" + "-fx-font-size: 20;" + "-fx-border-style: solid inside;" + "-fx-border-width: 2;" + "-fx-border-height: 2;" + "-fx-border-insets: 15;" + "-fx-border-radius: 5;" + "-fx-border-color: gray;");
        conditions.setLayoutX(scene.getWidth() / 2 + 200);
        conditions.setPrefWidth(500);
        
        // Set up info section
        HBox info = new HBox(10);
        Circle red = new Circle(10, Color.RED); 
        Circle yellow = new Circle(10, Color.YELLOW); 
        Circle green = new Circle(10, Color.GREEN); 
        info.getChildren().addAll(redText, red, yellowText, yellow, greenText, green);
        info.setStyle("-fx-padding: 10;" + "-fx-border-style: solid inside;" + "-fx-border-width: 2;" + "-fx-border-height: 2;" + "-fx-border-insets: 15;" + "-fx-border-radius: 5;" + "-fx-border-color: black;");

        // Create buttons for control
        Button stepButton = new Button("Step"); // Step button
        stepButton.setStyle("-fx-background-color: #008CBA; -fx-text-fill: white; -fx-font-size: 16px; -fx-padding: 6px 10px; -fx-border-radius: 4px;"); // Style the button
        stepButton.setOnAction(e -> {
            stepButton.setStyle("-fx-background-color: #ADD8E6; -fx-text-fill: black; -fx-font-size: 16px; -fx-padding: 6px 10px; -fx-border-radius: 4px;"); // Change button color on click
            step = true; // Set step flag to true
        });

        Button playButton = new Button("Play"); // Play button
        playButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;  -fx-font-size: 16px; -fx-padding: 6px 10px; -fx-border-radius: 4px;"); // Style the button
        playButton.setOnAction(e -> {
            if (numNodesTextField.getText().isEmpty()) { // Check if input is empty
                Alert notification = new Alert(AlertType.INFORMATION); // Create alert
                notification.setTitle("Attention"); // Set alert title
                notification.setHeaderText(null); // Set header text
                notification.setContentText("You must enter number of nodes"); // Set alert content
                notification.showAndWait(); // Show alert
                return;
            }
            int numNodes = Integer.parseInt(numNodesTextField.getText()); // Parse input to integer
            logic = new Logic(root, step, numNodes, scene); // Initialize logic object
            logic.DrawAndRun(); // Execute logic
        });

        Button pauseButton = new Button("Pause"); // Pause button
        pauseButton.setStyle("-fx-background-color: #008CBA; -fx-text-fill: white; -fx-font-size: 16px; -fx-padding: 6px 10px; -fx-border-radius: 4px;"); // Style the button
        pauseButton.setOnAction(e -> {
            if (logic != null) {
                logic.pauseAlgorithm(pauseButton); // Pause algorithm function call
            }
        });

        Button statistics = new Button("Statistics"); // Statistics button
        statistics.setStyle("-fx-background-color: #008CBA; -fx-text-fill: white; -fx-font-size: 16px; -fx-padding: 6px 10px; -fx-border-radius: 4px;"); // Style the button
        statistics.setOnAction(e -> {
            Statistics statsWindow = new Statistics(); // Create statistics window
            statsWindow.start(stage); // Start statistics window
        });

        // Add control elements to controls box
        controls.getChildren().addAll(numNodesLabel, numNodesTextField, playButton, stepButton, pauseButton, statistics, info);

        // Add controls and conditions to root pane
        root.getChildren().addAll(controls, conditions);

        // Set up the stage
        stage.setScene(scene); // Set scene
        stage.setTitle("Algorithm Visualization"); // Set title
        stage.show(); // Show stage
    }

    /**
     * Main method to launch the application
     * @param args Command-line arguments
     */
    public static void main(String[] args) {
        launch(); // Launch application
    }
}
