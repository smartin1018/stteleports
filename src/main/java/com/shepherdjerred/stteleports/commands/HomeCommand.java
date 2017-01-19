package com.shepherdjerred.stteleports.commands;

import com.shepherdjerred.riotbase.commands.CommandInfo;
import com.shepherdjerred.riotbase.commands.CommandSource;
import com.shepherdjerred.stteleports.commands.registers.TeleportCommandRegister;
import com.shepherdjerred.stteleports.objects.Teleport;
import com.shepherdjerred.stteleports.objects.TeleportPlayer;
import com.shepherdjerred.stteleports.util.TimeToString;
import org.bukkit.entity.Player;

public class HomeCommand extends AbstractTeleportCommand {


    public HomeCommand(TeleportCommandRegister teleportCommandRegister) {
        super(teleportCommandRegister, new CommandInfo(
                "home",
                "stTeleports.home",
                "Go to a home you have set",
                "/home [name]",
                0,
                false
        ));
    }

    @Override
    public void execute(CommandSource sender, String[] args) {

        Player player = sender.getPlayer();
        TeleportPlayer teleportPlayer = teleportPlayers.get(player);
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

        if (vaultManager.getEconomy() != null) {
            if (!vaultManager.getEconomy().has(player, Teleport.HOME.getCost())) {
                return;
            }
        }

        teleportActions.teleport(Teleport.HOME, player, teleportPlayer.getHome(home), false);
        
        sender.sendMessage(parser.colorString(true, "home.success"));
    }

}
