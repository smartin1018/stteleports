package com.shepherdjerred.stteleports.commands.tpa;

import com.shepherdjerred.riotbase.commands.CommandInfo;
import com.shepherdjerred.riotbase.messages.AbstractParser;
import com.shepherdjerred.stteleports.actions.TeleportAction;
import com.shepherdjerred.stteleports.commands.AbstractTeleportCommand;
import com.shepherdjerred.stteleports.objects.trackers.TeleportPlayerTracker;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.Arrays;

public class TpaCommand extends AbstractTeleportCommand {

    public TpaCommand(AbstractParser parser, TeleportPlayerTracker teleportPlayerTracker, TeleportAction teleportAction) {
        super(parser, new CommandInfo(
                "tpa",
                "stTeleports.tpa",
                "Teleport to another player",
                "/teleport [target] <destination>",
                1,
                false,
                new ArrayList<>(Arrays.asList("tp"))
        ), teleportPlayerTracker, teleportAction);
    }

    @Override
    public void execute(CommandSender sender, String[] args) {

        if (Bukkit.getPlayer(args[0]) == null) {

            return;
        }

    }

}
