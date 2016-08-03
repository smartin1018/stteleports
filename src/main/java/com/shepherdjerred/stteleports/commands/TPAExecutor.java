package com.shepherdjerred.stteleports.commands;

import com.shepherdjerred.stteleports.messages.MessageHelper;
import com.shepherdjerred.stteleports.messages.commands.GenericMessages;
import com.shepherdjerred.stteleports.messages.commands.SharedMessages;
import com.shepherdjerred.stteleports.objects.TeleportPlayer;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Calendar;

public class TPAExecutor implements CommandExecutor {


    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage(GenericMessages.getNoConsoleMessage());
            return true;
        }

        if (!sender.hasPermission("stTeleports.teleport.request.send")) {
            sender.sendMessage(GenericMessages.getNoPermsMessage());
            return true;
        }

        if (args.length < 1) {
            sender.sendMessage(GenericMessages.getNoArgsMessage("<target>"));
            return true;
        }

        TeleportPlayer teleportPlayer = TeleportPlayer.getTeleportPlayer(((Player) sender).getUniqueId());

        if (!teleportPlayer.cooldownIsOver()) {
            sender.sendMessage(MessageHelper.getMessagePrefix() + MessageHelper.colorMessagesString("commands.shared.stillCoolingDown")
                    .replace("%time%", teleportPlayer.getCooldownString()));
            sender.sendMessage("Current time: " + Calendar.getInstance().getTimeInMillis());
            sender.sendMessage("Curent cooldown: " + teleportPlayer.getCooldown());
            return true;
        }

        if (Bukkit.getPlayer(args[1]) != null) {
            // TODO Delete request after 30 seconds
            TeleportPlayer.getTeleportPlayer(args[1]).getRecievedRequests().add(teleportPlayer);
            sender.sendMessage("Sent request");
        } else
            sender.sendMessage(SharedMessages.getTargetNotOnlineMessage(args[1]));

        return true;

    }


}
