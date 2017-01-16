package com.shepherdjerred.stteleports;

import com.shepherdjerred.riotbase.RiotBase;
import com.shepherdjerred.stteleports.actions.TeleportAction;
import com.shepherdjerred.stteleports.commands.TeleportCommand;
import com.shepherdjerred.stteleports.commands.TeleportHereCommand;
import com.shepherdjerred.stteleports.commands.TeleportPositionCommand;
import com.shepherdjerred.stteleports.listeners.JoinListener;
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

    private final TeleportAction teleportAction = new TeleportAction();
    private final TeleportPlayerTracker teleportPlayerTracker = new TeleportPlayerTracker();

    private HikariDataSource hikariDataSource;

    private boolean databaseEnabled = false;

    @Override
    public void onEnable() {
        setupConfigs();

        // Setup database
        if (databaseEnabled) {
            saveResource("hikari.properties", false);
            saveResource("db/migration/V1__Initial.sql", true);
            HikariConfig hikariConfig = new HikariConfig(getDataFolder().getAbsolutePath() + "/hikari.properties");
            hikariDataSource = new HikariDataSource(hikariConfig);
            Flyway flyway = new Flyway();
            flyway.setLocations("filesystem:" + getDataFolder().getAbsolutePath() + "/db/migration/");
            flyway.setDataSource(hikariDataSource);
            flyway.migrate();
        }

        super.onEnable();

        registerCommands();
        registerListeners();

    }

    @Override
    protected void setupConfigs() {
        super.setupConfigs();
        databaseEnabled = getConfig().getBoolean("database.enabled");
        // Load teleport settings
    }

    private void registerCommands() {
        new TeleportCommand(parser, teleportAction).register(this);
        new TeleportHereCommand(parser, teleportAction).register(this);
        new TeleportPositionCommand(parser, teleportAction).register(this);
    }

    private void registerListeners() {
        new JoinListener(teleportPlayerTracker).register(this);
        new TeleportListener(teleportPlayerTracker).register(this);
    }

    private void checkOnlinePlayers() {
        for (Player player : Bukkit.getOnlinePlayers()) {
        }
    }

}
