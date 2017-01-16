package com.shepherdjerred.stteleports.listeners;

import com.shepherdjerred.riotbase.listeners.AbstractListener;
import com.shepherdjerred.stteleports.database.TeleportPlayerQueries;
import com.shepherdjerred.stteleports.objects.TeleportPlayer;
import com.shepherdjerred.stteleports.objects.trackers.TeleportPlayerTracker;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;

public class JoinListener extends AbstractListener {

    private final TeleportPlayerTracker teleportPlayerTracker;
    private final TeleportPlayerQueries teleportPlayerQueries;

    public JoinListener(TeleportPlayerTracker teleportPlayerTracker, TeleportPlayerQueries teleportPlayerQueries) {
        this.teleportPlayerTracker = teleportPlayerTracker;
        this.teleportPlayerQueries = teleportPlayerQueries;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        // TODO Do this async
        TeleportPlayer teleportPlayer = teleportPlayerQueries.loadPlayer(event.getPlayer().getUniqueId());

        if (teleportPlayer == null) {
            teleportPlayer = new TeleportPlayer(event.getPlayer().getUniqueId());
            teleportPlayerQueries.createPlayer(teleportPlayer);
        }

        teleportPlayerTracker.add(teleportPlayer);

    }

}
