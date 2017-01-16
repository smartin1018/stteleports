package com.shepherdjerred.stteleports.commands;

import com.shepherdjerred.riotbase.commands.CommandInfo;
import com.shepherdjerred.riotbase.messages.AbstractParser;
import com.shepherdjerred.stteleports.actions.TeleportAction;
import org.apache.commons.lang.StringUtils;
import org.bukkit.*;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;

public class TeleportPositionCommand extends AbstractTeleportCommand {

    public TeleportPositionCommand(AbstractParser parser, TeleportAction teleportAction) {
        super(parser, new CommandInfo(
                "tppos",
                "stTeleports.tppos",
                "Teleport to a coordinate",
                "/tppos <x> <y> <z> [world]",
                3,
                false,
                new ArrayList<>(Arrays.asList("tpp"))
        ), teleportAction);
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        Player target = (Player) sender;
        double x;
        double y;
        double z;
        World world;

        if (!StringUtils.isNumeric(args[0])) {
            sender.sendMessage(parser.colorString(true, "generic.argumentMustBeNumber", args[0]));
            return;
        }

        if (!StringUtils.isNumeric(args[1])) {
            sender.sendMessage(parser.colorString(true, "generic.argumentMustBeNumber", args[1]));
            return;
        }

        if (!StringUtils.isNumeric(args[2])) {
            sender.sendMessage(parser.colorString(true, "generic.argumentMustBeNumber", args[2]));
            return;
        }

        if (args.length > 3) {
            world = Bukkit.getWorld(args[3]);
            if (world == null) {
                sender.sendMessage(parser.colorString(true, "generic.invalidWorld", args[3]));
                return;
            }
        } else {
            world = target.getWorld();
        }

        x = Double.valueOf(args[0]);
        y = Double.valueOf(args[1]);
        z = Double.valueOf(args[2]);

        Location destination = new Location(world, x, y, z);

        teleportAction.teleport(target, destination, true);

        sender.sendMessage(parser.colorString(true, "tppos.success",
                target.getLocation().getBlockX(),
                target.getLocation().getBlockY(),
                target.getLocation().getBlockZ(),
                world.getName())
        );

    }

}
