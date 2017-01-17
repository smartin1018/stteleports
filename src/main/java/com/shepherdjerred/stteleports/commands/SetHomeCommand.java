package com.shepherdjerred.stteleports.commands;

import com.shepherdjerred.riotbase.commands.AbstractCommand;
import com.shepherdjerred.riotbase.commands.CommandInfo;
import com.shepherdjerred.riotbase.messages.AbstractParser;
import com.shepherdjerred.stteleports.database.TeleportPlayerDatabaseActions;
import com.shepherdjerred.stteleports.objects.TeleportPlayer;
import com.shepherdjerred.stteleports.objects.trackers.TeleportPlayerTracker;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SetHomeCommand extends AbstractCommand {

    private final TeleportPlayerTracker teleportPlayerTracker;
    private final TeleportPlayerDatabaseActions teleportPlayerDatabaseActions;

    public SetHomeCommand(AbstractParser parser, TeleportPlayerTracker teleportPlayerTracker, TeleportPlayerDatabaseActions teleportPlayerDatabaseActions) {
        super(parser, new CommandInfo(
                "sethome",
                "stTeleports.home.set",
                "Set your home",
                "/sethome [name]",
                0,
                false
        ));
        this.teleportPlayerTracker = teleportPlayerTracker;
        this.teleportPlayerDatabaseActions = teleportPlayerDatabaseActions;
    }

    @Override
    public void execute(CommandSender sender, String[] args) {

        String home = "default";

        if (args.length > 0) {
            if (args[0].length() < 3 || args[0].length() > 16) {
                sender.sendMessage(parser.colorString(true, "sethome.invalidLength"));
                return;
            }
            home = args[0];
        }

        Player player = (Player) sender;
        TeleportPlayer teleportPlayer = teleportPlayerTracker.get(player);
        teleportPlayer.addHome(home, ((Player) sender).getLocation());
        teleportPlayerDatabaseActions.addHome(teleportPlayer, home);
        sender.sendMessage(parser.colorString(true, "sethome.success"));
    }

}
