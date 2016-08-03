package com.shepherdjerred.stteleports.commands;

import com.shepherdjerred.stteleports.messages.commands.GenericMessages;
import com.shepherdjerred.stteleports.messages.commands.SharedMessages;
import com.shepherdjerred.stteleports.objects.TeleportPlayer;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TPADeclineExecutor implements CommandExecutor {


    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage(GenericMessages.getNoConsoleMessage());
            return true;
        }

        if (!sender.hasPermission("stTeleports.teleport.request.decline")) {
            sender.sendMessage(GenericMessages.getNoPermsMessage());
            return true;
        }

        if (args.length < 1) {
            sender.sendMessage(GenericMessages.getNoArgsMessage("<target>"));
            return true;
        }

        TeleportPlayer teleportPlayer = TeleportPlayer.getTeleportPlayer(((Player) sender).getUniqueId());

        if (Bukkit.getPlayer(args[1]) != null) {
            sender.sendMessage(SharedMessages.getTargetNotOnlineMessage(args[1]));
            return true;
        }

        if (!teleportPlayer.getRecievedRequests().contains(TeleportPlayer.getTeleportPlayer(args[1]))) {
            sender.sendMessage("No request from " + args[1]);
            return true;
        }

        teleportPlayer.getRecievedRequests().remove(TeleportPlayer.getTeleportPlayer(args[1]));
        sender.sendMessage("Delined request");


        return true;

    }


}
