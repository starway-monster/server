package monster.starway.server.data.entities;

import org.springframework.lang.NonNull;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;

@Entity
@IdClass(ChannelKey.class)
public class Channel {
    @Id
    @Column(name = "zone_a")
    @NonNull
    private String sourceZone;

    @Id
    @Column(name = "zone_b")
    @NonNull
    private String targetZone;

    @Id
    @Column(name = "distance")
    @NonNull
    private Integer distance;

    @NonNull
    public String getSourceZone() {
        return sourceZone;
    }

    public void setSourceZone(@NonNull String sourceZone) {
        this.sourceZone = sourceZone;
    }

    @NonNull
    public String getTargetZone() {
        return targetZone;
    }

    public void setTargetZone(@NonNull String targetZone) {
        this.targetZone = targetZone;
    }

    @NonNull
    public Integer getDistance() {
        return distance;
    }

    public void setDistance(@NonNull Integer distance) {
        this.distance = distance;
    }
}
