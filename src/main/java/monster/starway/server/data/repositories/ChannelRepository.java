package monster.starway.server.data.repositories;

import monster.starway.server.constants.QueryConstants;
import monster.starway.server.data.entities.Channel;
import monster.starway.server.data.entities.ChannelKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChannelRepository extends JpaRepository<Channel, ChannelKey> {
    @Query(value = QueryConstants.GET_CHANNELS_WITH_DUPLICATED, nativeQuery = true)
    List<Channel> getChannelsWithDuplicates();
}
