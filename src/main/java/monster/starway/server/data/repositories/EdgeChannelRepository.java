package monster.starway.server.data.repositories;

import monster.starway.server.constants.QueryConstants;
import monster.starway.server.data.entities.EdgeChannel;
import monster.starway.server.data.entities.EdgeChannelKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EdgeChannelRepository extends JpaRepository<EdgeChannel, EdgeChannelKey> {
    @Query(value = QueryConstants.GET_CHANNELS_BY_ZONES, nativeQuery = true)
    List<EdgeChannel> getChannelsByZones(@Param(value = "source_zone_name") String sourceZoneName,
                                                  @Param(value = "target_zone_name") String targetZoneName);
}
