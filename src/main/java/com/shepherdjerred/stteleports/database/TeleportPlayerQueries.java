package com.shepherdjerred.stteleports.database;

import com.shepherdjerred.stteleports.objects.TeleportPlayer;
import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;

@RegisterMapper(TeleportPlayerMapper.class)
public interface TeleportPlayerQueries {

    @SqlUpdate("INSERT INTO players VALUES (:uuid, :next_teleport, :cooldown_multiplier, :cost_multiplier, :cooldown_multiplier_modifier, :cost_multiplier_modifier)")
    void insert(@Bind("uuid") String uuid, @Bind("next_teleport") long nextTeleport, @Bind("cooldown_multiplier") double cooldownMultiplier, @Bind("cost_multiplier") double costMultiplier, @Bind("cooldown_multiplier_modifier") double cooldownMultiplierModifier, @Bind("cost_multiplier_modifier") double costMultiplierModifier);

    @SqlQuery("SELECT * FROM players WHERE player_uuid = :uuid")
    TeleportPlayer findByUuid(@Bind("uuid") String uuid);

    void close();

}
