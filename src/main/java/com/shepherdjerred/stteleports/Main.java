package com.shepherdjerred.stteleports;

import com.shepherdjerred.riotbase.RiotBase;
import com.shepherdjerred.stteleports.actions.TeleportActions;
import com.shepherdjerred.stteleports.commands.*;
import com.shepherdjerred.stteleports.commands.tpa.TpaCommand;
import com.shepherdjerred.stteleports.commands.tpa.TpaHereCommand;
import com.shepherdjerred.stteleports.database.TeleportPlayerDAO;
import com.shepherdjerred.stteleports.listeners.JoinListener;
import com.shepherdjerred.stteleports.listeners.QuitListener;
import com.shepherdjerred.stteleports.listeners.TeleportListener;
import com.shepherdjerred.stteleports.messages.Parser;
import com.shepherdjerred.stteleports.objects.trackers.TeleportPlayerTracker;
import com.shepherdjerred.stteleports.vault.VaultManager;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.flywaydb.core.Flyway;

import java.util.ResourceBundle;

public class Main extends RiotBase {

    private final Parser parser = new Parser(ResourceBundle.getBundle("messages"));

    private HikariDataSource hikariDataSource;

    private final VaultManager vaultManager = VaultManager.INSTANCE;

    private final TeleportPlayerTracker teleportPlayerTracker = new TeleportPlayerTracker();
    private final TeleportActions teleportActions = new TeleportActions(teleportPlayerTracker, vaultManager.getEconomy());
    private TeleportPlayerDAO teleportPlayerDAO;

    private boolean vaultEnabled = false;

    @Override
    public void onEnable() {
        setupConfigs();
        setupDatabase();

        if (vaultEnabled) {
            setupVault();
        }

        super.onEnable();

        registerCommands();
        registerListeners();

        checkOnlinePlayers();
    }

    @Override
    protected void setupConfigs() {
        super.setupConfigs();
        vaultEnabled = getConfig().getBoolean("vault.enabled");
        // TODO Load teleport settings from JSON
    }

    private void setupVault() {
        vaultManager.setupEconomy(this);
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
        new TeleportCommand(parser, teleportPlayerTracker, teleportActions).register(this);
        new TeleportHereCommand(parser, teleportPlayerTracker, teleportActions).register(this);
        new TeleportPositionCommand(parser, teleportPlayerTracker, teleportActions).register(this);
        new SetHomeCommand(parser, teleportPlayerTracker, teleportPlayerDAO).register(this);
        new HomeCommand(parser, teleportPlayerTracker, teleportActions).register(this);
        new DelHomeCommand(parser, teleportPlayerTracker, teleportPlayerDAO).register(this);
        new SpawnCommand(parser, teleportPlayerTracker, teleportActions).register(this);
        new ForwardCommand(parser, teleportPlayerTracker, teleportActions).register(this);
        new BackwardCommand(parser, teleportPlayerTracker, teleportActions).register(this);
        new TpaCommand(parser, teleportPlayerTracker, teleportActions).register(this);
        new TpaHereCommand(parser, teleportPlayerTracker, teleportActions).register(this);
    }

    private void registerListeners() {
        new JoinListener(teleportPlayerTracker, teleportPlayerDAO).register(this);
        new QuitListener(teleportPlayerTracker).register(this);
        new TeleportListener(teleportPlayerTracker).register(this);
    }

    private void checkOnlinePlayers() {
        // Yeah, this is a bit hacky
        JoinListener joinListener = new JoinListener(teleportPlayerTracker, teleportPlayerDAO);
        for (Player player : Bukkit.getOnlinePlayers()) {
            joinListener.loadPlayer(player);
        }
    }

}
