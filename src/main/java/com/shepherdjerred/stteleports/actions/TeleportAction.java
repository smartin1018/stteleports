package com.shepherdjerred.stteleports.actions;

import com.shepherdjerred.stteleports.objects.Teleport;
import com.shepherdjerred.stteleports.objects.TeleportPlayer;
import com.shepherdjerred.stteleports.objects.trackers.TeleportPlayerTracker;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class TeleportAction {

    private final TeleportPlayerTracker teleportPlayerTracker;

    public TeleportAction(TeleportPlayerTracker teleportPlayerTracker) {
        this.teleportPlayerTracker = teleportPlayerTracker;
    }

    public void teleport(Player target, Player destination) {
        target.teleport(destination);
    }

    public void teleport(Player target, Location destination, boolean safe) {

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
    }

    public void sendTeleportRequest(Player target, Player destination, Teleport type) {
        TeleportPlayer targetTpPlayer = teleportPlayerTracker.get(target);
        TeleportPlayer destinationTpPlayer = teleportPlayerTracker.get(destination);

        destinationTpPlayer.addRequest(targetTpPlayer.getUuid(), type);
        // TODO Expire the invite after some time
    }

}
