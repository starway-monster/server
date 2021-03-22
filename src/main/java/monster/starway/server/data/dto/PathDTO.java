package monster.starway.server.data.dto;

import java.util.List;
import java.util.Objects;

public class PathDTO {
    public List<EdgeDTO> graph;
    public int transfers;
    public int fee;

    public PathDTO() {
    }

    public PathDTO(List<EdgeDTO> graph, int transfers, int fee) {
        this.graph = graph;
        this.transfers = transfers;
        this.fee = fee;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PathDTO pathDTO = (PathDTO) o;
        return transfers == pathDTO.transfers &&
                fee == pathDTO.fee &&
                Objects.equals(graph, pathDTO.graph);
    }

    @Override
    public int hashCode() {
        return Objects.hash(graph, transfers, fee);
    }
}
