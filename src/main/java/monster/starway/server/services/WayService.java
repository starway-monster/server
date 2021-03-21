package monster.starway.server.services;

import monster.starway.server.data.entities.Dijkstra;
import monster.starway.server.data.entities.Graph;
import monster.starway.server.data.entities.Node;
import monster.starway.server.dto.PathDTO;
import monster.starway.server.dto.SearchDTO;
import monster.starway.server.dto.ZoneDTO;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class WayService {
    public SearchDTO getFilteredWays(String from, String to, List<String> excludedZones) {
        return getWay();
    }

    private SearchDTO getWay() {
        Node nodeA = new Node("A");
        Node nodeB = new Node("B");
        Node nodeC = new Node("C");

        nodeA.addDestination(nodeB, 10);
        nodeB.addDestination(nodeA, 10);
        nodeB.addDestination(nodeC, 20);
        nodeC.addDestination(nodeB, 20);

        List<Node> nodes = new ArrayList<>(){{
            add(nodeA);
            add(nodeB);
            add(nodeC);
        }};
        Graph graph = getZonesGraph(nodes);

        Dijkstra.calculateShortestPathFromSource(graph, nodeA);

        List<Node> pathByTransfers = nodeB.getShortestPath();
        pathByTransfers.add(nodeB);
        List<Node> pathByFee = nodeC.getShortestPath();
        pathByFee.add(nodeC);

        PathDTO pathDTOByTransfers = transformToPathDTO(pathByTransfers, nodeB.getDistance());
        PathDTO pathDTOByFee = transformToPathDTO(pathByFee, nodeC.getDistance());

        return new SearchDTO(pathDTOByTransfers, pathDTOByFee);
    }

    private Graph getZonesGraph(List<Node> nodes) {
        Graph graph = new Graph();
        for (Node node : nodes) {
            graph.addNode(node);
        }
        return graph;
    }

    public PathDTO transformToPathDTO(List<Node> nodes, int fee) {
        List<ZoneDTO> graph = new ArrayList<>();
        for (Node node : nodes)
            graph.add(new ZoneDTO(node.getName()));
        PathDTO pathDTO = new PathDTO(graph, nodes.size(), fee);

        return pathDTO;
    }
}
