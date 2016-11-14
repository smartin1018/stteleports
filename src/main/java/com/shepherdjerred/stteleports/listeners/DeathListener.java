package com.shepherdjerred.stteleports.listeners;

import com.shepherdjerred.stteleports.objects.TeleportPlayer;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

import java.util.EventListener;

public class DeathListener implements Listener {

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        TeleportPlayer teleportPlayer = TeleportPlayer.getTeleportPlayer(event.getEntity());
        teleportPlayer.getPreviousLocations().add(event.getEntity().getLocation());
    }

}
