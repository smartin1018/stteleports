package com.shepherdjerred.stteleports.commands;

import com.shepherdjerred.riotbase.commands.CommandInfo;
import com.shepherdjerred.riotbase.messages.AbstractParser;
import com.shepherdjerred.stteleports.actions.TeleportAction;
import com.shepherdjerred.stteleports.objects.TeleportPlayer;
import com.shepherdjerred.stteleports.objects.trackers.TeleportPlayerTracker;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class HomeCommand extends AbstractTeleportCommand {


    public HomeCommand(AbstractParser parser, TeleportPlayerTracker teleportPlayerTracker, TeleportAction teleportAction) {
        super(parser, new CommandInfo(
                "home",
                "stTeleports.home",
                "Go to a home you have set",
                "/home [name]",
                0,
                false
        ), teleportPlayerTracker, teleportAction);
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        Player player = (Player) sender;
        TeleportPlayer teleportPlayer = teleportPlayerTracker.get(player);
        teleportAction.teleport(player, teleportPlayer.getHome("default"), false);
        sender.sendMessage(parser.colorString(true, "home.success"));
    }

}
