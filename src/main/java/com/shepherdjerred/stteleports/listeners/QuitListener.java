package com.shepherdjerred.stteleports.listeners;

import com.shepherdjerred.stteleports.objects.TeleportPlayer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class QuitListener implements Listener {

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        TeleportPlayer.getTeleportPlayers().remove(event.getPlayer().getUniqueId());
    }

}
