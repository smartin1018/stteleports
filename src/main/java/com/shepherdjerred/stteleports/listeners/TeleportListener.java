package com.shepherdjerred.stteleports.listeners;

import com.shepherdjerred.stteleports.objects.TeleportPlayer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerTeleportEvent;

public class TeleportListener implements Listener {

    @EventHandler
    public void onPlayerTeleport(PlayerTeleportEvent event) {
        TeleportPlayer teleportPlayer = TeleportPlayer.getTeleportPlayer(event.getPlayer());
        teleportPlayer.getPreviousLocations().add(event.getFrom());
    }

}
