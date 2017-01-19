package com.shepherdjerred.stteleports.commands.tpa;

import com.shepherdjerred.riotbase.commands.CommandInfo;
import com.shepherdjerred.riotbase.messages.AbstractParser;
import com.shepherdjerred.stteleports.actions.TeleportActions;
import com.shepherdjerred.stteleports.commands.AbstractTeleportCommand;
import com.shepherdjerred.stteleports.objects.Teleport;
import com.shepherdjerred.stteleports.objects.trackers.TeleportPlayers;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TpaCommand extends AbstractTeleportCommand {

    public TpaCommand(AbstractParser parser, TeleportPlayers teleportPlayers, TeleportActions teleportActions) {
        super(parser, new CommandInfo(
                "tpa",
                "stTeleports.tpa",
                "Request to teleport to another player",
                "/tpa <destination>",
                1,
                false
        ), teleportPlayers, teleportActions);
        addChildren(
                new AcceptCommand(parser, teleportPlayers, teleportActions),
                new DenyCommand(parser, teleportPlayers, teleportActions)
        );
    }

    @Override
    public void execute(CommandSender sender, String[] args) {

        Player target = (Player) sender;
        Player destination = Bukkit.getPlayer(args[0]);

        if (destination == null) {
            sender.sendMessage(parser.colorString(true, "generic.playerNotOnline", args[0]));
            return;
        }

        if (destination == target) {
            sender.sendMessage(parser.colorString(true, "generic.cantTargetSelf"));
            return;
        }

        teleportActions.sendTeleportRequest(target, destination, Teleport.TPA);

        sender.sendMessage(parser.colorString(true, "tpa.send.success", destination.getName()));
        destination.sendMessage(parser.colorString(true, "tpa.send.destination", sender.getName()));

    }

}
