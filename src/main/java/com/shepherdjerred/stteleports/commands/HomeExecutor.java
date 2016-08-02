package com.shepherdjerred.stteleports.commands;

import com.shepherdjerred.stteleports.objects.TeleportPlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class HomeExecutor implements CommandExecutor {

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage("Players only");
            return true;
        }

        if (!sender.hasPermission("stTeleports.home")) {
            sender.sendMessage("No permission");
            return true;
        }

        TeleportPlayer teleportPlayer = TeleportPlayer.getTeleportPlayer(((Player) sender).getUniqueId());

        ((Player) sender).teleport(teleportPlayer.getHome());
        sender.sendMessage("Teleported home");

        return true;

    }

}
