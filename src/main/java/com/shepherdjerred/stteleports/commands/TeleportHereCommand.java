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

public class TeleportHereCommand extends AbstractTeleportCommand {

    public TeleportHereCommand(AbstractParser parser, TeleportPlayers teleportPlayers, TeleportActions teleportActions, VaultManager vaultManager) {
        super(parser, new CommandInfo(
                "tphere",
                "stTeleports.tphere",
                "Teleport another player to you",
                "/tphere <target>",
                1,
                false,
                Arrays.asList("tph", "bring")
        ), teleportPlayers, teleportActions, vaultManager);
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

        teleportActions.teleport(Teleport.TPHERE, target, destination);
        sender.sendMessage(parser.colorString(true, "teleportHere.success", target.getName()));
        target.sendMessage(parser.colorString(true, "teleportHere.success.target", sender.getName()));
    }


}
