package algorithm;

import java.util.Arrays;
import java.util.Random;

/**
 * Represents a collection of nodes in the algorithm.
 */
public class NodeCollection {
	public static Node[] nodes;

	/**
	 * Initializes the nodes in the collection.
	 * 
	 * @param numNodes The number of nodes to initialize.
	 * @return An array of initialized nodes.
	 */
	public static Node[] initializeNodes(int numNodes) {
		nodes = new Node[numNodes];
		Random rand = new Random();
		for (int i = 0; i < numNodes; i++) {
			// Determine the number of children for each node
			int numChildren = Math.max(rand.nextInt(numNodes - i + 1), 0); // Adjust for remaining nodes

			// Determine the parent index for each node
			int parentIndex;
			if (i == 0) {
				parentIndex = 0; // Root node has no parent
			} else {
				parentIndex = rand.nextInt(i); // Randomly select a parent from previous levels
			}

			// Create the node with the correct parent
			nodes[i] = new Node(i, numNodes, numChildren, parentIndex);
		}
		return nodes;
	}

	/**
	 * Retrieves a node from the collection by its index.
	 * 
	 * @param index The index of the node to retrieve.
	 * @return The node at the specified index.
	 */
	public static Node getNode(int index) {
		return nodes[index];
	}

	/**
	 * Retrieves all nodes in the collection.
	 * 
	 * @return An array containing all nodes in the collection.
	 */
	public static Node[] getAllNodes() {
		return nodes;
	}

	// Method commented out because it is not used in the provided code
//    public static boolean allNodesInPerfectState() {
//        return Arrays.stream(nodes).allMatch(node -> !node.isEq() && node.getS()==false);
//    }
}
