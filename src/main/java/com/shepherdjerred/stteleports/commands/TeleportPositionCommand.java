package com.shepherdjerred.stteleports.commands;

import com.shepherdjerred.riotbase.commands.CommandInfo;
import com.shepherdjerred.riotbase.messages.AbstractParser;
import com.shepherdjerred.stteleports.actions.TeleportActions;
import com.shepherdjerred.stteleports.objects.Teleport;
import com.shepherdjerred.stteleports.objects.TeleportPlayer;
import com.shepherdjerred.stteleports.objects.trackers.TeleportPlayers;
import com.shepherdjerred.stteleports.util.TimeToString;
import com.shepherdjerred.stteleports.vault.VaultManager;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;

public class TeleportPositionCommand extends AbstractTeleportCommand {

    public TeleportPositionCommand(AbstractParser parser, TeleportPlayers teleportPlayers, TeleportActions teleportActions) {
        super(parser, new CommandInfo(
                "tppos",
                "stTeleports.tppos",
                "Teleport to a coordinate",
                "/tppos <x> <y> <z> [world]",
                3,
                false,
                Arrays.asList("tpp")
        ), teleportPlayers, teleportActions);
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        Player target = (Player) sender;
        TeleportPlayer teleportPlayer = teleportPlayers.get(target);
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

        if (!teleportPlayer.isCooldownOver()) {
            sender.sendMessage(parser.colorString(true, "generic.cooldownActive", TimeToString.convertLong(teleportPlayer.getCooldown())));
            return;
        }

        if (VaultManager.INSTANCE.getEconomy() != null) {
            if (!VaultManager.INSTANCE.getEconomy().has(target, Teleport.BACKWARD.getCost())) {
                return;
            }
        }

        x = Double.valueOf(args[0]);
        y = Double.valueOf(args[1]);
        z = Double.valueOf(args[2]);

        Location destination = new Location(world, x, y, z);

        teleportActions.teleport(Teleport.TPPOS, target, destination, true);

        sender.sendMessage(parser.colorString(true, "tppos.success",
                target.getLocation().getBlockX(),
                target.getLocation().getBlockY(),
                target.getLocation().getBlockZ(),
                world.getName())
        );

    }

}
