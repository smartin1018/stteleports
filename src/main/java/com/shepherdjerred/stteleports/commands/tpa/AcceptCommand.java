package com.shepherdjerred.stteleports.commands.tpa;

import com.shepherdjerred.riotbase.commands.CommandInfo;
import com.shepherdjerred.riotbase.messages.AbstractParser;
import com.shepherdjerred.stteleports.actions.TeleportAction;
import com.shepherdjerred.stteleports.commands.AbstractTeleportCommand;
import com.shepherdjerred.stteleports.objects.Teleport;
import com.shepherdjerred.stteleports.objects.TeleportPlayer;
import com.shepherdjerred.stteleports.objects.trackers.TeleportPlayerTracker;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class AcceptCommand extends AbstractTeleportCommand {

    public AcceptCommand(AbstractParser parser, TeleportPlayerTracker teleportPlayerTracker, TeleportAction teleportAction) {
        super(parser, new CommandInfo(
                "accept",
                "stTeleports.tpa.accept",
                "Accept a teleport request",
                "/tpa accept <player>",
                1,
                false
        ), teleportPlayerTracker, teleportAction);
    }

    @Override
    public void execute(CommandSender sender, String[] args) {

        Player targetPlayer = Bukkit.getPlayer(args[0]);
        Player senderPlayer = (Player) sender;
        TeleportPlayer senderTeleportPlayer = teleportPlayerTracker.get(senderPlayer);

        if (targetPlayer == null) {
            sender.sendMessage(parser.colorString(true, "generic.playerNotOnline", args[0]));
            return;
        }

        if (!senderTeleportPlayer.hasRequest(targetPlayer.getUniqueId())) {
            sender.sendMessage(parser.colorString(true, "tpa.noRequest", targetPlayer.getName()));
            return;
        }

        Teleport type = senderTeleportPlayer.getTeleportType(targetPlayer.getUniqueId());
        senderTeleportPlayer.removeRequest(targetPlayer.getUniqueId());

        if (type == Teleport.TPA) {
            teleportAction.teleport(targetPlayer, senderPlayer);
        } else if (type == Teleport.TPAHERE) {
            teleportAction.teleport(senderPlayer, targetPlayer);
        } else {
            sender.sendMessage("Invalid teleport type. Please report this to an administrator");
            return;
        }

        senderPlayer.sendMessage(parser.colorString(true, "tpa.accept.sender", targetPlayer.getName()));
        targetPlayer.sendMessage(parser.colorString(true, "tpa.accept.target", senderPlayer.getName()));

    }

}
