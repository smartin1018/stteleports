package com.shepherdjerred.stteleports.commands;

import com.shepherdjerred.riotbase.commands.CommandInfo;
import com.shepherdjerred.riotbase.commands.CommandSource;
import com.shepherdjerred.stteleports.commands.registers.TeleportCommandRegister;
import com.shepherdjerred.stteleports.objects.Teleport;
import com.shepherdjerred.stteleports.objects.TeleportPlayer;
import com.shepherdjerred.stteleports.util.TimeToString;
import org.bukkit.entity.Player;

import java.util.Arrays;

public class ForwardCommand extends AbstractTeleportCommand {

    public ForwardCommand(TeleportCommandRegister teleportCommandRegister) {
        super(teleportCommandRegister, new CommandInfo(
                "forward",
                "stTeleports.forward",
                "Go forward in your teleport queue",
                "/forward",
                0,
                false,
                Arrays.asList("fw")
        ));
    }

    @Override
    public void execute(CommandSource sender, String[] args) {

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

        if (vaultManager.getEconomy() != null) {
            if (!vaultManager.getEconomy().has(player, Teleport.FORWARD.getCost())) {
                return;
            }
        }

        teleportActions.teleport(Teleport.FORWARD, player, teleportPlayer.getFirstLocation(), false);
        sender.sendMessage(parser.colorString(true, "forward.success"));

    }

}
