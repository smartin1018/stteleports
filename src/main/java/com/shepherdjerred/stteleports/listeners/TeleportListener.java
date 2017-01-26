package com.shepherdjerred.stteleports.listeners;

import com.shepherdjerred.riotbase.listeners.AbstractListener;
import com.shepherdjerred.stteleports.objects.TeleportPlayer;
import com.shepherdjerred.stteleports.objects.trackers.TeleportPlayers;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerTeleportEvent;

public class TeleportListener extends AbstractListener {

    private final TeleportPlayers teleportPlayers;

    public TeleportListener(TeleportPlayers teleportPlayers) {
        this.teleportPlayers = teleportPlayers;
    }

    @EventHandler
    public void onPlayerTeleport(PlayerTeleportEvent event) {
        TeleportPlayer player = teleportPlayers.get(event.getPlayer());
        if (player != null) {
            player.addLocation(event.getFrom());
        }
    }
}
