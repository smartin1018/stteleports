package com.shepherdjerred.stteleports.database;

import com.shepherdjerred.stteleports.objects.TeleportPlayer;
import com.zaxxer.hikari.HikariDataSource;
import org.skife.jdbi.v2.DBI;

import java.util.UUID;

public class TeleportPlayerDatabaseActions {

    private final HikariDataSource dataSource;

    public TeleportPlayerDatabaseActions(HikariDataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void createPlayer(TeleportPlayer player) {

        DBI dbi = new DBI(dataSource);

        TeleportPlayerQueries operations = dbi.open(TeleportPlayerQueries.class);

        operations.insert(
                player.getUuid().toString(),
                player.getNextAvaliableTeleport(),
                player.getCooldownMultiplier(),
                player.getCostMultiplier(),
                player.getCooldownMultiplierModifier(),
                player.getCostMultiplierModifier()
        );

        operations.close();

    }

    public TeleportPlayer loadPlayer(UUID player) {

        DBI dbi = new DBI(dataSource);

        TeleportPlayerQueries operations = dbi.open(TeleportPlayerQueries.class);

        TeleportPlayer teleportPlayer = operations.findByUuid(player.toString());

        return teleportPlayer;

    }

}
