package com.shepherdjerred.stteleports.listeners;

import com.shepherdjerred.riotbase.listeners.AbstractListener;
import com.shepherdjerred.stteleports.objects.trackers.TeleportPlayerTracker;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerTeleportEvent;

public class TeleportListener extends AbstractListener {

    private final TeleportPlayerTracker teleportPlayerTracker;

    public TeleportListener(TeleportPlayerTracker teleportPlayerTracker) {
        this.teleportPlayerTracker = teleportPlayerTracker;
    }

    @EventHandler
    public void onPlayerTeleport(PlayerTeleportEvent event) {
        // TODO Get TeleportPlayer
        // TODO Add the previous location to the TeleportPlayer's queue
    }

}
