package com.shepherdjerred.stteleports.objects;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;

public class Teleport {

    @NotNull
    private static HashMap<String, Teleport> teleports = new HashMap<>();

    @NotNull
    private String name;
    private boolean enabled;
    private int cooldown;
    private double cost;
    private double cooldownMultiplier;
    private double costMultiplier;

    public Teleport(@NotNull String name, boolean enabled, int cooldown, double cost, double cooldownMultiplier, double costMultiplier) {
        this.name = name;
        this.enabled = enabled;
        this.cooldown = cooldown;
        this.cost = cost;
        this.cooldownMultiplier = cooldownMultiplier;
        this.costMultiplier = costMultiplier;
        teleports.put(name, this);
    }

    @Nullable
    public static Teleport getTeleport(@NotNull String name) {
        return teleports.getOrDefault(name, null);
    }

    @NotNull
    public String getName() {
        return name;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public int getCooldown() {
        return cooldown;
    }

    public double getCost() {
        return cost;
    }

    public double getCooldownMultiplier() {
        return cooldownMultiplier;
    }

    public double getCostMultiplier() {
        return costMultiplier;
    }

}
