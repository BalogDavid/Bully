import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Please provide a Node ID.");
            return;
        }

        int nodeId = Integer.parseInt(args[0]);

        // Initialize list to hold all nodes (simulating distributed environment)
        List<Node> nodes = new ArrayList<>();

        // Create 3 nodes and add them to the list
        Node node1 = new Node(1, nodes);
        Node node2 = new Node(2, nodes);
        Node node3 = new Node(3, nodes);
        nodes.add(node1);
        nodes.add(node2);
        nodes.add(node3);

        // Identify current node
        Node currentNode = null;
        for (Node node : nodes) {
            if (node.getId() == nodeId) {
                currentNode = node;
            }
        }

        if (currentNode != null) {
            currentNode.startElection();
        }
    }
}
