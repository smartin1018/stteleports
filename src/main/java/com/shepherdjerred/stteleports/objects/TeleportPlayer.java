package com.shepherdjerred.stteleports.objects;

import com.shepherdjerred.stteleports.util.TeleportQueue;
import org.bukkit.Location;

import java.util.*;

public class TeleportPlayer {

    private static Calendar calendar = Calendar.getInstance();

    private final UUID uuid;

    private long cooldown = 0L;

    private final Map<String, Location> homes = new HashMap<>();
    private final Deque<Location> locations = new TeleportQueue<>(5);
    private final Map<UUID, Teleport> requesters = new HashMap<>();

    private double cooldownMultiplier = 1D;
    private double costMultiplier = 1D;

    private double cooldownMultiplierModifier = 1D;
    private double costMultiplierModifier = 1D;

    public TeleportPlayer(UUID uuid) {
        this.uuid = uuid;
    }

    public TeleportPlayer(UUID uuid, long cooldown, double cooldownMultiplier, double costMultiplier, double cooldownMultiplierModifier, double costMultiplierModifier) {
        this.uuid = uuid;
        this.cooldown = cooldown;
        this.cooldownMultiplier = cooldownMultiplier;
        this.costMultiplier = costMultiplier;
        this.cooldownMultiplierModifier = cooldownMultiplierModifier;
        this.costMultiplierModifier = costMultiplierModifier;
    }

    public void setCooldownMultiplier(double multiplier) {
        cooldownMultiplier = (cooldownMultiplier * (multiplier * cooldownMultiplierModifier));
    }

    public void calculateCostMultiplier(double multiplier) {
        costMultiplier = (costMultiplier * (multiplier * costMultiplierModifier));
    }

    public void setCooldownMultiplierModifier(double cooldownMultiplierModifier) {
        this.cooldownMultiplierModifier = cooldownMultiplierModifier;
    }

    public void setCostMultiplierModifier(double costMultiplierModifier) {
        this.costMultiplierModifier = costMultiplierModifier;
    }

    public long getCooldown() {
        return cooldown;
    }

    public double getCooldownMultiplier() {
        return cooldownMultiplier;
    }

    public double getCostMultiplier() {
        return costMultiplier;
    }

    public double getCooldownMultiplierModifier() {
        return cooldownMultiplierModifier;
    }

    public double getCostMultiplierModifier() {
        return costMultiplierModifier;
    }

    public void addRequest(UUID player, Teleport type) {
        requesters.put(player, type);
    }

    public boolean hasRequest(UUID player) {
        return requesters.containsKey(player);
    }

    public Teleport getTeleportType(UUID player) {
        return requesters.get(player);
    }

    public void removeRequest(UUID player) {
        requesters.remove(player);
    }

    public UUID getUuid() {
        return uuid;
    }

    public Location getHome(String homeName) {
        return homes.get(homeName);
    }

    public void addHome(String name, Location location) {
        homes.put(name, location);
    }

    public void removeHome(String name) {
        homes.remove(name);
    }

    public Location getLastLocation() {
        return locations.removeLast();
    }

    public Location getFirstLocation() {
        return locations.removeFirst();
    }

    public void addLocation(Location location) {
        locations.add(location);
    }

    public void calculateCooldown(long cooldown) {
        double newCooldown = (cooldown + calendar.getTimeInMillis()) * cooldownMultiplier;
        this.cooldown = (long) newCooldown;
    }

    public boolean isCooldownOver() {
        return cooldown <= calendar.getTimeInMillis();
    }

    @Override
    public String toString() {
        return "TeleportPlayer{" +
                "uuid=" + uuid +
                ", cooldown=" + cooldown +
                ", homes=" + homes +
                ", locations=" + locations +
                ", requesters=" + requesters +
                ", cooldownMultiplier=" + cooldownMultiplier +
                ", costMultiplier=" + costMultiplier +
                ", cooldownMultiplierModifier=" + cooldownMultiplierModifier +
                ", costMultiplierModifier=" + costMultiplierModifier +
                '}';
    }
}
