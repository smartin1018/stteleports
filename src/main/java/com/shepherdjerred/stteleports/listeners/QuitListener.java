package com.shepherdjerred.stteleports.listeners;

import com.shepherdjerred.riotbase.listeners.AbstractListener;
import com.shepherdjerred.stteleports.objects.trackers.TeleportPlayerTracker;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerQuitEvent;

public class QuitListener extends AbstractListener {

    private final TeleportPlayerTracker teleportPlayerTracker;

    public QuitListener(TeleportPlayerTracker teleportPlayerTracker) {
        this.teleportPlayerTracker = teleportPlayerTracker;
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        teleportPlayerTracker.remove(event.getPlayer());
    }

}
