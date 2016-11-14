package com.shepherdjerred.stteleports.commands;

import com.shepherdjerred.stteleports.messages.MessageHelper;
import com.shepherdjerred.stteleports.messages.commands.GenericMessages;
import com.shepherdjerred.stteleports.objects.Teleport;
import com.shepherdjerred.stteleports.objects.TeleportPlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SpawnExecutor implements CommandExecutor {


    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage(GenericMessages.getNoConsoleMessage());
            return true;
        }

        if (!sender.hasPermission("stTeleports.teleport.spawn")) {
            sender.sendMessage(GenericMessages.getNoPermsMessage());
            return true;
        }

        TeleportPlayer teleportPlayer = TeleportPlayer.getTeleportPlayer(((Player) sender).getUniqueId());

        if (!teleportPlayer.cooldownIsOver()) {
            sender.sendMessage(MessageHelper.getMessagePrefix() + MessageHelper.colorMessagesString("commands.shared.stillCoolingDown")
                    .replace("%time%", teleportPlayer.getCooldownString()));
            return true;
        }

        ((Player) sender).teleport(((Player) sender).getWorld().getSpawnLocation());
        sender.sendMessage(MessageHelper.getMessagePrefix() + MessageHelper.colorMessagesString("commands.spawn.success"));

        teleportPlayer.runTeleport(Teleport.getTeleport("spawn"));

        return true;

    }


}
