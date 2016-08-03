package com.shepherdjerred.stteleports.listeners;

import com.shepherdjerred.stteleports.files.FileManager;
import com.shepherdjerred.stteleports.objects.TeleportPlayer;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.UUID;

public class JoinListener implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        TeleportPlayer teleportPlayer = new TeleportPlayer(event.getPlayer().getUniqueId());

        if (FileManager.getInstance().storage.getConfigurationSection("homes." + event.getPlayer().getUniqueId()) + "home" != null) {
            teleportPlayer.setHome(new Location(
                    Bukkit.getWorld(UUID.fromString(FileManager.getInstance().storage.getString("homes." + event.getPlayer().getUniqueId() + ".home.world"))),
                    FileManager.getInstance().storage.getDouble("homes." + event.getPlayer().getUniqueId() + ".home.x"),
                    FileManager.getInstance().storage.getDouble("homes." + event.getPlayer().getUniqueId() + ".home.y"),
                    FileManager.getInstance().storage.getDouble("homes." + event.getPlayer().getUniqueId() + ".home.z"),
                    Float.parseFloat(FileManager.getInstance().storage.getString("homes." + event.getPlayer().getUniqueId() + ".home.yaw")),
                    Float.parseFloat(FileManager.getInstance().storage.getString("homes." + event.getPlayer().getUniqueId() + ".home.pitch"))
            ));
        }

    }

}
