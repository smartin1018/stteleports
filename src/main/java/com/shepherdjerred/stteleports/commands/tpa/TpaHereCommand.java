package com.shepherdjerred.stteleports.commands.tpa;

import com.shepherdjerred.riotbase.commands.CommandInfo;
import com.shepherdjerred.riotbase.commands.CommandSource;
import com.shepherdjerred.stteleports.commands.AbstractTeleportCommand;
import com.shepherdjerred.stteleports.commands.registers.TeleportCommandRegister;
import com.shepherdjerred.stteleports.objects.Teleport;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class TpaHereCommand extends AbstractTeleportCommand {

    public TpaHereCommand(TeleportCommandRegister teleportCommandRegister) {
        super(teleportCommandRegister, new CommandInfo(
                "teleport",
                "stTeleports.tpahere",
                "Request that another player teleports to you",
                "/tpahere <target>",
                1,
                false
        ));
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

        teleportActions.sendTeleportRequest(target, destination, Teleport.TPAHERE);

        sender.sendMessage(parser.colorString(true, "tpahere.send.success", destination.getName()));
        destination.sendMessage(parser.colorString(true, "tpahere.send.destination", sender.getSender().getName()));

    }

}
