package com.shepherdjerred.stteleports.objects;

import com.shepherdjerred.stteleports.extensions.Vault;
import com.shepherdjerred.stteleports.files.FileManager;
import com.shepherdjerred.stteleports.util.TeleportQueue;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.concurrent.TimeUnit;

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
    @Nullable
    private TeleportPlayer teleportRequester;
    @NotNull
    private Deque<Location> previousLocations;
    private boolean override;

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
        teleportRequester = null;
        teleportPlayers.put(uuid, this);
        previousLocations = new TeleportQueue<>(3);
        override = false;
    }

    public boolean isOverride() {
        return override;
    }

    public void setOverride(boolean override) {
        this.override = override;
    }

    public static HashMap<UUID, TeleportPlayer> getTeleportPlayers() {
        return teleportPlayers;
    }

    @Nullable
    public static TeleportPlayer getTeleportPlayer(@NotNull Player player) {
        return getTeleportPlayer(player.getUniqueId());
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
        return teleportPlayers.get(uuid);
    }

    @Nullable
    public TeleportPlayer getTeleportRequester() {
        return teleportRequester;
    }

    public void setTeleportRequester(TeleportPlayer teleportRequester) {
        this.teleportRequester = teleportRequester;
    }

    public void removeTeleportRequester() {
        teleportRequester = null;
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
        FileManager.getInstance().storage.set("players." + getUuid() + ".cooldown", cooldown);
        FileManager.getInstance().saveFiles(FileManager.FileName.STORAGE);
    }

    public double getCooldownMultiplier() {
        return cooldownMultiplier;
    }

    public void setCooldownMultiplier(double cooldownMultiplier) {
        this.cooldownMultiplier = cooldownMultiplier;
        FileManager.getInstance().storage.set("players." + getUuid() + ".cooldown-multiplier", cooldownMultiplier);
        FileManager.getInstance().saveFiles(FileManager.FileName.STORAGE);
    }

    public double getCostMultiplier() {
        return costMultiplier;
    }

    public void setCostMultiplier(double costMultiplier) {
        this.costMultiplier = costMultiplier;
        FileManager.getInstance().storage.set("players." + getUuid() + ".cost-multiplier", costMultiplier);
        FileManager.getInstance().saveFiles(FileManager.FileName.STORAGE);
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

        long diffInMillies = getCooldown() - Calendar.getInstance().getTimeInMillis();

        long hours = TimeUnit.HOURS.convert(diffInMillies, TimeUnit.MILLISECONDS);
        diffInMillies -= TimeUnit.HOURS.toMillis(hours);

        long minutes = TimeUnit.MINUTES.convert(diffInMillies, TimeUnit.MILLISECONDS);
        diffInMillies -= TimeUnit.MINUTES.toMillis(minutes);

        long seconds = TimeUnit.SECONDS.convert(diffInMillies, TimeUnit.MILLISECONDS);

        if (hours < 1 && minutes < 1 && seconds < 1)
            seconds = 1L;

        String string = "";

        if (hours > 0) {
            string = string.concat(String.valueOf(hours) + " hour");
            if (hours > 1)
                string = string.concat("s");
        }

        if (hours > 0 && minutes > 0)
            string = string.concat(", ");

        if (minutes > 0) {
            string = string.concat(String.valueOf(minutes) + " minute");
            if (minutes > 1)
                string = string.concat("s");
        }

        if (minutes > 0 && seconds > 0 || minutes == 0 && seconds > 0 && hours > 0)
            string = string.concat(", ");

        if (seconds > 0) {
            string = string.concat(String.valueOf(seconds) + " second");
            if (seconds > 1)
                string = string.concat("s");
        }

        return String.valueOf(string);
    }

    public boolean cooldownIsOver() {
        return getCooldown() <= Calendar.getInstance().getTimeInMillis();
    }

    public void runTeleport(Teleport teleport) {
        if (Vault.enabled)
            if (!Vault.chargeForTeleport(Bukkit.getPlayer(getUuid()), teleport)) {
                return;
            } else {
                setCostMultiplier(getCostMultiplier() + teleport.getCostMultiplier());
            }

        setCooldown((long) (teleport.getCooldown() * getCooldownMultiplier()));
        setCooldownMultiplier(getCooldownMultiplier() + teleport.getCooldownMultiplier());

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.SECOND, (int) getCooldown());

        setCooldown(calendar.getTimeInMillis());
    }

    @NotNull
    public Deque<Location> getPreviousLocations() {
        return previousLocations;
    }
}
