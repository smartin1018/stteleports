package com.shepherdjerred.stteleports.commands;

import com.shepherdjerred.riotbase.commands.CommandInfo;
import com.shepherdjerred.riotbase.messages.AbstractParser;
import com.shepherdjerred.stteleports.actions.TeleportActions;
import com.shepherdjerred.stteleports.objects.Teleport;
import com.shepherdjerred.stteleports.objects.TeleportPlayer;
import com.shepherdjerred.stteleports.objects.trackers.TeleportPlayers;
import com.shepherdjerred.stteleports.util.TimeToString;
import com.shepherdjerred.stteleports.vault.VaultManager;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SpawnCommand extends AbstractTeleportCommand {

    public SpawnCommand(AbstractParser parser, TeleportPlayers teleportPlayers, TeleportActions teleportActions) {
        super(parser, new CommandInfo(
                "spawn",
                "stTeleports.spawn",
                "Go to the worlds spawn",
                "/spawn",
                0,
                false
        ), teleportPlayers, teleportActions);
    }

    @Override
    public void execute(CommandSender sender, String[] args) {

        Player player = (Player) sender;
        TeleportPlayer teleportPlayer = teleportPlayers.get(player);

        if (!teleportPlayer.isCooldownOver()) {
            sender.sendMessage(parser.colorString(true, "generic.cooldownActive", TimeToString.convertLong(teleportPlayer.getCooldown())));
            return;
        }

        if (VaultManager.INSTANCE.getEconomy() != null) {
            if (!VaultManager.INSTANCE.getEconomy().has(player, Teleport.SPAWN.getCost())) {
                return;
            }
        }

        teleportActions.teleport(Teleport.SPAWN, player, player.getWorld().getSpawnLocation(), false);

        sender.sendMessage(parser.colorString(true, "spawn.success"));
    }

}
