package com.shepherdjerred.stteleports.commands;

import com.shepherdjerred.riotbase.commands.CommandInfo;
import com.shepherdjerred.riotbase.messages.AbstractParser;
import com.shepherdjerred.stteleports.actions.TeleportActions;
import com.shepherdjerred.stteleports.objects.Teleport;
import com.shepherdjerred.stteleports.objects.TeleportPlayer;
import com.shepherdjerred.stteleports.objects.trackers.TeleportPlayers;
import com.shepherdjerred.stteleports.util.TimeToString;
import com.shepherdjerred.stteleports.vault.VaultManager;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;

public class ForwardCommand extends AbstractTeleportCommand {

    public ForwardCommand(AbstractParser parser, TeleportPlayers teleportPlayers, TeleportActions teleportActions) {
        super(parser, new CommandInfo(
                "forward",
                "stTeleports.forward",
                "Go forward in your teleport queue",
                "/forward",
                0,
                false,
                Arrays.asList("fw")
        ), teleportPlayers, teleportActions);
    }

    @Override
    public void execute(CommandSender sender, String[] args) {

        Player player = (Player) sender;
        TeleportPlayer teleportPlayer = teleportPlayers.get(player);

        if (teleportPlayer.getFirstLocation() == null) {
            sender.sendMessage(parser.colorString(true, "player.emptyQueue"));
            return;
        }

        if (!teleportPlayer.isCooldownOver()) {
            sender.sendMessage(parser.colorString(true, "generic.cooldownActive", TimeToString.convertLong(teleportPlayer.getCooldown())));
            return;
        }

        if (VaultManager.INSTANCE.getEconomy() != null) {
            if (!VaultManager.INSTANCE.getEconomy().has(player, Teleport.FORWARD.getCost())) {
                return;
            }
        }

        teleportActions.teleport(Teleport.FORWARD, player, teleportPlayer.getFirstLocation(), false);
        sender.sendMessage(parser.colorString(true, "forward.success"));

    }

}
