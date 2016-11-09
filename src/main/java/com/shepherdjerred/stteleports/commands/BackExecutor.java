package com.shepherdjerred.stteleports.commands;

import com.shepherdjerred.stteleports.messages.MessageHelper;
import com.shepherdjerred.stteleports.messages.commands.GenericMessages;
import com.shepherdjerred.stteleports.objects.Teleport;
import com.shepherdjerred.stteleports.objects.TeleportPlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class BackExecutor implements CommandExecutor {

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage(GenericMessages.getNoConsoleMessage());
            return true;
        }

        if (!sender.hasPermission("stTeleports.back")) {
            sender.sendMessage(GenericMessages.getNoPermsMessage());
            return true;
        }

        TeleportPlayer teleportPlayer = TeleportPlayer.getTeleportPlayer(((Player) sender).getUniqueId());

        if (teleportPlayer.getPreviousLocations().isEmpty()) {
            sender.sendMessage(MessageHelper.colorMessagesString("commands.backward.noPreviousLocations"));
            return true;
        }

        if (!teleportPlayer.cooldownIsOver()) {
            sender.sendMessage(MessageHelper.getMessagePrefix() + MessageHelper.colorMessagesString("commands.shared.stillCoolingDown")
                    .replace("%time%", teleportPlayer.getCooldownString()));
            return true;
        }

        ((Player) sender).teleport(teleportPlayer.getPreviousLocations().pollFirst());
        sender.sendMessage(MessageHelper.getMessagePrefix() + MessageHelper.colorMessagesString("commands.backward.success"));

        teleportPlayer.runTeleport(Teleport.getTeleport("backward"));

        return true;

    }

}
