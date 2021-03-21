package monster.starway.server.dto;

import java.util.Objects;

public class ZoneDTO {
    public String name;

    public ZoneDTO() {
    }

    public ZoneDTO(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ZoneDTO zoneDTO = (ZoneDTO) o;
        return Objects.equals(name, zoneDTO.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
