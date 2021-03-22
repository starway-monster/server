package monster.starway.server.data.entities;

import java.io.Serializable;
import java.util.Objects;

public class ChannelKey implements Serializable {
    private String sourceZone;
    private String targetZone;
    private Integer distance;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChannelKey that = (ChannelKey) o;
        return Objects.equals(sourceZone, that.sourceZone) && Objects.equals(targetZone, that.targetZone) && Objects.equals(distance, that.distance);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sourceZone, targetZone, distance);
    }
}
