package com.shepherdjerred.stteleports.commands;

import com.shepherdjerred.riotbase.commands.CommandInfo;
import com.shepherdjerred.riotbase.messages.AbstractParser;
import com.shepherdjerred.stteleports.actions.TeleportActions;
import com.shepherdjerred.stteleports.objects.Teleport;
import com.shepherdjerred.stteleports.objects.TeleportPlayer;
import com.shepherdjerred.stteleports.objects.trackers.TeleportPlayers;
import com.shepherdjerred.stteleports.util.TimeToString;
import com.shepherdjerred.stteleports.vault.VaultManager;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;

public class TeleportCommand extends AbstractTeleportCommand {

    public TeleportCommand(AbstractParser parser, TeleportPlayers teleportPlayers, TeleportActions teleportActions, VaultManager vaultManager) {
        super(parser, new CommandInfo(
                "teleport",
                "stTeleports.teleport",
                "Teleport to another player",
                "/teleport [target] <destination>",
                1,
                false,
                Arrays.asList("tp")
        ), teleportPlayers, teleportActions, vaultManager);
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

        TeleportPlayer teleportPlayer = teleportPlayers.get(target);

        if (!teleportPlayer.isCooldownOver()) {
            sender.sendMessage(parser.colorString(true, "generic.cooldownActive", TimeToString.convertLong(teleportPlayer.getCooldown())));
            return;
        }

        if (vaultManager.getEconomy() != null) {
            if (!vaultManager.getEconomy().has(target, Teleport.BACKWARD.getCost())) {
                return;
            }
        }

        teleportActions.teleport(Teleport.TELEPORT, target, destination);

        if (args.length > 1) {
            sender.sendMessage(parser.colorString(true, "teleport.success.withTarget", target.getName(), destination.getName()));
            target.sendMessage(parser.colorString(true, "teleport.success.teleported", destination.getName(), sender.getName()));
        } else {
            sender.sendMessage(parser.colorString(true, "teleport.success", destination.getName()));
        }

        sender.sendMessage(parser.colorString(true, "teleport.success.teleportedTo", target.getName()));

    }

}
