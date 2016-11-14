package com.shepherdjerred.stteleports.listeners;

import com.shepherdjerred.stteleports.files.ConfigHelper;
import com.shepherdjerred.stteleports.objects.TeleportPlayer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;

public class RespawnListener implements Listener {

    @EventHandler
    public void onPlayerRespawn(PlayerRespawnEvent event) {
        TeleportPlayer teleportPlayer = TeleportPlayer.getTeleportPlayer(event.getPlayer());
        if (teleportPlayer.getHome() != null) {
            event.setRespawnLocation(teleportPlayer.getHome());
            if (ConfigHelper.debug)
                event.getPlayer().sendMessage("Respawn locations set to home");
        }
    }

}
