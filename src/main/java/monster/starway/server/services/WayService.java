package monster.starway.server.services;

import monster.starway.server.data.entities.Channel;
import monster.starway.server.data.entities.Dijkstra;
import monster.starway.server.data.entities.Graph;
import monster.starway.server.data.entities.Node;
import monster.starway.server.data.repositories.ChannelRepository;
import monster.starway.server.data.dto.PathDTO;
import monster.starway.server.data.dto.SearchDTO;
import monster.starway.server.data.dto.EdgeDTO;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class WayService {
    private final ChannelRepository channelRepository;

    public WayService(ChannelRepository channelRepository) {
        this.channelRepository = channelRepository;
    }

    public SearchDTO getFilteredWays(String from, String to, List<String> excludedZones) {
        List<Channel> channelsWithDuplicates = channelRepository.getChannelsWithDuplicates();
        excludeZones(channelsWithDuplicates, excludedZones);
        Set<Node> nodes = transformChannelsToNodes(channelsWithDuplicates);
        Graph graph = createGraph(nodes);
        initiateSourceZone(from, nodes, graph);
        Node nodeTo = getNodeByName(nodes, to);
        List<Node> path = getPath(nodeTo);

        SearchDTO searchDTO = transformToSearchDTO(nodeTo, path);

        return searchDTO;
    }

    private void excludeZones(List<Channel> channels, List<String> excludedZones) {
        if (excludedZones != null) {
            List<Channel> excludedChannels = new ArrayList<>();
            for (Channel channel : channels) {
                for (String zoneName : excludedZones) {
                    if (channel.getSourceZone().equalsIgnoreCase(zoneName) ||
                            channel.getTargetZone().equalsIgnoreCase(zoneName))
                        excludedChannels.add(channel);
                }
            }
            channels.removeAll(excludedChannels);
        }
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

    private List<Node> getPath(Node nodeTo) {
        List<Node> path = nodeTo.getShortestPath();
        path.add(nodeTo);
        return path;
    }

    private SearchDTO transformToSearchDTO(Node nodeTo, List<Node> path) {
        PathDTO pathDTO = transformToPathDTO(path, nodeTo.getDistance());
        SearchDTO searchDTO = new SearchDTO(pathDTO, pathDTO);
        return searchDTO;
    }

    public PathDTO transformToPathDTO(List<Node> nodes, int fee) {
        List<EdgeDTO> graph = new ArrayList<>();
        Node fromZone = nodes.get(0);
        for (Node node : nodes) {
            Integer edgeFee = fromZone.getAdjacentNodes().get(node);
            int stepFee = edgeFee != null ? edgeFee.intValue() : 0;
            graph.add(new EdgeDTO(fromZone.getName(), node.getName(), stepFee));
            fromZone = node;
        }
        graph = graph.subList(1, graph.size());

        PathDTO pathDTO = new PathDTO(graph, nodes.size(), fee);

        return pathDTO;
    }
}
