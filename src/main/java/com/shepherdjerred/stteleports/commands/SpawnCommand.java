package com.shepherdjerred.stteleports.commands;

import com.shepherdjerred.riotbase.commands.CommandInfo;
import com.shepherdjerred.riotbase.messages.AbstractParser;
import com.shepherdjerred.stteleports.actions.TeleportAction;
import com.shepherdjerred.stteleports.objects.trackers.TeleportPlayerTracker;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SpawnCommand extends AbstractTeleportCommand {

    public SpawnCommand(AbstractParser parser, TeleportPlayerTracker teleportPlayerTracker, TeleportAction teleportAction) {
        super(parser, new CommandInfo(
                "spawn",
                "stTeleports.spawn",
                "Go to the worlds spawn",
                "/spawn",
                0,
                false
        ), teleportPlayerTracker, teleportAction);
    }

    @Override
    public void execute(CommandSender sender, String[] args) {

        Player player = (Player) sender;

        teleportAction.teleport(player, player.getWorld().getSpawnLocation(), false);

        sender.sendMessage(parser.colorString(true, "spawn.success"));
    }

}
