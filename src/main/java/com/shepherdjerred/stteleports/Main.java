package com.shepherdjerred.stteleports;

import com.shepherdjerred.stteleports.commands.*;
import com.shepherdjerred.stteleports.files.ConfigHelper;
import com.shepherdjerred.stteleports.files.FileManager;
import com.shepherdjerred.stteleports.listeners.JoinListener;
import com.shepherdjerred.stteleports.listeners.QuitListener;
import com.shepherdjerred.stteleports.listeners.RespawnListener;
import com.shepherdjerred.stteleports.listeners.TeleportListener;
import com.shepherdjerred.stteleports.metrics.MetricsLite;
import com.shepherdjerred.stteleports.objects.TeleportPlayer;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;
import java.util.UUID;

public class Main extends JavaPlugin {

    private static Main instance;

    public Main() {
        instance = this;
    }

    public static Main getInstance() {
        return instance;
    }

    @Override
    public void onEnable() {

        ConfigHelper.loadConfigs();

        runDecrement();

        this.getCommand("stteleports").setExecutor(new MainExecutor());

        this.getCommand("teleport").setExecutor(new TPExecutor());
        this.getCommand("tpa").setExecutor(new TPAExecutor());
        this.getCommand("sethome").setExecutor(new SetHomeExecutor());
        this.getCommand("home").setExecutor(new HomeExecutor());
        this.getCommand("backward").setExecutor(new BackExecutor());
        this.getCommand("forward").setExecutor(new ForwardExecutor());

        getServer().getPluginManager().registerEvents(new JoinListener(), this);
        getServer().getPluginManager().registerEvents(new QuitListener(), this);
        getServer().getPluginManager().registerEvents(new RespawnListener(), this);
        getServer().getPluginManager().registerEvents(new TeleportListener(), this);

        try {
            MetricsLite metrics = new MetricsLite(this);
            metrics.start();
        } catch (IOException e) {
            getLogger().info("Error enabling metrics");
            e.printStackTrace();
        }

        Bukkit.getServer().getOnlinePlayers().forEach(player -> {
            TeleportPlayer teleportPlayer = new TeleportPlayer(player.getUniqueId());

            if (FileManager.getInstance().storage.getConfigurationSection("players." + player.getUniqueId() + ".cooldown") != null)
                teleportPlayer.setCooldown(FileManager.getInstance().storage.getLong("players." + player.getUniqueId() + ".cooldown"));

            if (FileManager.getInstance().storage.getConfigurationSection("players." + player.getUniqueId() + ".cooldown-multiplier") != null)
                teleportPlayer.setCooldownMultiplier(FileManager.getInstance().storage.getDouble("players." + player.getUniqueId() + ".cooldown-multiplier"));

            if (FileManager.getInstance().storage.getConfigurationSection("players." + player.getUniqueId() + ".cost-multiplier") != null)
                teleportPlayer.setCostMultiplier(FileManager.getInstance().storage.getDouble("players." + player.getUniqueId() + ".cost-multiplier"));

            if (FileManager.getInstance().storage.getConfigurationSection("players." + player.getUniqueId() + ".homes.home") != null
                    && Bukkit.getWorld(UUID.fromString(FileManager.getInstance().storage.getString("" + player.getUniqueId() + ".homes.home.world"))) != null) {
                teleportPlayer.setHome(new Location(
                        Bukkit.getWorld(UUID.fromString(FileManager.getInstance().storage.getString("" + player.getUniqueId() + ".homes.home.world"))),
                        FileManager.getInstance().storage.getDouble("players." + player.getUniqueId() + ".homes.home.x"),
                        FileManager.getInstance().storage.getDouble("players." + player.getUniqueId() + ".homes.home.y"),
                        FileManager.getInstance().storage.getDouble("players." + player.getUniqueId() + ".homes.home.z"),
                        Float.parseFloat(FileManager.getInstance().storage.getString("players." + player.getUniqueId() + ".homes.home.yaw")),
                        Float.parseFloat(FileManager.getInstance().storage.getString("players." + player.getUniqueId() + ".homes.home.pitch"))
                ));
            }

        });

    }

    void runDecrement() {
        getServer().getScheduler().scheduleSyncRepeatingTask(this, () -> Bukkit.getServer().getOnlinePlayers().forEach(player -> {

            TeleportPlayer teleportPlayer = TeleportPlayer.getTeleportPlayer(player);

            if (teleportPlayer.getCooldownMultiplier() > 1) {
                teleportPlayer.setCooldownMultiplier(teleportPlayer.getCooldownMultiplier() - getConfig().getDouble("periodic.cooldown-multiplier-decrease"));
                if (teleportPlayer.getCooldownMultiplier() < 1)
                    teleportPlayer.setCooldownMultiplier(1);
            }

            if (teleportPlayer.getCostMultiplier() > 1) {
                teleportPlayer.setCostMultiplier(teleportPlayer.getCooldownMultiplier() - getConfig().getDouble("periodic.cost-multiplier-decrease"));
                if (teleportPlayer.getCostMultiplier() < 1)
                    teleportPlayer.setCostMultiplier(1);
            }

            if (ConfigHelper.debug) {
                getLogger().info("Decrementing multipliers");
                getLogger().info("New cooldown multiplier for " + player.getName() + ": " + teleportPlayer.getCooldownMultiplier());
                getLogger().info("New cost multiplier for " + player.getName() + ": " + teleportPlayer.getCostMultiplier());
            }

        }), 0L, getConfig().getInt("periodic.frequency") * 20);
    }
}
