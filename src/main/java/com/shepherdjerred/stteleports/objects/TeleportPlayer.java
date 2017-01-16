package com.shepherdjerred.stteleports.objects;

import com.shepherdjerred.stteleports.util.TeleportQueue;
import org.bukkit.Location;

import java.util.*;

public class TeleportPlayer {

    private final UUID uuid;

    private final long nextAvaliableTeleport = 0L;

    private final Map<String, Location> homes = new HashMap<>();
    private final Deque<Location> locations = new TeleportQueue<>(5);

    private double cooldownMultiplier = 1D;
    private double costMultiplier = 1D;

    private double cooldownMultiplierModifier = 1D;
    private double costMultiplierModifier = 1D;

    public TeleportPlayer(UUID uuid) {
        this.uuid = uuid;
    }

    public TeleportPlayer(UUID uuid, double cooldownMultiplier, double costMultiplier, double cooldownMultiplierModifier, double costMultiplierModifier) {
        this.uuid = uuid;
        this.cooldownMultiplier = cooldownMultiplier;
        this.costMultiplier = costMultiplier;
        this.cooldownMultiplierModifier = cooldownMultiplierModifier;
        this.costMultiplierModifier = costMultiplierModifier;
    }

    public long getNextAvaliableTeleport() {
        return nextAvaliableTeleport;
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

}
