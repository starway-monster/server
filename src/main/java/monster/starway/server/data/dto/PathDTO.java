package monster.starway.server.data.dto;

import java.util.List;
import java.util.Objects;

public class PathDTO {
    public List<EdgeDTO> graph;
    public int transfers;
    public int fee;
    private int channelCombinations;

    public PathDTO() {
    }

    public PathDTO(List<EdgeDTO> graph, int transfers, int fee, int channelCombinations) {
        this.graph = graph;
        this.transfers = transfers;
        this.fee = fee;
        this.channelCombinations = channelCombinations;
    }

    public List<EdgeDTO> getGraph() {
        return graph;
    }

    public void setGraph(List<EdgeDTO> graph) {
        this.graph = graph;
    }

    public int getTransfers() {
        return transfers;
    }

    public void setTransfers(int transfers) {
        this.transfers = transfers;
    }

    public int getFee() {
        return fee;
    }

    public void setFee(int fee) {
        this.fee = fee;
    }

    public int getChannelCombinations() {
        return channelCombinations;
    }

    public void setChannelCombinations(int channelCombinations) {
        this.channelCombinations = channelCombinations;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PathDTO pathDTO = (PathDTO) o;
        return transfers == pathDTO.transfers && fee == pathDTO.fee && channelCombinations == pathDTO.channelCombinations && Objects.equals(graph, pathDTO.graph);
    }

    @Override
    public int hashCode() {
        return Objects.hash(graph, transfers, fee, channelCombinations);
    }
}
