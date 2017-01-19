package com.shepherdjerred.stteleports.commands;

import com.shepherdjerred.riotbase.commands.AbstractCommand;
import com.shepherdjerred.riotbase.commands.CommandInfo;
import com.shepherdjerred.riotbase.messages.AbstractParser;
import com.shepherdjerred.stteleports.database.TeleportPlayerDAO;
import com.shepherdjerred.stteleports.objects.TeleportPlayer;
import com.shepherdjerred.stteleports.objects.trackers.TeleportPlayers;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class DelHomeCommand extends AbstractCommand {

    private final TeleportPlayers teleportPlayers;
    private final TeleportPlayerDAO teleportPlayerDAO;

    public DelHomeCommand(AbstractParser parser, TeleportPlayers teleportPlayers, TeleportPlayerDAO teleportPlayerDAO) {
        super(parser, new CommandInfo(
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
    public void execute(CommandSender sender, String[] args) {

        Player player = (Player) sender;
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
