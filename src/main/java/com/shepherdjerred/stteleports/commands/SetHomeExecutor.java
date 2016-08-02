package com.shepherdjerred.stteleports.commands;

import com.shepherdjerred.stteleports.objects.TeleportPlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SetHomeExecutor implements CommandExecutor {

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage("Players only");
            return true;
        }

        if (!sender.hasPermission("stTeleports.sethome")) {
            sender.sendMessage("No permission");
            return true;
        }

        TeleportPlayer teleportPlayer = TeleportPlayer.getTeleportPlayer(((Player) sender).getUniqueId());

        teleportPlayer.setHome(((Player) sender).getLocation());
        sender.sendMessage("Set home");

        return true;

    }


}
