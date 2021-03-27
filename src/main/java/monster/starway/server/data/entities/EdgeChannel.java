package monster.starway.server.data.entities;

import org.springframework.lang.NonNull;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;

@Entity
@IdClass(EdgeChannelKey.class)
public class EdgeChannel {
    @Id
    @Column(name = "channel_id")
    @NonNull
    private String channelId;

    @NonNull
    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(@NonNull String channelId) {
        this.channelId = channelId;
    }
}
