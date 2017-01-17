package com.shepherdjerred.stteleports.commands;

import com.shepherdjerred.riotbase.commands.CommandInfo;
import com.shepherdjerred.riotbase.messages.AbstractParser;
import com.shepherdjerred.stteleports.actions.TeleportActions;
import com.shepherdjerred.stteleports.objects.Teleport;
import com.shepherdjerred.stteleports.objects.TeleportPlayer;
import com.shepherdjerred.stteleports.objects.trackers.TeleportPlayerTracker;
import com.shepherdjerred.stteleports.util.TimeToString;
import com.shepherdjerred.stteleports.vault.VaultManager;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class HomeCommand extends AbstractTeleportCommand {


    public HomeCommand(AbstractParser parser, TeleportPlayerTracker teleportPlayerTracker, TeleportActions teleportActions) {
        super(parser, new CommandInfo(
                "home",
                "stTeleports.home",
                "Go to a home you have set",
                "/home [name]",
                0,
                false
        ), teleportPlayerTracker, teleportActions);
    }

    @Override
    public void execute(CommandSender sender, String[] args) {

        Player player = (Player) sender;
        TeleportPlayer teleportPlayer = teleportPlayerTracker.get(player);
        String home = "default";

        if (args.length > 0) {
            home = args[0];
        }

        if (teleportPlayer.getHome(home) == null) {
            sender.sendMessage(parser.colorString(true, "home.invalidName", home));
            return;
        }

        if (!teleportPlayer.isCooldownOver()) {
            sender.sendMessage(parser.colorString(true, "generic.cooldownActive", TimeToString.convertLong(teleportPlayer.getCooldown())));
            return;
        }

        if (VaultManager.INSTANCE.getEconomy() != null) {
            if (!VaultManager.INSTANCE.getEconomy().has(player, Teleport.HOME.getCost())) {
                return;
            }
        }

        teleportActions.teleport(Teleport.HOME, player, teleportPlayer.getHome(home), false);
        
        sender.sendMessage(parser.colorString(true, "home.success"));
    }

}
