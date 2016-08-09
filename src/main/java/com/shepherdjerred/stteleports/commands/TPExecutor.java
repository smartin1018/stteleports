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

        Player player = (Player) sender;
        TeleportPlayer teleportPlayer = TeleportPlayer.getTeleportPlayer(player.getUniqueId());
        boolean success = false;

        if (!teleportPlayer.cooldownIsOver()) {
            player.sendMessage(MessageHelper.getMessagePrefix() + MessageHelper.colorMessagesString("commands.shared.stillCoolingDown")
                    .replace("%time%", teleportPlayer.getCooldownString()));
            return true;
        }

        if (args.length < 2) {
            if (Bukkit.getPlayer(args[0]) != null) {
                if (Bukkit.getPlayer(args[0]) != player) {
                    player.teleport(Bukkit.getPlayer(args[0]));
                    player.sendMessage(MessageHelper.getMessagePrefix() + MessageHelper.colorMessagesString("commands.teleport.success")
                            .replace("%player%", args[0]));
                    success = true;
                } else {
                    player.sendMessage(MessageHelper.getMessagePrefix() + MessageHelper.colorMessagesString("commands.shared.cantTeleportToSelf"));
                }
            } else
                player.sendMessage(SharedMessages.getTargetNotOnlineMessage(args[0]));
        } else {
            if (Bukkit.getPlayer(args[0]) != null)
                if (Bukkit.getPlayer(args[1]) != null) {
                    if (Bukkit.getPlayer(args[0]) != Bukkit.getPlayer(args[1])) {
                        (Bukkit.getPlayer(args[0])).teleport(Bukkit.getPlayer(args[1]));
                        player.sendMessage(MessageHelper.getMessagePrefix() + MessageHelper.colorMessagesString("commands.teleport.successOther")
                                .replace("%target%", args[0]).replace("%destination%", args[1]));
                        success = true;
                    } else {
                        player.sendMessage(MessageHelper.getMessagePrefix() + MessageHelper.colorMessagesString("commands.shared.cantTeleportTargetToSelf"));
                    }
                } else
                    player.sendMessage(SharedMessages.getTargetNotOnlineMessage(args[1]));
            else
                player.sendMessage(SharedMessages.getTargetNotOnlineMessage(args[0]));
        }

        if (success) {
            teleportPlayer.runTeleport(Teleport.getTeleport("tp"));
        }

        return true;

    }


}
