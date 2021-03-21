package monster.starway.server.services;

import monster.starway.server.data.entities.Dijkstra;
import monster.starway.server.data.entities.Graph;
import monster.starway.server.data.entities.Node;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WayService {
    public String getFilteredWays(String from, String to, List<String> excludedZones) {
        String result = getEchoMessage(from, to, excludedZones) + "\n" + getWay();
        return result;
    }

    private String getWay() {
        Node nodeA = new Node("A");
        Node nodeB = new Node("B");
        Node nodeC = new Node("C");

        nodeA.addDestination(nodeB, 10);
        nodeB.addDestination(nodeA, 10);
        nodeB.addDestination(nodeC, 20);
        nodeC.addDestination(nodeB, 20);

        Graph graph = new Graph();
        graph.addNode(nodeA);
        graph.addNode(nodeB);
        graph.addNode(nodeC);

        Dijkstra.calculateShortestPathFromSource(graph, nodeA);

        String aToB = "";
        for (Node node : nodeB.getShortestPath()) {
            aToB += node.getName() + "--->";
        }
        aToB.substring(0, aToB.length() - 5);
        aToB += "B: " + nodeB.getDistance();

        String aToC = "";
        for (Node node : nodeC.getShortestPath()) {
            aToC += node.getName() + "--->";
        }
        aToC.substring(0, aToC.length() - 5);
        aToC += "C: " + nodeC.getDistance();

        return aToB + "\n" + aToC;
    }

    private String getEchoMessage(String from, String to, List<String> excludedZones) {
        String excluded = "";
        if (excludedZones != null && excludedZones.size() > 0) {
            excluded = ", excluded:";
            for (String excludedZone: excludedZones) {
                excluded += excludedZone + ",";
            }
            excluded = excluded.substring(0, excluded.length()-2);
        }
        return String.format("Hello from: " + from + ", to:" + to + excluded);
    }
}
