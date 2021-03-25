package monster.starway.server.data.dto;

import java.util.Objects;

public class EdgeDTO {
    public String fromZone;
    public String toZone;
    public int fee;
    public String channel;

    public EdgeDTO() {
    }

    public EdgeDTO(String fromZone, String toZone, int fee) {
        this.fromZone = fromZone;
        this.toZone = toZone;
        this.fee = fee;
    }

    public EdgeDTO(String fromZone, String toZone, int fee, String channel) {
        this.fromZone = fromZone;
        this.toZone = toZone;
        this.fee = fee;
        this.channel = channel;
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

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EdgeDTO edgeDTO = (EdgeDTO) o;
        return fee == edgeDTO.fee && Objects.equals(fromZone, edgeDTO.fromZone) && Objects.equals(toZone, edgeDTO.toZone) && Objects.equals(channel, edgeDTO.channel);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fromZone, toZone, fee, channel);
    }
}
