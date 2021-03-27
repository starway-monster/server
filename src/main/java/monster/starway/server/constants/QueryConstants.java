package monster.starway.server.constants;

public interface QueryConstants {
    String GET_CHANNELS_BY_ZONES = "" +
            "select\n" +
            "    channels.channel_id\n" +
            "from\n" +
            "    ibc_channels as channels\n" +
            "inner join ibc_connections as connections\n" +
            "    on channels.zone = connections.zone and channels.connection_id = connections.connection_id\n" +
            "inner join ibc_clients as clients\n" +
            "     on connections.zone = clients.zone and connections.client_id = clients.client_id\n" +
            "where\n" +
            "    clients.zone = :source_zone_name and\n" +
            "    clients.chain_id = :target_zone_name and\n" +
            "    channels.is_opened = true;";

    String GET_ZONE_BY_CHANNEL_AND_COUNTERPARTY_ZONE = "" +
            "select\n" +
            "    clients.chain_id as chain_id\n" +
            "from\n" +
            "    ibc_channels as channels\n" +
            "inner join ibc_connections as connections\n" +
            "    on channels.zone = connections.zone and channels.connection_id = connections.connection_id\n" +
            "inner join ibc_clients as clients\n" +
            "    on connections.zone = clients.zone and connections.client_id = clients.client_id\n" +
            "where\n" +
            "    channels.zone = :source_zone_name and \n" +
            "    channels.channel_id = :channel_name ;";

    String GET_CHANNELS_WITH_DUPLICATED = "" +
            "with channels_w_duplicates as (\n" +
            "select distinct\n" +
            "    channels.zone zone_a,\n" +
            "    clients.chain_id zone_b\n" +
            "from\n" +
            "    ibc_channels as channels\n" +
            "inner join ibc_connections as connections\n" +
            "    on channels.connection_id = connections.connection_id and channels.zone = connections.zone\n" +
            "inner join ibc_clients as clients\n" +
            "    on channels.zone = clients.zone and connections.client_id = clients.client_id\n" +
            "where is_opened = true\n" +
            "), joined_channels as (\n" +
            "select\n" +
            "    zone_a,\n" +
            "    zone_b\n" +
            "from\n" +
            "    channels_w_duplicates\n" +
            "union all\n" +
            "select\n" +
            "    zone_b as zone_a,\n" +
            "    zone_a as zone_b\n" +
            "from\n" +
            "    channels_w_duplicates\n" +
            ")\n" +
            "\n" +
            "select distinct\n" +
            "    zone_a,\n" +
            "    zone_b,\n" +
            "    1 as distance\n" +
            "from\n" +
            "    joined_channels\n" +
            "order by\n" +
            "    zone_a;";
}
