package com.shepherdjerred.stteleports.listeners;

import com.shepherdjerred.riotbase.listeners.AbstractListener;
import com.shepherdjerred.stteleports.database.TeleportPlayerDAO;
import com.shepherdjerred.stteleports.objects.TeleportPlayer;
import com.shepherdjerred.stteleports.objects.trackers.TeleportPlayers;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.UUID;

public class JoinListener extends AbstractListener {

    private final TeleportPlayers teleportPlayers;
    private final TeleportPlayerDAO teleportPlayerDAO;

    public JoinListener(TeleportPlayers teleportPlayers, TeleportPlayerDAO teleportPlayerDAO) {
        this.teleportPlayers = teleportPlayers;
        this.teleportPlayerDAO = teleportPlayerDAO;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        // TODO Do this async
        loadPlayer(event.getPlayer().getUniqueId());
    }

    public void loadPlayer(UUID uuid) {
        TeleportPlayer teleportPlayer = teleportPlayerDAO.load(uuid);

        if (teleportPlayer == null) {
            teleportPlayer = new TeleportPlayer(uuid);
            teleportPlayerDAO.insert(teleportPlayer);
        }

        teleportPlayers.add(teleportPlayer);
    }

}
