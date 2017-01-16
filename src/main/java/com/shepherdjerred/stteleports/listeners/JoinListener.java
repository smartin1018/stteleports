package com.shepherdjerred.stteleports.listeners;

import com.shepherdjerred.riotbase.listeners.AbstractListener;
import com.shepherdjerred.stteleports.database.TeleportPlayerDatabaseActions;
import com.shepherdjerred.stteleports.objects.TeleportPlayer;
import com.shepherdjerred.stteleports.objects.trackers.TeleportPlayerTracker;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;

public class JoinListener extends AbstractListener {

    private final TeleportPlayerTracker teleportPlayerTracker;
    private final TeleportPlayerDatabaseActions teleportPlayerQueries;

    public JoinListener(TeleportPlayerTracker teleportPlayerTracker, TeleportPlayerDatabaseActions teleportPlayerQueries) {
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
            Bukkit.broadcastMessage("New player!");
        } else {
            Bukkit.broadcastMessage("Returning player!");
        }

        teleportPlayerTracker.add(teleportPlayer);

        System.out.println(teleportPlayer.toString());

    }

}
