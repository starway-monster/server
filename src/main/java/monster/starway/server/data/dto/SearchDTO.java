package monster.starway.server.data.dto;

import java.util.Objects;

public class SearchDTO {
    public PathDTO pathByTransfers;
    public PathDTO pathByFee;

    public SearchDTO() {
    }

    public SearchDTO(PathDTO pathByTransfers, PathDTO pathByFee) {
        this.pathByTransfers = pathByTransfers;
        this.pathByFee = pathByFee;
    }

    public PathDTO getPathByTransfers() {
        return pathByTransfers;
    }

    public void setPathByTransfers(PathDTO pathByTransfers) {
        this.pathByTransfers = pathByTransfers;
    }

    public PathDTO getPathByFee() {
        return pathByFee;
    }

    public void setPathByFee(PathDTO pathByFee) {
        this.pathByFee = pathByFee;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SearchDTO searchDTO = (SearchDTO) o;
        return Objects.equals(pathByTransfers, searchDTO.pathByTransfers) &&
                Objects.equals(pathByFee, searchDTO.pathByFee);
    }

    @Override
    public int hashCode() {
        return Objects.hash(pathByTransfers, pathByFee);
    }
}
