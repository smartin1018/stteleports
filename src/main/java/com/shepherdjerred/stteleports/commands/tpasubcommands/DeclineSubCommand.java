package com.shepherdjerred.stteleports.commands.tpasubcommands;

import com.shepherdjerred.stteleports.messages.commands.GenericMessages;
import com.shepherdjerred.stteleports.messages.commands.SharedMessages;
import com.shepherdjerred.stteleports.objects.TeleportPlayer;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class DeclineSubCommand {

    public static void Executor(CommandSender sender, String[] args) {


        if (!(sender instanceof Player)) {
            sender.sendMessage(GenericMessages.getNoConsoleMessage());
            return;
        }

        if (!sender.hasPermission("stTeleports.teleport.request.decline")) {
            sender.sendMessage(GenericMessages.getNoPermsMessage());
            return;
        }

        if (args.length < 1) {
            sender.sendMessage(GenericMessages.getNoArgsMessage("<target>"));
            return;
        }

        TeleportPlayer teleportPlayer = TeleportPlayer.getTeleportPlayer(((Player) sender).getUniqueId());

        if (Bukkit.getPlayer(args[1]) != null) {
            sender.sendMessage(SharedMessages.getTargetNotOnlineMessage(args[1]));
            return;
        }

        if (!(teleportPlayer.getTeleportRequester() == TeleportPlayer.getTeleportPlayer(args[1]))) {
            sender.sendMessage("No request from " + args[1]);
            return;
        }

        teleportPlayer.removeTeleportRequester();
        sender.sendMessage("Delined request");

    }

}
