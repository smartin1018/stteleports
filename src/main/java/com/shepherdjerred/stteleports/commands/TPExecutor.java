package com.shepherdjerred.stteleports.commands;

import com.shepherdjerred.stteleports.messages.MessageHelper;
import com.shepherdjerred.stteleports.messages.commands.GenericMessages;
import com.shepherdjerred.stteleports.messages.commands.SharedMessages;
import com.shepherdjerred.stteleports.objects.Teleport;
import com.shepherdjerred.stteleports.objects.TeleportPlayer;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Calendar;

public class TPExecutor implements CommandExecutor {

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage(GenericMessages.getNoConsoleMessage());
            return true;
        }

        if (!sender.hasPermission("stTeleports.teleport.teleport")) {
            sender.sendMessage(GenericMessages.getNoPermsMessage());
            return true;
        }

        if (args.length < 1) {
            sender.sendMessage(GenericMessages.getNoArgsMessage("<target> [target]"));
            return true;
        }

        TeleportPlayer teleportPlayer = TeleportPlayer.getTeleportPlayer(((Player) sender).getUniqueId());
        boolean success = false;

        if (!teleportPlayer.cooldownIsOver()) {
            sender.sendMessage(MessageHelper.getMessagePrefix() + MessageHelper.colorMessagesString("commands.shared.stillCoolingDown")
                    .replace("%time%", teleportPlayer.getCooldownString()));
            sender.sendMessage("Current time: " + Calendar.getInstance().getTimeInMillis());
            sender.sendMessage("Curent cooldown: " + teleportPlayer.getCooldown());
            return true;
        }

        if (args.length < 2) {
            if (Bukkit.getPlayer(args[0]) != null) {
                ((Player) sender).teleport(Bukkit.getPlayer(args[0]));
                sender.sendMessage(MessageHelper.getMessagePrefix() + MessageHelper.colorMessagesString("commands.teleport.success")
                        .replace("%player%", args[0]));
                success = true;
            } else
                sender.sendMessage(SharedMessages.getTargetNotOnlineMessage(args[0]));
        } else {
            if (Bukkit.getPlayer(args[0]) != null)
                if (Bukkit.getPlayer(args[1]) != null) {
                    (Bukkit.getPlayer(args[0])).teleport(Bukkit.getPlayer(args[1]));
                    sender.sendMessage(MessageHelper.getMessagePrefix() + MessageHelper.colorMessagesString("commands.teleport.successOther")
                            .replace("%target%", args[0]).replace("%destination%", args[1]));
                    success = true;
                } else
                    sender.sendMessage(SharedMessages.getTargetNotOnlineMessage(args[1]));
            else
                sender.sendMessage(SharedMessages.getTargetNotOnlineMessage(args[0]));
        }

        if (success) {
            teleportPlayer.runTeleport(Teleport.getTeleport("tp"));
            sender.sendMessage("New cooldown multiplier: " + teleportPlayer.getCooldownMultiplier());
            sender.sendMessage("New cooldown: " + teleportPlayer.getCooldown());
        }

        return true;

    }


}
