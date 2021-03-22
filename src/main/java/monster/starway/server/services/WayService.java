package monster.starway.server.services;

import monster.starway.server.data.entities.Channel;
import monster.starway.server.data.entities.Dijkstra;
import monster.starway.server.data.entities.Graph;
import monster.starway.server.data.entities.Node;
import monster.starway.server.data.repository.ChannelRepository;
import monster.starway.server.dto.PathDTO;
import monster.starway.server.dto.SearchDTO;
import monster.starway.server.dto.ZoneDTO;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class WayService {
    private ChannelRepository channelRepository;

    public WayService(ChannelRepository channelRepository) {
        this.channelRepository = channelRepository;
    }

    public SearchDTO getFilteredWays(String from, String to, List<String> excludedZones) {
        List<Channel> channelsWithDuplicates = channelRepository.getChannelsWithDuplicates();
        Set<Node> nodes = transformChannelsToNodes(channelsWithDuplicates);
        Graph graph = createGraph(nodes);
        initiateSourceZone(from, nodes, graph);

        Node nodeTo = getNodeByName(nodes, to);
        List<Node> path = nodeTo.getShortestPath();
        path.add(nodeTo);

        PathDTO pathDTO = transformToPathDTO(path, nodeTo.getDistance());

        SearchDTO searchDTO = new SearchDTO(pathDTO, pathDTO);

        return searchDTO;
    }



    private Set<Node> transformChannelsToNodes(List<Channel> channels) {
        Map<String, Node> nodes = getNodesFromChannels(channels);
        initiateNodesDirections(nodes, channels);
        return new HashSet<>(nodes.values());
    }

    private Graph createGraph(Set<Node> nodes) {
        Graph graph = new Graph();
        graph.setNodes(nodes);
        return graph;
    }

    private void initiateSourceZone(String from, Set<Node> nodes, Graph graph) {
        Node node = getNodeByName(nodes, from);
        Dijkstra.calculateShortestPathFromSource(graph, node);
    }

    private Node getNodeByName(Set<Node> nodes, String name) {
        for (Node node : nodes) {
            if (node.getName().equalsIgnoreCase(name)) {
                return node;
            }
        }
        return null;
    }

    private Map<String, Node> getNodesFromChannels(List<Channel> channels) {
        Map<String, Node> nodes = new HashMap();
        for (Channel channel : channels) {
            String sourceZone = channel.getSourceZone();
            String targetZone = channel.getTargetZone();

            Node node = nodes.get(sourceZone);
            if (node == null)
                nodes.put(sourceZone, new Node(sourceZone));

            node = nodes.get(targetZone);
            if (node == null)
                nodes.put(targetZone, new Node(targetZone));
        }
        return nodes;
    }

    private void initiateNodesDirections(Map<String, Node> nodes, List<Channel> channels) {
        for (Channel channel : channels) {
            String sourceZoneName = channel.getSourceZone();
            String targetZoneName = channel.getTargetZone();
            Node sourceZone = nodes.get(sourceZoneName);
            Node targetNode = nodes.get(targetZoneName);
            if (sourceZone != null && targetNode != null) {
                sourceZone.addDestination(targetNode, channel.getDistance());
                targetNode.addDestination(sourceZone, channel.getDistance());
                continue;
            }
//            todo: throw exception
        }
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
