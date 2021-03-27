package monster.starway.server.data.entities;

import java.io.Serializable;
import java.util.Objects;

public class EdgeChannelKey implements Serializable {
    private String channelId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EdgeChannelKey that = (EdgeChannelKey) o;
        return Objects.equals(channelId, that.channelId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(channelId);
    }
}