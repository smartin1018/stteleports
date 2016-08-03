package com.shepherdjerred.stteleports.objects;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public class TeleportPlayer {

    @NotNull
    private static HashMap<UUID, TeleportPlayer> teleportPlayers = new HashMap<>();
    @NotNull
    private UUID uuid;
    @NotNull
    private String name;
    private long cooldown;
    private double cooldownMultiplier;
    private double costMultiplier;
    @Nullable
    private Location home;
    @NotNull
    private List<TeleportPlayer> recievedRequests;
    /**
     * Creates a TeleportPlayer object, only the UUID is needed
     * Name is pulled from Bukkit, other values default to 'empty' states
     *
     * @param uuid The UUID of the player
     */
    public TeleportPlayer(@NotNull UUID uuid) {
        this.uuid = uuid;
        name = Bukkit.getPlayer(uuid).getName();
        cooldown = 0L;
        cooldownMultiplier = 1.0;
        costMultiplier = 1.0;
        recievedRequests = new ArrayList<>();
        teleportPlayers.put(uuid, this);

    }

    public static HashMap<UUID, TeleportPlayer> getTeleportPlayers() {
        return teleportPlayers;
    }

    @Nullable
    public static TeleportPlayer getTeleportPlayer(@NotNull String name) {
        for (TeleportPlayer teleportPlayer : teleportPlayers.values())
            if (teleportPlayer.getName().equals(name))
                return teleportPlayer;

        return null;
    }

    @Nullable
    public static TeleportPlayer getTeleportPlayer(@NotNull UUID uuid) {
        return teleportPlayers.getOrDefault(uuid, null);
    }

    @NotNull
    public List<TeleportPlayer> getRecievedRequests() {
        return recievedRequests;
    }

    @NotNull
    public UUID getUuid() {
        return uuid;
    }

    @NotNull
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

    @Nullable
    public Location getHome() {
        return home;
    }

    public void setHome(@Nullable Location home) {
        this.home = home;
    }

    @NotNull
    public String getCooldownString() {
        return String.valueOf((Calendar.getInstance().getTimeInMillis() - getCooldown()) / 1000L);
    }

    public boolean cooldownIsOver() {
        return getCooldown() <= Calendar.getInstance().getTimeInMillis();
    }

    public void runTeleport(Teleport teleport) {
        setCooldown(teleport.getCooldown() + (int) (teleport.getCooldownMultiplier() * getCooldownMultiplier()));
        setCooldownMultiplier(getCooldownMultiplier() + teleport.getCooldownMultiplier());

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.SECOND, (int) getCooldown());

        setCooldown(calendar.getTimeInMillis());
    }
}
