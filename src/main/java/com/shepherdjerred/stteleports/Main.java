package com.shepherdjerred.stteleports;

import com.shepherdjerred.riotbase.RiotBase;
import com.shepherdjerred.stteleports.actions.TeleportAction;
import com.shepherdjerred.stteleports.commands.*;
import com.shepherdjerred.stteleports.commands.tpa.TpaCommand;
import com.shepherdjerred.stteleports.commands.tpa.TpaHereCommand;
import com.shepherdjerred.stteleports.database.TeleportPlayerDAO;
import com.shepherdjerred.stteleports.listeners.JoinListener;
import com.shepherdjerred.stteleports.listeners.QuitListener;
import com.shepherdjerred.stteleports.listeners.TeleportListener;
import com.shepherdjerred.stteleports.messages.Parser;
import com.shepherdjerred.stteleports.objects.trackers.TeleportPlayerTracker;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.flywaydb.core.Flyway;

import java.util.ResourceBundle;

public class Main extends RiotBase {

    private final Parser parser = new Parser(ResourceBundle.getBundle("messages"));

    private HikariDataSource hikariDataSource;

    private final TeleportPlayerTracker teleportPlayerTracker = new TeleportPlayerTracker();
    private final TeleportAction teleportAction = new TeleportAction(teleportPlayerTracker);
    private TeleportPlayerDAO teleportPlayerDAO;

    @Override
    public void onEnable() {
        setupConfigs();
        setupDatabase();

        super.onEnable();

        registerCommands();
        registerListeners();

    }

    @Override
    protected void setupConfigs() {
        super.setupConfigs();
        // Load teleport settings from JSON
    }

    private void setupDatabase() {
        saveResource("hikari.properties", false);
        saveResource("db/migration/V1__Initial.sql", true);

        HikariConfig hikariConfig = new HikariConfig(getDataFolder().getAbsolutePath() + "/hikari.properties");
        hikariDataSource = new HikariDataSource(hikariConfig);

        Flyway flyway = new Flyway();
        flyway.setLocations("filesystem:" + getDataFolder().getAbsolutePath() + "/db/migration/");
        flyway.setDataSource(hikariDataSource);
        flyway.migrate();

        teleportPlayerDAO = new TeleportPlayerDAO(hikariDataSource);
    }

    private void registerCommands() {
        new TeleportCommand(parser, teleportPlayerTracker, teleportAction).register(this);
        new TeleportHereCommand(parser, teleportPlayerTracker, teleportAction).register(this);
        new TeleportPositionCommand(parser, teleportPlayerTracker, teleportAction).register(this);
        new SetHomeCommand(parser, teleportPlayerTracker, teleportPlayerDAO).register(this);
        new HomeCommand(parser, teleportPlayerTracker, teleportAction).register(this);
        new DelHomeCommand(parser, teleportPlayerTracker, teleportPlayerDAO).register(this);
        new SpawnCommand(parser, teleportPlayerTracker, teleportAction).register(this);
        new ForwardCommand(parser, teleportPlayerTracker, teleportAction).register(this);
        new BackwardCommand(parser, teleportPlayerTracker, teleportAction).register(this);
        new TpaCommand(parser, teleportPlayerTracker, teleportAction).register(this);
        new TpaHereCommand(parser, teleportPlayerTracker, teleportAction).register(this);
    }

    private void registerListeners() {
        new JoinListener(teleportPlayerTracker, teleportPlayerDAO).register(this);
        new QuitListener(teleportPlayerTracker).register(this);
        new TeleportListener(teleportPlayerTracker).register(this);
    }

    private void checkOnlinePlayers() {
        for (Player player : Bukkit.getOnlinePlayers()) {
        }
    }

}
