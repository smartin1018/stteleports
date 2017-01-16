package com.shepherdjerred.stteleports;

import com.shepherdjerred.riotbase.RiotBase;
import com.shepherdjerred.stteleports.actions.TeleportAction;
import com.shepherdjerred.stteleports.commands.TeleportCommand;
import com.shepherdjerred.stteleports.commands.TeleportHereCommand;
import com.shepherdjerred.stteleports.commands.TeleportPositionCommand;
import com.shepherdjerred.stteleports.database.TeleportPlayerDatabaseActions;
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

    private final TeleportAction teleportAction = new TeleportAction();
    private final TeleportPlayerTracker teleportPlayerTracker = new TeleportPlayerTracker();
    private TeleportPlayerDatabaseActions teleportPlayerQueries;

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

        teleportPlayerQueries = new TeleportPlayerDatabaseActions(hikariDataSource);
    }

    private void registerCommands() {
        new TeleportCommand(parser, teleportAction).register(this);
        new TeleportHereCommand(parser, teleportAction).register(this);
        new TeleportPositionCommand(parser, teleportAction).register(this);
    }

    private void registerListeners() {
        new JoinListener(teleportPlayerTracker, teleportPlayerQueries).register(this);
        new QuitListener(teleportPlayerTracker).register(this);
        new TeleportListener(teleportPlayerTracker).register(this);
    }

    private void checkOnlinePlayers() {
        for (Player player : Bukkit.getOnlinePlayers()) {
        }
    }

}
