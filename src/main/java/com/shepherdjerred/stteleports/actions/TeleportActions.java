package com.shepherdjerred.stteleports.actions;

import com.shepherdjerred.stteleports.objects.Teleport;
import com.shepherdjerred.stteleports.objects.TeleportPlayer;
import com.shepherdjerred.stteleports.objects.trackers.TeleportPlayerTracker;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class TeleportActions {

    private final TeleportPlayerTracker teleportPlayerTracker;
    private final Economy economy;

    public TeleportActions(TeleportPlayerTracker teleportPlayerTracker, Economy economy) {
        this.teleportPlayerTracker = teleportPlayerTracker;
        this.economy = economy;
    }

    public void teleport(Teleport type, Player target, Player destination) {
        target.teleport(destination);
        doCooldown(teleportPlayerTracker.get(target), type);
        chargePlayer(target, type);
    }

    public void teleport(Teleport type, Player target, Location destination, boolean safe) {

        if (safe) {
            // Let's be sure we don't teleport them in a block
            while (destination.getBlock().getType() != Material.AIR) {
                destination.add(0, 1, 0);
            }

            if (target.getGameMode() == GameMode.SURVIVAL) {
                // Let's be sure they don't fall to their death
                while (destination.add(0, -1, 0).getBlock().getType() == Material.AIR) {
                    destination.add(0, -1, 0);
                }
            }
        }

        target.teleport(destination);
        doCooldown(teleportPlayerTracker.get(target), type);
        chargePlayer(target, type);
    }

    public void sendTeleportRequest(Player target, Player destination, Teleport type) {
        TeleportPlayer targetTpPlayer = teleportPlayerTracker.get(target);
        TeleportPlayer destinationTpPlayer = teleportPlayerTracker.get(destination);

        destinationTpPlayer.addRequest(targetTpPlayer.getUuid(), type);
        // TODO Expire the invite after some time
    }

    private void doCooldown(TeleportPlayer teleportPlayer, Teleport type) {
        teleportPlayer.calculateCooldown(type.getCooldown());
        teleportPlayer.calculateCooldownMultiplier(type.getCooldownMultiplier());
    }

    private void chargePlayer(Player player, Teleport type) {
        TeleportPlayer teleportPlayer = teleportPlayerTracker.get(player);
        economy.withdrawPlayer(player, type.getCost());
        teleportPlayer.calculateCostMultiplier(teleportPlayer.getCooldownMultiplier());
    }

}
