import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class Node {
    private int id;
    private boolean isCoordinator;
    private List<Node> allNodes;

    public Node(int id, List<Node> allNodes) {
        this.id = id;
        this.isCoordinator = false;
        this.allNodes = allNodes;
    }

    public int getId() {
        return id;
    }

    public boolean isCoordinator() {
        return isCoordinator;
    }

    public void setCoordinator(boolean isCoordinator) {
        this.isCoordinator = isCoordinator;
        logEvent("Node " + id + " is now the coordinator.");
    }

    // Method to start an election
    public void startElection() {
        logEvent("Node " + id + " starts an election.");
        boolean foundHigherId = false;

        // Send election messages to nodes with higher IDs
        for (Node node : allNodes) {
            if (node.getId() > this.id) {
                foundHigherId = true;
                logEvent("Node " + id + " sends election message to Node " + node.getId());
                node.receiveElectionMessage(this);
            }
        }

        // If no higher ID nodes are found, this node becomes the coordinator
        if (!foundHigherId) {
            setCoordinator(true);
            announceCoordinator();
        }
    }

    // Method to receive an election message
    public void receiveElectionMessage(Node sender) {
        logEvent("Node " + id + " received election message from Node " + sender.getId());

        // Respond by starting its own election if not already a coordinator
        if (!isCoordinator) {
            startElection();
        }
    }

    // Announce this node as the new coordinator
    private void announceCoordinator() {
        for (Node node : allNodes) {
            if (node.getId() != this.id) {
                node.receiveCoordinatorMessage(this);
            }
        }
    }

    // Receive coordinator announcement
    public void receiveCoordinatorMessage(Node coordinator) {
        this.isCoordinator = false; // Reset coordinator status if receiving a message
        logEvent("Node " + id + " acknowledges Node " + coordinator.getId() + " as the new coordinator.");
    }

    // Log event method
    private void logEvent(String message) {
        System.out.println(message); // Print to console
        try (FileWriter fw = new FileWriter("logs/event.log", true)) {
            fw.write(message + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
