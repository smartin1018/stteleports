package com.shepherdjerred.stteleports.actions;

import org.bukkit.Location;
import org.bukkit.entity.Player;

public class TeleportAction {

    public void teleport(Player target, Player destination) {
        target.teleport(destination);
    }

    public void teleport(Player target, Location location) {
        target.teleport(location);
    }

}
