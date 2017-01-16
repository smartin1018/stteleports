package com.shepherdjerred.stteleports.listeners;

import com.shepherdjerred.riotbase.listeners.AbstractListener;
import com.shepherdjerred.stteleports.objects.trackers.TeleportPlayerTracker;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;

public class JoinListener extends AbstractListener {

    private final TeleportPlayerTracker teleportPlayerTracker;

    public JoinListener(TeleportPlayerTracker teleportPlayerTracker) {
        this.teleportPlayerTracker = teleportPlayerTracker;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        // TODO Load TeleportPlayer
        // TODO Put TeleportPlayer into tracker
    }

}
