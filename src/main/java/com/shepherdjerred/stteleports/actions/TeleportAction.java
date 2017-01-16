package com.shepherdjerred.stteleports.actions;

import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class TeleportAction {

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

}