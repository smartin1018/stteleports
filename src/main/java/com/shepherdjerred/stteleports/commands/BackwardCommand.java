package com.shepherdjerred.stteleports.commands;

import com.shepherdjerred.riotbase.commands.CommandInfo;
import com.shepherdjerred.riotbase.messages.AbstractParser;
import com.shepherdjerred.stteleports.actions.TeleportActions;
import com.shepherdjerred.stteleports.objects.Teleport;
import com.shepherdjerred.stteleports.objects.TeleportPlayer;
import com.shepherdjerred.stteleports.objects.trackers.TeleportPlayerTracker;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;

public class BackwardCommand extends AbstractTeleportCommand {

    public BackwardCommand(AbstractParser parser, TeleportPlayerTracker teleportPlayerTracker, TeleportActions teleportActions) {
        super(parser, new CommandInfo(
                "backward",
                "stTeleports.backward",
                "Go backward in your teleport queue",
                "/backward",
                0,
                false,
                Arrays.asList("back", "return")
        ), teleportPlayerTracker, teleportActions);
    }

    @Override
    public void execute(CommandSender sender, String[] args) {

        Player player = (Player) sender;
        TeleportPlayer teleportPlayer = teleportPlayerTracker.get(player);

        if (teleportPlayer.getLastLocation() == null) {
            sender.sendMessage(parser.colorString(true, "player.emptyQueue"));
            return;
        }

        teleportActions.teleport(Teleport.BACKWARD, player, teleportPlayer.getFirstLocation(), false);
        sender.sendMessage(parser.colorString(true, "backward.success"));

    }

}
