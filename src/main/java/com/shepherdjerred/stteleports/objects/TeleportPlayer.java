package com.shepherdjerred.stteleports.objects;

import org.bukkit.Bukkit;
import org.bukkit.Location;

import java.util.HashMap;
import java.util.UUID;

public class TeleportPlayer {

    public static HashMap<UUID, TeleportPlayer> teleportPlayers = new HashMap<>();

    private UUID uuid;
    private String name;
    private long cooldown;
    private double cooldownMultiplier;
    private double costMultiplier;
    private Location home;

    public TeleportPlayer(UUID uuid) {
        this.uuid = uuid;
        name = Bukkit.getPlayer(uuid).getName();
        cooldown = 0L;
        cooldownMultiplier = 1.0;
        costMultiplier = 1.0;
    }

    public static TeleportPlayer getTeleportPlayer(String name) {
        for (TeleportPlayer teleportPlayer : teleportPlayers.values())
            if (teleportPlayer.getName().equals(name))
                return teleportPlayer;

        return null;
    }

    public static TeleportPlayer getTeleportPlayer(UUID uuid) {
        return teleportPlayers.getOrDefault(uuid, null);
    }

    public UUID getUuid() {
        return uuid;
    }

    public String getName() {
        return name;
    }

    public long getCooldown() {
        return cooldown;
    }

    public void setCooldown(long cooldown) {
        this.cooldown = cooldown;
    }

    public double getCooldownMultiplier() {
        return cooldownMultiplier;
    }

    public void setCooldownMultiplier(double cooldownMultiplier) {
        this.cooldownMultiplier = cooldownMultiplier;
    }

    public double getCostMultiplier() {
        return costMultiplier;
    }

    public void setCostMultiplier(double costMultiplier) {
        this.costMultiplier = costMultiplier;
    }

    public Location getHome() {
        return home;
    }

    public void setHome(Location home) {
        this.home = home;
    }
}
