package monster.starway.server.data.dto;

import java.util.List;
import java.util.Objects;

public class EdgeDTO {
    public String fromZone;
    public String toZone;
    public int fee;
    public List<String> channels;

    public EdgeDTO() {
    }

    public EdgeDTO(String fromZone, String toZone, int fee, List<String> channels) {
        this.fromZone = fromZone;
        this.toZone = toZone;
        this.fee = fee;
        this.channels = channels;
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

    public List<String> getChannels() {
        return channels;
    }

    public void setChannels(List<String> channels) {
        this.channels = channels;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EdgeDTO edgeDTO = (EdgeDTO) o;
        return fee == edgeDTO.fee && Objects.equals(fromZone, edgeDTO.fromZone) && Objects.equals(toZone, edgeDTO.toZone) && Objects.equals(channels, edgeDTO.channels);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fromZone, toZone, fee, channels);
    }
}
