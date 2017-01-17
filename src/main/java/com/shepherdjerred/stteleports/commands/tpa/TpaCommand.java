package com.shepherdjerred.stteleports.commands.tpa;

import com.shepherdjerred.riotbase.commands.CommandInfo;
import com.shepherdjerred.riotbase.messages.AbstractParser;
import com.shepherdjerred.stteleports.actions.TeleportAction;
import com.shepherdjerred.stteleports.commands.AbstractTeleportCommand;
import com.shepherdjerred.stteleports.objects.Teleport;
import com.shepherdjerred.stteleports.objects.trackers.TeleportPlayerTracker;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TpaCommand extends AbstractTeleportCommand {

    public TpaCommand(AbstractParser parser, TeleportPlayerTracker teleportPlayerTracker, TeleportAction teleportAction) {
        super(parser, new CommandInfo(
                "tpa",
                "stTeleports.tpa",
                "Request to teleport to another player",
                "/tpa <destination>",
                1,
                false
        ), teleportPlayerTracker, teleportAction);
        addChildren(
                new AcceptCommand(parser, teleportPlayerTracker, teleportAction),
                new DenyCommand(parser, teleportPlayerTracker, teleportAction)
        );
    }

    @Override
    public void execute(CommandSender sender, String[] args) {

        Player target = (Player) sender;
        Player destination = Bukkit.getPlayer(args[0]);

        if (destination == null) {
            sender.sendMessage(parser.colorString(true, "generic.playerNotOnline", args[0]));
            return;
        }

        if (destination == target) {
            sender.sendMessage(parser.colorString(true, "generic.cantTargetSelf"));
            return;
        }

        teleportAction.sendTeleportRequest(target, destination, Teleport.TPA);

        sender.sendMessage(parser.colorString(true, "tpa.send.success", destination.getName()));
        destination.sendMessage(parser.colorString(true, "tpa.send.destination", sender.getName()));

    }

}
