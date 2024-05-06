package com.example.algorithm;

import java.util.Random;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

/**
 * Represents a node in the algorithm.
 */
public class Node {
    private int index; // Index of the node
    private boolean eq; // Represents the 'eq' attribute of the node
    private boolean s; // Represents the 's' attribute of the node
    private int sup; // Represents the 'sup' attribute of the node

    public Circle circle; // JavaFX circle for visualization
    private int numNodes; // Total number of nodes in the system
    public int children; // Number of children of the node
    public int parent; // Index of the parent node

    /**
     * Constructor to initialize the node.
     * @param index is number of the node
     * @param n Total number of nodes in the system
     * @param childrennum Number of children of the node
     * @param sup2 Index of the parent node
     */
    public Node(int index, int n, int childrennum, int sup2) {
        this.index = index;
        Random r = new Random();
        this.eq = r.nextBoolean();
        numNodes = n;
        children = childrennum;
        this.s = r.nextBoolean();
        if (index == 0) {
            this.sup = 0;
        } else {
            this.sup = 1;
        }
        parent = sup2;
        this.circle = new Circle(); // Initialize the JavaFX circle
        this.circle.setRadius(30); // Set circle radius (adjust as needed)
        this.circle.setFill(Color.RED); // Initial color
    }

    /**
     * Determines if the conditions for executing the algorithm are met for the node.
     * if the nodes is a terminal node return true, if the node is the root check if all s values in the tree are the same
     * @param i Index of the node
     * @return True if conditions are met, otherwise False
     */
    public boolean test(int i) {
        if (NodeCollection.getNode(i).getSup() == 1) {
            return true;
        } else {
            for (int j = 1; j < numNodes; j++) {
                if (!NodeCollection.getNode(j).isEq()) {
                    return false;
                }
            }
            Boolean commonSValue = null;
            for (int j = 0; j < numNodes; j++) {
                boolean currentSValue = NodeCollection.getNode(j).getS();
                if (commonSValue == null) {
                    commonSValue = currentSValue; // Initialize commonSValue
                } else if (commonSValue != currentSValue) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Executes the algorithm for the node.
     * @param K Value used in the algorithm
     */
    public void executeAlgorithm(int K) {
        if (!eq && test(index)) {
            eq = true;
            System.out.println("Node " + index + " has eq " + eq);
        }

        if (eq && (s != sSup())) {
            if (sup == 0) {
                s = !s;
                System.out.println("Node " + index + " has s " + s);
            } else {
                s = sSup();
            }
            eq = false;
        }

        System.out.println("eq=" + eq + "  s=" + s);
        System.out.println("node num -  " + index);
    }

    /**
     * Checks the value of the 's' attribute for the root node
     * always return false for the root one of the algorithm laws
     * @return Value of 's' attribute for the parent node
     */
    public boolean sSup() {
        if (this.index == 0) {
            return !NodeCollection.getNode(0).getS(); // always return false for the root one of the algorithm laws
        } else {
            // Retrieve the root node's s value 
            return NodeCollection.getNode(0).getS();
        }
    }

    /**
     * Gets the value of the 's' attribute for the node.
     * @return Value of 's' attribute
     */
    public boolean getS() {
        return s;
    }

    /**
     * Sets the value of the 's' attribute for the node.
     * @param s Value to set
     */
    public void setS(boolean s) {
        this.s = s;
    }

    /**
     * Gets the value of the 'sup' attribute for the node.
     * @return Value of 'sup' attribute
     */
    public int getSup() {
        return sup;
    }
    /**
     * Gets the index of the parent of the node.
     * @return index of parent
     */
	public int getparentIndex() {
		return parent;
	}
    /**
     * Sets the value of the 'sup' attribute for the node.
     * @param sup Value to set
     */
    public void setSup(int sup) {
        this.sup = sup;
    }

    /**
     * Checks the value of the 'eq' attribute for the node.
     * @return True if 'eq' attribute is true, otherwise False
     */
    public boolean isEq() {
        return eq;
    }

    /**
     * Sets the value of the 'eq' attribute for the node.
     * @param eq Value to set
     */
    public void setEq(boolean eq) {
        this.eq = eq;
    }

    /**
     * Gets the index of the node.
     * @return Index of the node
     */
    public int getIndex() {
        return index;
    }

    /**
     * Gets the JavaFX circle representing the node.
     * @return JavaFX circle
     */
    public Circle getCircle() {
        return circle;
    }
}
