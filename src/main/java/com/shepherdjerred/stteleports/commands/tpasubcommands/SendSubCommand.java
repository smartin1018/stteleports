package com.shepherdjerred.stteleports.commands.tpasubcommands;

import com.shepherdjerred.stteleports.Main;
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

        if (args.length < 2) {
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

        if (Bukkit.getPlayer(args[1]) == null) {
            player.sendMessage(SharedMessages.getTargetNotOnlineMessage(args[1]));
            return;
        }

        if (Bukkit.getPlayer(args[1]) != player) {
            player.sendMessage(MessageHelper.getMessagePrefix() + MessageHelper.colorMessagesString("commands.teleportRequest.send.cantRequestSelf"));
            return;
        }

        TeleportPlayer.getTeleportPlayer(args[1]).setTeleportRequester(teleportPlayer);
        player.sendMessage(MessageHelper.getMessagePrefix() + MessageHelper.colorMessagesString("commands.teleportRequest.send.requestSender"));
        Bukkit.getPlayer(args[1]).sendMessage(MessageHelper.getMessagePrefix() + MessageHelper.colorMessagesString("commands.teleportRequest.send.requestTarget"));

        Main.getInstance().getServer().getScheduler().scheduleSyncDelayedTask(Main.getInstance(), () -> {
            if (TeleportPlayer.getTeleportPlayer(args[1]).getTeleportRequester() == teleportPlayer) {
                TeleportPlayer.getTeleportPlayer(args[1]).setTeleportRequester(null);
                player.sendMessage(MessageHelper.getMessagePrefix() + MessageHelper.colorMessagesString("commands.teleportRequest.send.expire.sender").replace("%player%", Bukkit.getPlayer(args[1]).getName()));
                Bukkit.getPlayer(args[1]).sendMessage(MessageHelper.getMessagePrefix() + MessageHelper.colorMessagesString("commands.teleportRequest.send.expire.target").replace("%player%", teleportPlayer.getName()));
            }
        }, Main.getInstance().getConfig().getInt("tpa.requestDuration") * 20);
    }

}
