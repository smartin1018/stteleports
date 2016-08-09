package com.shepherdjerred.stteleports.commands.tpasubcommands;

import com.shepherdjerred.stteleports.messages.MessageHelper;
import com.shepherdjerred.stteleports.messages.commands.GenericMessages;
import com.shepherdjerred.stteleports.messages.commands.SharedMessages;
import com.shepherdjerred.stteleports.objects.Teleport;
import com.shepherdjerred.stteleports.objects.TeleportPlayer;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class AcceptSubCommand {

    public static void Executor(CommandSender sender, String[] args) {


        if (!(sender instanceof Player)) {
            sender.sendMessage(GenericMessages.getNoConsoleMessage());
            return;
        }

        if (!sender.hasPermission("stTeleports.teleport.request.accept")) {
            sender.sendMessage(GenericMessages.getNoPermsMessage());
            return;
        }

        if (args.length < 2) {
            sender.sendMessage(GenericMessages.getNoArgsMessage("<target>"));
            return;
        }

        TeleportPlayer teleportPlayer = TeleportPlayer.getTeleportPlayer(((Player) sender).getUniqueId());

        if (Bukkit.getPlayer(args[1]) != null) {
            sender.sendMessage(SharedMessages.getTargetNotOnlineMessage(args[1]));
            return;
        }

        if (!(teleportPlayer.getTeleportRequester() == TeleportPlayer.getTeleportPlayer(args[1]))) {
            sender.sendMessage(MessageHelper.getMessagePrefix() + MessageHelper.colorMessagesString("commands.teleportRequest.requestDoesntExist").replace("%player%", Bukkit.getPlayer(args[1]).getName()));
            return;
        }

        Bukkit.getPlayer(args[1]).sendMessage(MessageHelper.getMessagePrefix() + MessageHelper.colorMessagesString("commands.teleportRequest.accept.requestSender").replace("%player%", sender.getName()));
        sender.sendMessage(MessageHelper.getMessagePrefix() + MessageHelper.colorMessagesString("commands.teleportRequest.accept.requestTarget").replace("%player%", Bukkit.getPlayer(args[1]).getName()));

        teleportPlayer.removeTeleportRequester();
        TeleportPlayer.getTeleportPlayer(args[1]).runTeleport(Teleport.getTeleport("tpa"));
        Bukkit.getPlayer(args[1]).teleport(((Player) sender).getLocation());

    }

}
