package com.shepherdjerred.stteleports.commands;

import com.shepherdjerred.riotbase.commands.CommandInfo;
import com.shepherdjerred.riotbase.commands.CommandSource;
import com.shepherdjerred.stteleports.commands.registers.TeleportCommandRegister;
import com.shepherdjerred.stteleports.objects.Teleport;
import com.shepherdjerred.stteleports.objects.TeleportPlayer;
import com.shepherdjerred.stteleports.util.TimeToString;
import org.bukkit.entity.Player;

public class SpawnCommand extends AbstractTeleportCommand {

    public SpawnCommand(TeleportCommandRegister teleportCommandRegister) {
        super(teleportCommandRegister, new CommandInfo(
                "spawn",
                "stTeleports.spawn",
                "Go to the worlds spawn",
                "/spawn",
                0,
                false
        ));
    }

    @Override
    public void execute(CommandSource sender, String[] args) {

        Player player = (Player) sender;
        TeleportPlayer teleportPlayer = teleportPlayers.get(player);

        if (!teleportPlayer.isCooldownOver()) {
            sender.sendMessage(parser.colorString(true, "generic.cooldownActive", TimeToString.convertLong(teleportPlayer.getCooldown())));
            return;
        }

        if (vaultManager.getEconomy() != null) {
            if (!vaultManager.getEconomy().has(player, Teleport.SPAWN.getCost())) {
                return;
            }
        }

        teleportActions.teleport(Teleport.SPAWN, player, player.getWorld().getSpawnLocation(), false);

        sender.sendMessage(parser.colorString(true, "spawn.success"));
    }

}
