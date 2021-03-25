package monster.starway.server.data.repositories;

import monster.starway.server.constants.QueryConstants;
import monster.starway.server.data.entities.Zone;
import monster.starway.server.data.entities.ZoneKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ZoneRepository extends JpaRepository<Zone, ZoneKey> {
    @Query(value = QueryConstants.GET_ZONE_BY_CHANNEL_AND_COUNTERPARTY_ZONE, nativeQuery = true)
    List<Zone> getZoneByChannelAndCounerpartyZone(@Param(value = "source_zone_name") String sourceZoneName,
                                                     @Param(value = "channel_name") String channelName);
}
