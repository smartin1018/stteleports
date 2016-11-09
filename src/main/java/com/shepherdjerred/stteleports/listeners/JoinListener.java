package com.shepherdjerred.stteleports.listeners;

import com.shepherdjerred.stteleports.Main;
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

        if (Main.getInstance().getConfig().getBoolean("override.cooldown.op") && event.getPlayer().isOp())
            teleportPlayer.setOverride(true);

        if (Main.getInstance().getConfig().getBoolean("override.cost.op") && event.getPlayer().isOp())
            teleportPlayer.setOverride(true);

        if (FileManager.getInstance().storage.getConfigurationSection("players." + event.getPlayer().getUniqueId() + ".cooldown") != null)
            teleportPlayer.setCooldown(FileManager.getInstance().storage.getLong("players." + event.getPlayer().getUniqueId() + ".cooldown"));

        if (FileManager.getInstance().storage.getConfigurationSection("players." + event.getPlayer().getUniqueId() + ".cooldown-multiplier") != null)
            teleportPlayer.setCooldownMultiplier(FileManager.getInstance().storage.getDouble("players." + event.getPlayer().getUniqueId() + ".cooldown-multiplier"));

        if (FileManager.getInstance().storage.getConfigurationSection("players." + event.getPlayer().getUniqueId() + ".cost-multiplier") != null)
            teleportPlayer.setCostMultiplier(FileManager.getInstance().storage.getDouble("players." + event.getPlayer().getUniqueId() + ".cost-multiplier"));

        if (FileManager.getInstance().storage.getConfigurationSection("players." + event.getPlayer().getUniqueId() + ".homes.home") != null
                && Bukkit.getWorld(UUID.fromString(FileManager.getInstance().storage.getString("players." + event.getPlayer().getUniqueId() + ".homes.home.world"))) != null) {
            teleportPlayer.setHome(new Location(
                    Bukkit.getWorld(UUID.fromString(FileManager.getInstance().storage.getString("players." + event.getPlayer().getUniqueId() + ".homes.home.world"))),
                    FileManager.getInstance().storage.getDouble("players." + event.getPlayer().getUniqueId() + ".homes.home.x"),
                    FileManager.getInstance().storage.getDouble("players." + event.getPlayer().getUniqueId() + ".homes.home.y"),
                    FileManager.getInstance().storage.getDouble("players." + event.getPlayer().getUniqueId() + ".homes.home.z"),
                    Float.parseFloat(FileManager.getInstance().storage.getString("players." + event.getPlayer().getUniqueId() + ".homes.home.yaw")),
                    Float.parseFloat(FileManager.getInstance().storage.getString("players." + event.getPlayer().getUniqueId() + ".homes.home.pitch"))
            ));
        }

    }

}
