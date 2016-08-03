package com.shepherdjerred.stteleports.commands;

import com.shepherdjerred.stteleports.messages.MessageHelper;
import com.shepherdjerred.stteleports.messages.commands.GenericMessages;
import com.shepherdjerred.stteleports.objects.Teleport;
import com.shepherdjerred.stteleports.objects.TeleportPlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Calendar;

public class HomeExecutor implements CommandExecutor {

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage(GenericMessages.getNoConsoleMessage());
            return true;
        }

        if (!sender.hasPermission("stTeleports.home")) {
            sender.sendMessage(GenericMessages.getNoPermsMessage());
            return true;
        }

        TeleportPlayer teleportPlayer = TeleportPlayer.getTeleportPlayer(((Player) sender).getUniqueId());

        if (teleportPlayer.getHome() == null) {
            sender.sendMessage(MessageHelper.colorMessagesString("commands.home.noHome"));
        }

        if (!teleportPlayer.cooldownIsOver()) {
            sender.sendMessage(MessageHelper.getMessagePrefix() + MessageHelper.colorMessagesString("commands.shared.stillCoolingDown")
                    .replace("%time%", teleportPlayer.getCooldownString()));
            sender.sendMessage("Current time: " + Calendar.getInstance().getTimeInMillis());
            sender.sendMessage("Curent cooldown: " + teleportPlayer.getCooldown());
            return true;
        }

        ((Player) sender).teleport(teleportPlayer.getHome());
        sender.sendMessage(MessageHelper.getMessagePrefix() + MessageHelper.colorMessagesString("commands.home.success"));

        teleportPlayer.runTeleport(Teleport.getTeleport("home"));
        sender.sendMessage("New cooldown multiplier: " + teleportPlayer.getCooldownMultiplier());
        sender.sendMessage("New cooldown: " + teleportPlayer.getCooldown());

        return true;

    }

}
