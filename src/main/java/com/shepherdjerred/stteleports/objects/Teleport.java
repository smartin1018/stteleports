package com.shepherdjerred.stteleports.objects;

import com.shepherdjerred.stteleports.Main;

import java.util.HashMap;

public class Teleport {

    public static HashMap<String, Teleport> teleports = new HashMap<>();

    private String name;
    private boolean enabled;
    private int cooldown;
    private double cost;
    private double cooldownMultiplier;
    private double costMultiplier;

    public Teleport(String name) {
        this.name = name;
        this.enabled = Main.getInstance().getConfig().getBoolean("teleports." + name + ".enabled");
        this.cooldown = Main.getInstance().getConfig().getInt("teleports." + name + ".cooldown");
        this.cost = Main.getInstance().getConfig().getDouble("teleports." + name + ".cost");
        this.cooldownMultiplier = Main.getInstance().getConfig().getDouble("teleports." + name + ".cooldown-multiplier");
        this.costMultiplier = Main.getInstance().getConfig().getDouble("teleports." + name + ".cost-multiplier");
        teleports.put(name, this);
    }

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
