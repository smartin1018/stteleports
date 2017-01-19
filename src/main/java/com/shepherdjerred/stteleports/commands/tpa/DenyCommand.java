package com.shepherdjerred.stteleports.commands.tpa;

import com.shepherdjerred.riotbase.commands.CommandInfo;
import com.shepherdjerred.riotbase.commands.CommandSource;
import com.shepherdjerred.stteleports.commands.AbstractTeleportCommand;
import com.shepherdjerred.stteleports.commands.registers.TeleportCommandRegister;
import com.shepherdjerred.stteleports.objects.TeleportPlayer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.Arrays;

public class DenyCommand extends AbstractTeleportCommand {

    public DenyCommand(TeleportCommandRegister teleportCommandRegister) {
        super(teleportCommandRegister, new CommandInfo(
                "deny",
                "stTeleports.tpa.deny",
                "Deny a teleport request",
                "/tpa deny <player>",
                1,
                false,
                Arrays.asList("decline")
        ));
    }

    @Override
    public void execute(CommandSource sender, String[] args) {

        Player targetPlayer = Bukkit.getPlayer(args[0]);
        Player senderPlayer = sender.getPlayer();
        TeleportPlayer senderTeleportPlayer = teleportPlayers.get(senderPlayer);

        if (targetPlayer == null) {
            sender.sendMessage(parser.colorString(true, "generic.playerNotOnline", args[0]));
            return;
        }

        if (!senderTeleportPlayer.hasRequest(targetPlayer.getUniqueId())) {
            sender.sendMessage(parser.colorString(true, "tpa.noRequest", targetPlayer.getName()));
            return;
        }

        senderTeleportPlayer.removeRequest(targetPlayer.getUniqueId());

        senderPlayer.sendMessage(parser.colorString(true, "tpa.deny.sender", targetPlayer.getName()));
        targetPlayer.sendMessage(parser.colorString(true, "tpa.deny.target", senderPlayer.getName()));
    }

}
