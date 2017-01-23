package com.shepherdjerred.stteleports.commands.tpa;

import com.shepherdjerred.riotbase.commands.CommandInfo;
import com.shepherdjerred.riotbase.commands.CommandSource;
import com.shepherdjerred.stteleports.commands.AbstractTeleportCommand;
import com.shepherdjerred.stteleports.commands.registers.TeleportCommandRegister;
import com.shepherdjerred.stteleports.objects.Teleport;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class TpaCommand extends AbstractTeleportCommand {

    public TpaCommand(TeleportCommandRegister teleportCommandRegister) {
        super(teleportCommandRegister, new CommandInfo(
                "tpa",
                "stTeleports.tpa",
                "Request to teleport to another player",
                "/tpa <destination>",
                1,
                false
        ));
        addChildren(
                new AcceptCommand(teleportCommandRegister),
                new DenyCommand(teleportCommandRegister)
        );
    }

    @Override
    public void execute(CommandSource sender, String[] args) {

        Player target = sender.getPlayer();
        Player destination = Bukkit.getPlayer(args[0]);

        if (destination == null) {
            sender.sendMessage(parser.colorString(true, "generic.playerNotOnline", args[0]));
            return;
        }

        if (destination == target) {
            sender.sendMessage(parser.colorString(true, "generic.cantTargetSelf"));
            return;
        }

        teleportController.sendTeleportRequest(target, destination, Teleport.TPA);

        sender.sendMessage(parser.colorString(true, "tpa.send.success", destination.getName()));
        destination.sendMessage(parser.colorString(true, "tpa.send.destination", sender.getSender().getName()));

    }

}
