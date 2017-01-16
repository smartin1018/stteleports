package com.shepherdjerred.stteleports.commands;

import com.shepherdjerred.riotbase.commands.CommandInfo;
import com.shepherdjerred.riotbase.messages.AbstractParser;
import com.shepherdjerred.stteleports.actions.TeleportAction;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;

public class TeleportHereCommand extends AbstractTeleportCommand {

    public TeleportHereCommand(AbstractParser parser, TeleportAction teleportAction) {
        super(parser, new CommandInfo(
                "tphere",
                "stTeleports.tphere",
                "Teleport another player to you",
                "/tphere <target>",
                1,
                false,
                new ArrayList<>(Arrays.asList("tph, bring"))
        ), teleportAction);
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        Player target = Bukkit.getPlayer(args[0]);
        Player destination = (Player) sender;

        if (target == null) {
            sender.sendMessage(parser.colorString(true, "generic.playerNotOnline", args[0]));
            return;
        }

        if (destination == target) {
            sender.sendMessage(parser.colorString(true, "generic.cantTargetSelf"));
            return;
        }

        teleportAction.teleport(target, destination);
        sender.sendMessage(parser.colorString(true, "teleport.success", destination.getName()));
    }


}
