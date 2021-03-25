package monster.starway.server.data.entities;

import java.io.Serializable;
import java.util.Objects;

public class ZoneKey implements Serializable {
    private String chainId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ZoneKey zoneKey = (ZoneKey) o;
        return Objects.equals(chainId, zoneKey.chainId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(chainId);
    }
}
