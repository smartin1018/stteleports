package com.shepherdjerred.stteleports.listeners;

import com.shepherdjerred.riotbase.listeners.AbstractListener;
import com.shepherdjerred.stteleports.database.TeleportPlayerDAO;
import com.shepherdjerred.stteleports.objects.TeleportPlayer;
import com.shepherdjerred.stteleports.objects.trackers.TeleportPlayerTracker;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;

public class JoinListener extends AbstractListener {

    private final TeleportPlayerTracker teleportPlayerTracker;
    private final TeleportPlayerDAO teleportPlayerDAO;

    public JoinListener(TeleportPlayerTracker teleportPlayerTracker, TeleportPlayerDAO teleportPlayerDAO) {
        this.teleportPlayerTracker = teleportPlayerTracker;
        this.teleportPlayerDAO = teleportPlayerDAO;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        // TODO Do this async
        loadPlayer(event.getPlayer());
    }

    public void loadPlayer(Player player) {
        TeleportPlayer teleportPlayer = teleportPlayerDAO.load(player.getUniqueId());

        if (teleportPlayer == null) {
            teleportPlayer = new TeleportPlayer(player.getUniqueId());
            teleportPlayerDAO.insert(teleportPlayer);
        }

        teleportPlayerTracker.add(teleportPlayer);
    }

}
