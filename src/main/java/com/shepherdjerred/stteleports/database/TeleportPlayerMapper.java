package com.shepherdjerred.stteleports.database;

import com.shepherdjerred.stteleports.objects.TeleportPlayer;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class TeleportPlayerMapper implements ResultSetMapper<TeleportPlayer> {

    @Override
    public TeleportPlayer map(int index, ResultSet r, StatementContext ctx) throws SQLException {
        return new TeleportPlayer(
                UUID.fromString(r.getString("player_uuid")),
                r.getLong("next_teleport"),
                r.getDouble("cooldown_multiplier"),
                r.getDouble("cost_multiplier"),
                r.getDouble("cooldown_multiplier_modifier"),
                r.getDouble("cost_multiplier_modifier")
        );
    }

}
