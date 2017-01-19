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

public class TpaHereCommand extends AbstractTeleportCommand {

    public TpaHereCommand(AbstractParser parser, TeleportPlayers teleportPlayers, TeleportActions teleportActions) {
        super(parser, new CommandInfo(
                "teleport",
                "stTeleports.tpahere",
                "Request that another player teleports to you",
                "/tpahere <target>",
                1,
                false
        ), teleportPlayers, teleportActions);
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

        teleportActions.sendTeleportRequest(target, destination, Teleport.TPAHERE);

        sender.sendMessage(parser.colorString(true, "tpahere.send.success", destination.getName()));
        destination.sendMessage(parser.colorString(true, "tpahere.send.destination", sender.getName()));

    }

}
