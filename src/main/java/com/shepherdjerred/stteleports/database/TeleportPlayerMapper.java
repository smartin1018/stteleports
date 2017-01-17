package com.shepherdjerred.stteleports.database;

import com.shepherdjerred.stteleports.objects.TeleportPlayer;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class TeleportPlayerMapper implements ResultSetMapper<TeleportPlayer> {

    private TeleportPlayer teleportPlayer;

    @Override
    public TeleportPlayer map(int index, ResultSet r, StatementContext ctx) throws SQLException {

        if (index == 0) {
            teleportPlayer = new TeleportPlayer(
                    UUID.fromString(r.getString("player_uuid")),
                    r.getLong("next_teleport"),
                    r.getDouble("cooldown_multiplier"),
                    r.getDouble("cost_multiplier"),
                    r.getDouble("cooldown_multiplier_modifier"),
                    r.getDouble("cost_multiplier_modifier")
            );
        }

        Location location = new Location(
                Bukkit.getWorld(UUID.fromString(r.getString("player_homes.world"))),
                r.getInt("player_homes.x"),
                r.getInt("player_homes.y"),
                r.getInt("player_homes.z"),
                r.getFloat("player_homes.yaw"),
                r.getFloat("player_homes.pitch")
        );

        teleportPlayer.addHome(r.getString("player_homes.home_name"), location);

        return teleportPlayer;

    }

}
