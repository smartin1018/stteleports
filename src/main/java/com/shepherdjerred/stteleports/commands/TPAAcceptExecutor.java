package com.shepherdjerred.stteleports.commands;

import com.shepherdjerred.stteleports.messages.commands.GenericMessages;
import com.shepherdjerred.stteleports.messages.commands.SharedMessages;
import com.shepherdjerred.stteleports.objects.Teleport;
import com.shepherdjerred.stteleports.objects.TeleportPlayer;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TPAAcceptExecutor implements CommandExecutor {

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage(GenericMessages.getNoConsoleMessage());
            return true;
        }

        if (!sender.hasPermission("stTeleports.teleport.request.accept")) {
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
        TeleportPlayer.getTeleportPlayer(args[1]).runTeleport(Teleport.getTeleport("tpa"));
        Bukkit.getPlayer(args[1]).teleport(((Player) sender).getLocation());
        Bukkit.getPlayer(args[1]).sendMessage(sender.getName() + " accepted your tpa request");

        sender.sendMessage("Accepted request");
        sender.sendMessage("New cooldown multiplier: " + teleportPlayer.getCooldownMultiplier());
        sender.sendMessage("New cooldown: " + teleportPlayer.getCooldown());

        return true;

    }

}
