package com.shepherdjerred.stteleports.commands;

import com.shepherdjerred.riotbase.commands.CommandInfo;
import com.shepherdjerred.riotbase.commands.CommandSource;
import com.shepherdjerred.stteleports.commands.registers.TeleportCommandRegister;
import com.shepherdjerred.stteleports.objects.Teleport;
import com.shepherdjerred.stteleports.objects.TeleportPlayer;
import com.shepherdjerred.stteleports.util.TimeToString;
import org.bukkit.entity.Player;

import java.util.Arrays;

public class BackwardCommand extends AbstractTeleportCommand {

    public BackwardCommand(TeleportCommandRegister teleportCommandRegister) {
        super(teleportCommandRegister, new CommandInfo(
                "backward",
                "stTeleports.backward",
                "Go backward in your teleport queue",
                "/backward",
                0,
                false,
                Arrays.asList("back", "return")
        ));
    }

    @Override
    public void execute(CommandSource sender, String[] args) {

        Player player = (Player) sender;
        TeleportPlayer teleportPlayer = teleportPlayers.get(player);

        if (teleportPlayer.getLastLocation() == null) {
            sender.sendMessage(parser.colorString(true, "player.emptyQueue"));
            return;
        }

        if (!teleportPlayer.isCooldownOver()) {
            sender.sendMessage(parser.colorString(true, "generic.cooldownActive", TimeToString.convertLong(teleportPlayer.getCooldown())));
            return;
        }

        if (vaultManager.getEconomy() != null) {
            if (!vaultManager.getEconomy().has(player, Teleport.BACKWARD.getCost())) {
                return;
            }
        }

        teleportActions.teleport(Teleport.BACKWARD, player, teleportPlayer.getFirstLocation(), false);
        sender.sendMessage(parser.colorString(true, "backward.success"));

    }

}
