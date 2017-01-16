package com.shepherdjerred.stteleports.database;

import com.shepherdjerred.stteleports.objects.TeleportPlayer;
import com.zaxxer.hikari.HikariDataSource;
import org.skife.jdbi.v2.DBI;
import org.skife.jdbi.v2.Handle;
import org.skife.jdbi.v2.Query;
import org.skife.jdbi.v2.util.StringMapper;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public class TeleportPlayerQueries {

    private final HikariDataSource dataSource;

    public TeleportPlayerQueries(HikariDataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void createPlayer(TeleportPlayer player) {

        DBI dbi = new DBI(dataSource);
        Handle h = dbi.open();
        String sql = "INSERT INTO towns VALUES (?,?,?,?,?)";

        h.createStatement(sql)
                .bind(0, player.getUuid().toString())
                .bind(1, player.getNextAvaliableTeleport())
                .bind(2, player.getCooldownMultiplier())
                .bind(3, player.getCostMultiplier())
                .bind(4, player.getCooldownMultiplierModifier())
                .bind(5, player.getCostMultiplierModifier())
                .execute();

        h.close();

    }

    public TeleportPlayer loadPlayer(UUID player) {

        DBI dbi = new DBI(dataSource);
        Handle h = dbi.open();
        String sql = "SELECT * FROM players WHERE player_uuid = ?";

        Query<Map<String, Object>> q = h.createQuery(sql)
                .bind(0, player.toString());

        Query<String> q2 = q.map(StringMapper.FIRST);

        List<String> rs = q2.list();

        rs.forEach(s -> System.out.println(s));

        return null;

    }

}
