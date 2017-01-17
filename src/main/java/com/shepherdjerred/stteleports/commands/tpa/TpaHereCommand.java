package com.shepherdjerred.stteleports.commands.tpa;

import com.shepherdjerred.riotbase.commands.CommandInfo;
import com.shepherdjerred.riotbase.messages.AbstractParser;
import com.shepherdjerred.stteleports.actions.TeleportAction;
import com.shepherdjerred.stteleports.commands.AbstractTeleportCommand;
import com.shepherdjerred.stteleports.objects.trackers.TeleportPlayerTracker;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.Arrays;

public class TpaHereCommand extends AbstractTeleportCommand {

    public TpaHereCommand(AbstractParser parser, TeleportPlayerTracker teleportPlayerTracker, TeleportAction teleportAction) {
        super(parser, new CommandInfo(
                "teleport",
                "stTeleports.teleport",
                "Teleport to another player",
                "/teleport [target] <destination>",
                1,
                false,
                new ArrayList<>(Arrays.asList("tp"))
        ), teleportPlayerTracker, teleportAction);
    }

    @Override
    public void execute(CommandSender sender, String[] args) {

    }

}
