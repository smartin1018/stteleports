package com.shepherdjerred.stteleports.commands;

import com.shepherdjerred.riotbase.commands.CommandInfo;
import com.shepherdjerred.riotbase.messages.AbstractParser;
import com.shepherdjerred.stteleports.actions.TeleportAction;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;

public class TeleportCommand extends AbstractTeleportCommand {

    public TeleportCommand(AbstractParser parser, TeleportAction teleportAction) {
        super(parser, new CommandInfo(
                "teleport",
                "stTeleports.teleport",
                "Teleport to another player",
                "/teleport [target] <destination>",
                1,
                false,
                new ArrayList<>(Arrays.asList("tp"))
        ), teleportAction);
    }

    @Override
    public void execute(CommandSender sender, String[] args) {

        Player target;
        Player destination;

        if (args.length > 1) {
            target = Bukkit.getPlayer(args[0]);
            destination = Bukkit.getPlayer(args[1]);
            if (target == null) {
                sender.sendMessage(parser.colorString(true, "generic.playerNotOnline", args[0]));
                return;
            }
            if (destination == null) {
                sender.sendMessage(parser.colorString(true, "generic.playerNotOnline", args[1]));
                return;
            }
        } else {
            target = (Player) sender;
            destination = Bukkit.getPlayer(args[0]);

            if (destination == null) {
                sender.sendMessage(parser.colorString(true, "generic.playerNotOnline", args[0]));
                return;
            }
        }

        if (destination == target) {
            sender.sendMessage(parser.colorString(true, "generic.cantTargetSelf"));
            return;
        }

        teleportAction.teleport(target, destination);

        if (destination.getLocation().getBlock().getType() != Material.AIR) {

        }

        if (args.length > 1) {
            sender.sendMessage(parser.colorString(true, "teleport.success.withTarget", target.getName(), destination.getName()));
            target.sendMessage(parser.colorString(true, "teleport.success.teleported", destination.getName(), sender.getName()));
        } else {
            sender.sendMessage(parser.colorString(true, "teleport.success", destination.getName()));
        }

        sender.sendMessage(parser.colorString(true, "teleport.success.teleportedTo", target.getName()));

    }

}
