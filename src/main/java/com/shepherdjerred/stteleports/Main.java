package com.shepherdjerred.stteleports;

import com.shepherdjerred.riotbase.RiotBase;
import com.shepherdjerred.stteleports.actions.TeleportAction;
import com.shepherdjerred.stteleports.commands.TeleportCommand;
import com.shepherdjerred.stteleports.commands.TeleportHereCommand;
import com.shepherdjerred.stteleports.commands.TeleportPositionCommand;
import com.shepherdjerred.stteleports.messages.Parser;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ResourceBundle;

public class Main extends RiotBase {

    private final Parser parser = new Parser(ResourceBundle.getBundle("messages"));

    private final TeleportAction teleportAction = new TeleportAction();

    @Override
    public void onEnable() {
        setupConfigs();

        // Setup database

        super.onEnable();

        registerCommands();
        registerListeners();

    }

    @Override
    protected void setupConfigs() {
        super.setupConfigs();
        // Load teleport settings
    }

    private void registerCommands() {
        new TeleportCommand(parser, teleportAction).register(this);
        new TeleportHereCommand(parser, teleportAction).register(this);
        new TeleportPositionCommand(parser, teleportAction).register(this);
    }

    private void registerListeners() {

    }

    private void checkOnlinePlayers() {
        for (Player player : Bukkit.getOnlinePlayers()) {
        }
    }

}
