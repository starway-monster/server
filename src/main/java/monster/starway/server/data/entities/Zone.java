package monster.starway.server.data.entities;

import org.springframework.lang.NonNull;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;

@Entity
@IdClass(ZoneKey.class)
public class Zone {
    @Id
    @Column(name = "chain_id")
    @NonNull
    private String chainId;

    @NonNull
    public String getChainId() {
        return chainId;
    }

    public void setChainId(@NonNull String chainId) {
        this.chainId = chainId;
    }
}
