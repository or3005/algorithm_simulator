package com.example.algorithm;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

/**
 * The Logic class controls the execution of the algorithm visualization.
 * It manages the node layout, colors, and interactions.
 */
public class Logic {

    private  int numNodes = 0; // Number of nodes in the system
    private static final int K = 10; // Modify K as needed
    private static final int nodeRadius = 40; // Radius of node circles
    private Circle[] circles;
    public Node[] nodes;
    public Button[] buttons;
    
    private boolean firstRun = true;
    public boolean step = false;
    private boolean pauseRequested = false;
    private Pane root;
    Scene scene;
    int averageForGraph = 0;
    private Group[] groups;

    /**
     * Constructor to initialize Logic object.
     * @param root The root pane of the scene
     * @param step Flag to enable step-by-step execution
     * @param numNodes Number of nodes in the system
     * @param scene The scene where the visualization is displayed
     */
    public Logic(Pane root ,boolean step,int numNodes,Scene scene) {
        this.root = root;
        this.step = step;
        this.numNodes = numNodes;
        this.scene = scene;
    }

    /**
     * Draws nodes and initializes the algorithm visualization.
     */
    public void DrawAndRun() {
        circles = new Circle[numNodes];
        buttons = new Button[numNodes];
        groups = new Group[numNodes];

        nodes = NodeCollection.initializeNodes(numNodes);
        double horizontalSpacing = 150; // Adjust this value to control the horizontal space between nodes
        double verticalSpacing = 150; // Adjust this value to control the vertical space between levels
        Random rand = new Random();

        
        
         // the part to create and draw tree with nodes represent as circles
        for (int i = 0; i < numNodes; i++) {
            // Node layout
            Circle circle;
            Text text;
            int index = i;
          //  int ran = rand.nextInt(numNodes * 12);
            if (i == 0) {
                // Root node
                circle = new Circle(600, 120, nodeRadius);
                circles[i] = circle;
                circle.setFill(Color.RED);
                text = new Text(nodes[i].getS()+ "/" + nodes[i].isEq() );
                text.setX(600 - nodeRadius / 2); // Adjust text position
                text.setY(100 + nodeRadius / 4); // Adjust text position
            } else {
                // Child nodes
                int parentIndex = nodes[i].getparentIndex();
                double parentX = circles[parentIndex].getCenterX();
                double parentY = circles[parentIndex].getCenterY();
                double angle = Math.PI / 6; // Adjust this angle to control the balance
                double sideFactor = (nodes[i].children % 2 == 0) ? 1 : -1; // Alternating sides
                double offsetX = sideFactor * horizontalSpacing * Math.cos(angle);
                double offsetY = verticalSpacing; // Same Y level for all child nodes

                // Adjust X and Y coordinates to stay within the scene bounds
                double childX = Math.min(Math.max(parentX + offsetX, nodeRadius), scene.getWidth() - nodeRadius);
                double childY = Math.min(Math.max(parentY + offsetY, nodeRadius), scene.getHeight() - nodeRadius);
                childY = Math.min(childY, scene.getHeight() - nodeRadius);
                for (int j = 0; j < i; j++) {
                    if (circles[j] == null) {
                        continue; // Skip null circles
                    }
                    double existingX = circles[j].getCenterX();
                    double existingY = circles[j].getCenterY();
                    double distance = Math.sqrt(Math.pow(existingX - childX, 2) + Math.pow(existingY - childY, 2));
                    if (distance < 2 * nodeRadius) {
                        // Nodes are too close, adjust the position
                        double angle2 = Math.atan2(childY - existingY, childX - existingX);
                        childX = 30 + existingX + 2 * nodeRadius * Math.cos(angle2);
                        childY = existingY + 2 * nodeRadius * Math.sin(angle2);
                    }
                }

                circle = new Circle(childX, childY, nodeRadius);
                circles[i] = circle;
                // Set color based on algorithm
                if (!nodes[i].isEq() || nodes[i].isEq() && nodes[i].sSup() != nodes[i].getS()) {
                    circle.setFill(Color.YELLOW);
                } else {
                    circle.setFill(Color.RED);
                }
                circles[i] = circle;
            }

            // Add circles to the root pane
            root.getChildren().add(circle);

            
            
            if(step) {
            // Create buttons for each node
            Button button = new Button(nodes[i].getS() + "/" + nodes[i].isEq());
           
            button.setFont(Font.font("Arial", FontWeight.BOLD, 6.5)); // Set font size and make text bold

           // button.setStyle("-fx-font-weight: bold;");
            button.setMinSize(nodeRadius, nodeRadius);
            button.setMaxSize(nodeRadius, nodeRadius );

            button.setShape(new Circle(nodeRadius));
            
            button.setScaleY(circle.getScaleY()*2);
            button.setScaleX(circle.getScaleX()*2);
            button.setLayoutX(circle.getCenterX()-20 ); // Adjust the horizontal position of the button
            button.setLayoutY(circle.getCenterY() -20); // Adjust the vertical position of the button
            
            
            
//            button.setScaleY(circle.getScaleY());
//            button.setScaleX(circle.getStrokeWidth() + 2);
//            button.setLayoutX(circle.getCenterX()-14 );
//            button.setLayoutY(circle.getCenterY() - 20);
//            button.setMaxWidth(nodeRadius-18);
//            button.setPrefHeight(nodeRadius);
//            
//            
//            
            
            // Set button action to execute algorithm for the respective node
            button.setOnAction(event -> {
                try {
                    runAlgorithmForNode(index);
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
            });
            // Set button color based on node color
            if (circle.getFill() == Color.YELLOW) {
                button.setStyle("-fx-background-color: yellow; ");
            } else {
                button.setStyle("-fx-background-color: red; ");
            }
            buttons[index] = button;
            root.getChildren().add(button);
        }
            else {
            	 text = new Text( nodes[i].getS()+ "/" + nodes[i].isEq());
            	text.setFont(Font.font(14)); // Set font size
            	text.setFill(Color.BLACK); // Set text color
            	text.setX(circle.getCenterX() - text.getBoundsInLocal().getWidth() / 2);
            	text.setY(circle.getCenterY() + text.getBoundsInLocal().getHeight() / 4);

            	 Group group = new Group(circle, text);
            	 groups[i] = group;
            	 Button button = new Button("");
            	 buttons[index] = button;
                 root.getChildren().add(button);
                 root.getChildren().add(group);
            }
            
            
       }

      
        // Draw lines to represent parent-child relationships
        for (int i = 1; i < numNodes; i++) {
            int parentIndex = nodes[i].getparentIndex();
            Circle parentCircle = circles[parentIndex];
            Circle childCircle = circles[i];
            Line line = new Line();
            line.setStrokeWidth(5);
            line.setStartX(parentCircle.getCenterX());
            line.setStartY(parentCircle.getCenterY() + nodeRadius);
            line.setEndX(childCircle.getCenterX());
            line.setEndY(childCircle.getCenterY() - nodeRadius);
            root.getChildren().add(line);
        }

        // Start the algorithm execution
        if (!step) {
            Thread algorithmThread = new Thread(this::runAlgorithm);
            algorithmThread.setDaemon(true); // Daemon threads will exit when the main program exits
            algorithmThread.start();
        }
    }

    /**
     * Executes the algorithm for a specific node when clicked.
     * @param index The number of the clicked node
     * @throws InterruptedException Thrown if interrupted while sleeping
     */
    private void runAlgorithmForNode(int index) throws InterruptedException {
        if (circles[index].getFill() == Color.RED) {
            Alert notification = new Alert(AlertType.INFORMATION);
            notification.setTitle("Attention");
            notification.setHeaderText(null);
            notification.setContentText("This Node can't have the turn!");
            notification.showAndWait();
            return;
        } else if (firstRun) {
         
            firstRun = false;
            UpdateNodeColorForClick(circles, index, numNodes);
        } else {
            UpdateNodeColorForClick(circles, index, numNodes);
        }
        
        if (allNodesInPerfectState()) {
        	for (int i = 0; i < numNodes; i++) {
	            if(i==index) {
	                circles[index].setFill(Color.GREEN);
	                buttons[index].setStyle("-fx-background-color:green; ");
	            }
	            else {
	                circles[i].setFill(Color.RED);
	                buttons[i].setStyle("-fx-background-color:red; ");
	            }
	        }

	        new Thread(() -> {
	            try {
	                // Adjust the delay duration as needed
	                TimeUnit.SECONDS.sleep(1); // Wait for 2 seconds
	                Platform.runLater(() -> {
	                    // Change circles[index] to red after the delay
	                    circles[index].setFill(Color.RED);
	                    buttons[index].setStyle("-fx-background-color:red; ");
	                    
	                  	circles[0].setFill(Color.YELLOW);
		            	buttons[0].setStyle("-fx-background-color: yellow; ");
	                    
	                    
	                    // Show the notification after updating colors
	                    Alert notification = new Alert(AlertType.INFORMATION);
	                    notification.setTitle("Attention");
	                    notification.setHeaderText(null);
	                    notification.setContentText("Perfect state reached");
	                    notification.showAndWait();
	                });
	            } catch (InterruptedException e) {
	                e.printStackTrace();
	            }
	        }).start();
	   
	    } 
	 
}
    

    /**
     * Updates the node colors after a click event.
     * @param circle The array of circles representing nodes
     * @param clickedIndex The index of the clicked node
     * @param numNodes The total number of nodes
     * @throws InterruptedException Thrown if interrupted while sleeping
     */
    private void UpdateNodeColorForClick(Circle[] circle, int clickedIndex, int numNodes) throws InterruptedException {
        circle[clickedIndex].setFill(Color.GREEN);
        buttons[clickedIndex].setStyle("-fx-background-color: green; ");
        Node node = NodeCollection.getNode(clickedIndex);
        node.executeAlgorithm(4);
        buttons[clickedIndex].setText( nodes[clickedIndex].getS()+ "/" + nodes[clickedIndex].isEq());
        for (int i = 1; i < numNodes; i++) {
            if (i != clickedIndex) {
                if (!nodes[i].isEq() || nodes[i].sSup() != nodes[i].getS()) {
                    circle[i].setFill(Color.YELLOW);
                    buttons[i].setStyle("-fx-background-color: yellow; ");
                    buttons[i].setText( nodes[i].getS()+ "/" + nodes[i].isEq());
                    
                } else {
                    circle[i].setFill(Color.RED);
                    buttons[i].setStyle("-fx-background-color:red; ");
                    buttons[i].setText( nodes[i].getS()+ "/" + nodes[i].isEq());
                }
            }
        }
        //RooTurn(circle, clickedIndex, numNodes);
    }

    /**
     * Main algorithm execution loop for automatic run.
     */
    private void runAlgorithm() {
        try {
            while (true) {
                Random rand = new Random();
                if (firstRun) {
                    firstRun = false;
                }
                for (int i = 0; i < numNodes; i++) {
                    int j = rand.nextInt(numNodes);
                    if(circles[j].getFill()==Color.YELLOW || circles[j].getFill()==Color.GREEN) {
                    	UpdateColor(circles, j, numNodes,groups);	
                    }
                    
                    if (allNodesInPerfectState()) {
                    	  System.out.println("Parfect state reached");
                    	  circles[0].setFill(Color.YELLOW);
                    }
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Pauses the algorithm execution.
     * @param pauseButton The button used to pause/resume execution
     */
    public void pauseAlgorithm(Button pauseButton) {
        if (!pauseRequested) {
            pauseButton.setText("Continue");
            pauseRequested = true;
        } else {
            pauseButton.setText("Pause");
            pauseRequested = false;
        }
    }

    /**
     * Updates node colors based on the current state of the algorithm execution.
     * @param circle The array of circles representing nodes
     * @param i The index of the current node
     * @param numNodes The total number of nodes
     * @throws InterruptedException Thrown if interrupted while sleeping
     */
    private void UpdateColor(Circle[] circle, int i, int numNodes, Group[] groups) throws InterruptedException {
        Node node = NodeCollection.getNode(i);
        if (pauseRequested) {
            while (pauseRequested) {
                Thread.sleep(100); // Sleep to reduce CPU usage
            }
        }
        switch (i) {
            case (0): 
            
            {
            	// first we check if it is the root turn and then execute the algorithm
                boolean rootTurn = RooTurn(circle, i, numNodes);
                if (rootTurn) {
                    for (int j = 1; j < numNodes; j++) {
                        circle[j].setFill(Color.RED);
                        //buttons[j].setStyle("-fx-background-color:red; ");
                        
                    }
                    circle[0].setFill(Color.YELLOW);
                    node.executeAlgorithm(4);
                 
                    
                    Text text = (Text) groups[0].getChildren().get(1);
                    text.setText(  node.getS()+ "/" +node.isEq());
                    //buttons[0].setStyle("-fx-background-color: yellow; ");
                    Thread.sleep(2000);
                    System.out.println("Green is - 0");
                   
                    averageForGraph++;
                    System.out.println("ROOT");
                    circle[0].setFill(Color.GREEN);
                   
                    text.setText(  node.getS()+ "/" +node.isEq());
                 
                    Thread.sleep(2000);
                    for (int j = 1; j < numNodes; j++) {
                        circle[j].setFill(Color.YELLOW);
                       
                        
                    }
                    circle[0].setFill(Color.RED);
                    break;
                }
            }
            default: 
            	// update colors for circles and execute node algorithm
            {
                for (int j = 1; j < numNodes; j++) {
                    if (j != i) {
                    	 // Set the color of the nodes without the turn
                        if (!nodes[j].isEq() || nodes[j].sSup() != nodes[j].getS()) {
                            circle[j].setFill(Color.YELLOW);
                            // buttons[j].setStyle("-fx-background-color: yellow; ");
                           System.out.println("Yellow for  " + j + "  (not root)");
                            circle[0].setFill(Color.RED);
                           // buttons[0].setStyle("-fx-background-color:red; ");
                        } else {
                            circle[j].setFill(Color.RED);
                         //   buttons[j].setStyle("-fx-background-color:red; ");
                            circle[0].setFill(Color.RED);
                          //  buttons[0].setStyle("-fx-background-color:red; ");
                        }
                    } 
                    // Set the color for the node with the turn
                    else if (j == i && (!node.isEq() || node.isEq() && node.sSup() != node.getS())) {
                        circle[j].setFill(Color.GREEN);
                       // buttons[j].setStyle("-fx-background-color:green; ");
                        averageForGraph++;
                        System.out.println("Green is -" + i);
                       
                        
                        
                      //  button.setText(node.isEq() + "/" + node.getS()); 
                        circle[0].setFill(Color.RED);
                      //  buttons[0].setStyle("-fx-background-color:red; ");
                        node.executeAlgorithm(4);
                        Text text = (Text) groups[j].getChildren().get(1);
                        text.setText(node.getS()+ "/" +node.isEq());
                    }
                }
                
                Thread.sleep(1000);
            }
        }
        Thread.sleep(1000);
    }

    /**
     * Checks if it's the root's turn to execute the algorithm.
     * @param circle The array of circles representing nodes
     * @param i The index of the current node
     * @param numNodes The total number of nodes
     * @return True if it's the root's turn, otherwise False
     */
    private boolean RooTurn(Circle[] circle, int i, int numNodes) {
        boolean allRed = true;
        for (int j = 1; j < numNodes; j++) {
            if (!nodes[j].isEq() || nodes[j].sSup() != nodes[j].getS()) {
                allRed = false;
            }
        }
        return allRed;
    }

    /**
     * Checks if all of the system  in the perfect state.
     * @return True if all nodes are not privilege except the root otherwise False
     */
    private boolean allNodesInPerfectState() {
        boolean allEqTrue = true;
        Boolean commonSValue = null;
        
        // eq= true check part
        for (int i = 1; i < numNodes; i++) {
            Node node = NodeCollection.getNode(i);
            if (!node.isEq()) {
                allEqTrue = false;
            }
        }
        
        // S1=S2=S3=....Sn part
        for (int i = 0; i < numNodes; i++) {
            Node node = NodeCollection.getNode(i);
            boolean currentSValue = node.getS();
            if (commonSValue == null) {
                commonSValue = currentSValue;
            } else if (commonSValue != currentSValue) {
                allEqTrue = false;
                break;
            }
        }
        if (firstRun) {
            firstRun = false;
            return false;
        }
        return allEqTrue;
    }
}
