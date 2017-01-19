package com.shepherdjerred.stteleports.listeners;

import com.shepherdjerred.riotbase.listeners.AbstractListener;
import com.shepherdjerred.stteleports.objects.trackers.TeleportPlayers;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerQuitEvent;

public class QuitListener extends AbstractListener {

    private final TeleportPlayers teleportPlayers;

    public QuitListener(TeleportPlayers teleportPlayers) {
        this.teleportPlayers = teleportPlayers;
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        teleportPlayers.remove(event.getPlayer());
    }

}
