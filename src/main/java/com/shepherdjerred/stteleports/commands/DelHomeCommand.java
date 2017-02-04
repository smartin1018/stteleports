package com.shepherdjerred.stteleports.commands;

import com.shepherdjerred.riotbase.commands.AbstractCommand;
import com.shepherdjerred.riotbase.commands.CommandInfo;
import com.shepherdjerred.riotbase.commands.CommandRegister;
import com.shepherdjerred.riotbase.commands.SpigotCommandSource;
import com.shepherdjerred.stteleports.database.TeleportPlayerDAO;
import com.shepherdjerred.stteleports.objects.TeleportPlayer;
import com.shepherdjerred.stteleports.objects.trackers.TeleportPlayers;
import org.bukkit.entity.Player;

public class DelHomeCommand extends AbstractCommand {

    private final TeleportPlayers teleportPlayers;
    private final TeleportPlayerDAO teleportPlayerDAO;

    public DelHomeCommand(CommandRegister commandRegister, TeleportPlayers teleportPlayers, TeleportPlayerDAO teleportPlayerDAO) {
        super(commandRegister, new CommandInfo(
                "delhome",
                "stTeleports.home.delete",
                "Delete your home",
                "/delhome [name]",
                0,
                false
        ));
        this.teleportPlayers = teleportPlayers;
        this.teleportPlayerDAO = teleportPlayerDAO;
    }

    @Override
    public void execute(SpigotCommandSource sender, String[] args) {

        Player player = sender.getPlayer();
        TeleportPlayer teleportPlayer = teleportPlayers.get(player);

        String home = "default";

        if (args.length > 1) {
            home = args[0];
        }

        if (teleportPlayer.getHome(home) == null) {
            sender.sendMessage(parser.colorString(true, "home.invalidName", home));
            return;
        }

        teleportPlayer.removeHome(home);
        teleportPlayerDAO.deleteHome(teleportPlayer, home);
        sender.sendMessage(parser.colorString(true, "deletehome.success", home));
    }
}
