package monster.starway.server.services;

import monster.starway.server.data.entities.*;
import monster.starway.server.data.repositories.ChannelRepository;
import monster.starway.server.data.dto.PathDTO;
import monster.starway.server.data.dto.SearchDTO;
import monster.starway.server.data.dto.EdgeDTO;
import monster.starway.server.data.repositories.EdgeChannelRepository;
import monster.starway.server.data.repositories.ZoneRepository;
import monster.starway.server.exceptions.RouteException;
import monster.starway.server.exceptions.ValidationException;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class WayService {
    private final ChannelRepository channelRepository;
    private final ZoneRepository zoneRepository;
    private final EdgeChannelRepository edgeChannelRepository;

    public WayService(ChannelRepository channelRepository, ZoneRepository zoneRepository, EdgeChannelRepository edgeChannelRepository) {
        this.channelRepository = channelRepository;
        this.zoneRepository = zoneRepository;
        this.edgeChannelRepository = edgeChannelRepository;
    }

    public PathDTO getUnescrowPath(String zoneCurrent, String zoneSource, String trace) {
        PathDTO pathFromCurrent = null;
        PathDTO pathFromSource = null;
        PathDTO pathResult = null;

        if (zoneCurrent != null) {
            pathFromCurrent = getUnescrowCurrentZone(zoneCurrent, trace);
        }
        if (zoneSource != null) {
            pathFromSource = getUnescrowSourceZone(zoneSource, trace);
        }
        if (pathFromCurrent == null && pathFromSource == null)
            throw new RouteException("Pathfinding error");
        if (pathFromCurrent != null && pathFromSource != null && !pathFromCurrent.equals(pathFromSource)) {
            throw new RouteException("Denom trace doesn't correspond to the specified zones");
        }

        if (pathFromCurrent != null)
            pathResult = pathFromCurrent;
        if (pathFromSource != null)
            pathResult = pathFromSource;
        if (pathResult == null)
            throw new RouteException("Path not found");
        return pathResult;
    }

    private PathDTO getUnescrowCurrentZone(String zone, String trace) {
        List<String> channels = getChannelsNamesByTrace(trace);
        return unescrowPathSearchFromCurrentZone(zone, channels);
    }

    private PathDTO getUnescrowSourceZone(String zone, String trace) {
        List<String> channels = getChannelsNamesByTrace(trace);
        return null; // todo: need to implement unescrowPathSearchFromSourceZone
    }

    private PathDTO unescrowPathSearchFromCurrentZone(String zone, List<String> channels) {
        List<EdgeDTO> edges = new ArrayList<>();
        for (String channel : channels) {
            List<Zone> zones = zoneRepository.getZoneByChannelAndCounerpartyZone(zone, channel);
            if (zones == null || zones.size() > 1) {
                throw new RouteException("Pathfinding error");
            }
            if (zones.size() == 0)
                return null;
            String counterpartyZone = zones.get(0).getChainId().toLowerCase();
            edges.add(new EdgeDTO(
                    zone.toLowerCase(),
                    counterpartyZone,
                    1, //fee stub
                    Collections.singletonList(channel)
            ));
            zone = counterpartyZone;
        }
        return new PathDTO(edges, edges.size(), edges.size(), 0); //fee stub
    }

    private List<String> getChannelsNamesByTrace(String trace) {
        List<String> channels = Arrays.asList(trace.toLowerCase().replace("transfer", "").replace("//", "/").split("/"));
        String[] transfer = trace.split("/");
        if (channels.size() < 3 || transfer.length < 3 || !transfer[transfer.length - 3].equalsIgnoreCase("transfer"))
            throw new ValidationException("Wrong trace");
        channels = channels.subList(1, channels.size() - 1);
        return channels;
    }


    public SearchDTO getFilteredWays(String from, String to, List<String> excludedZones) {
        List<Channel> channelsWithDuplicates = channelRepository.getChannelsWithDuplicates();
        excludeZones(channelsWithDuplicates, excludedZones);
        Set<Node> nodes = transformChannelsToNodes(channelsWithDuplicates);
        Graph graph = createGraph(nodes);
        initiateSourceZone(from, nodes, graph);
        Node nodeTo = getNodeByName(nodes, to);
        List<Node> path = getPath(nodeTo);

        //if path.size = 1, then check if the zones are directly connected, if not, then they have no path
        validatePath(path, nodeTo);

        SearchDTO searchDTO = transformToSearchDTO(nodeTo, path);
        fillChannelsInSearch(searchDTO);

        return searchDTO;
    }

    private void fillChannelsInSearch(SearchDTO search) {
        fillChannelsInPath(search.getPathByTransfers());
        fillChannelsInPath(search.getPathByFee());
    }

    private void fillChannelsInPath(PathDTO path) {
        fillChannelsInEdge(path.getGraph());
        int combinations = 0;
        for (EdgeDTO edge : path.getGraph()) {
            if (edge.getChannels() != null)
                combinations += combinations + edge.getChannels().size();
        }
        path.setChannelCombinations(combinations);
    }

    private void fillChannelsInEdge(List<EdgeDTO> graph) {
        for (EdgeDTO edge : graph) {
            List<EdgeChannel> channelsByZones = edgeChannelRepository.getChannelsByZones(edge.fromZone, edge.toZone);
            List channels = new LinkedList<String>();
            for (EdgeChannel edgeChannel : channelsByZones) {
                channels.add(edgeChannel.getChannelId());
            }
            edge.setChannels(channels);
        }
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

    private void validatePath(List<Node> path, Node nodeTo) {
        if (path.size() > 1)
            return;
        if (path.size() == 0)
            throw new RouteException("Calculation error");
        for (Node node : path.get(0).getAdjacentNodes().keySet()) {
            if (node.equals(nodeTo)) {
                return;
            }
        }
        throw new RouteException("Unreachable route");
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
            EdgeDTO edgeDTO = new EdgeDTO(fromZone.getName(), node.getName(), stepFee, null);
            graph.add(edgeDTO);
            fromZone = node;
        }
        graph = graph.subList(1, graph.size());

        PathDTO pathDTO = new PathDTO(graph, nodes.size() - 1, fee, 0);

        return pathDTO;
    }
}
