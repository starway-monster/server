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

    @Column(name = "distance")
    @NonNull
    private Integer distance;
}
