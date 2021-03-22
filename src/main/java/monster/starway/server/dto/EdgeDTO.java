package monster.starway.server.dto;

import java.util.Objects;

public class EdgeDTO {
    public String fromZone;
    public String toZone;
    public int fee;

    public EdgeDTO() {
    }

    public EdgeDTO(String fromZone, String toZone, int fee) {
        this.fromZone = fromZone;
        this.toZone = toZone;
        this.fee = fee;
    }

    public String getFromZone() {
        return fromZone;
    }

    public void setFromZone(String fromZone) {
        this.fromZone = fromZone;
    }

    public String getToZone() {
        return toZone;
    }

    public void setToZone(String toZone) {
        this.toZone = toZone;
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
        EdgeDTO edgeDTO = (EdgeDTO) o;
        return Objects.equals(fromZone, edgeDTO.fromZone) && Objects.equals(toZone, edgeDTO.toZone) && Objects.equals(fee, edgeDTO.fee);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fromZone, toZone, fee);
    }
}
