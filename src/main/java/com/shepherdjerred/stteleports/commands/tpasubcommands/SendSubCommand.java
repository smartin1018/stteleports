package com.shepherdjerred.stteleports.commands.tpasubcommands;

import com.shepherdjerred.stteleports.messages.MessageHelper;
import com.shepherdjerred.stteleports.messages.commands.GenericMessages;
import com.shepherdjerred.stteleports.messages.commands.SharedMessages;
import com.shepherdjerred.stteleports.objects.TeleportPlayer;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SendSubCommand {

    public static void Executor(CommandSender sender, String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage(GenericMessages.getNoConsoleMessage());
            return;
        }

        if (!sender.hasPermission("stTeleports.teleport.request.send")) {
            sender.sendMessage(GenericMessages.getNoPermsMessage());
            return;
        }

        if (args.length < 1) {
            sender.sendMessage(GenericMessages.getNoArgsMessage("<target>"));
            return;
        }

        Player player = (Player) sender;
        TeleportPlayer teleportPlayer = TeleportPlayer.getTeleportPlayer(player.getUniqueId());

        if (!teleportPlayer.cooldownIsOver()) {
            player.sendMessage(MessageHelper.getMessagePrefix() + MessageHelper.colorMessagesString("commands.shared.stillCoolingDown")
                    .replace("%time%", teleportPlayer.getCooldownString()));
            return;
        }

        if (Bukkit.getPlayer(args[1]) != null) {
            if (Bukkit.getPlayer(args[1]) != player) {
                // TODO Delete request after 30 seconds
                TeleportPlayer.getTeleportPlayer(args[1]).setTeleportRequester(teleportPlayer);
                player.sendMessage("Sent request");
            } else
                player.sendMessage("You can't send a request to yourself");
        } else
            player.sendMessage(SharedMessages.getTargetNotOnlineMessage(args[1]));

    }

}
