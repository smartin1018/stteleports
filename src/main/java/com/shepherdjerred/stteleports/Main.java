package com.shepherdjerred.stteleports;

import com.shepherdjerred.riotbase.RiotBase;
import com.shepherdjerred.riotbase.commands.CommandRegister;
import com.shepherdjerred.riotbase.listeners.ListenerRegister;
import com.shepherdjerred.stteleports.controllers.TeleportController;
import com.shepherdjerred.stteleports.commands.*;
import com.shepherdjerred.stteleports.commands.registers.TeleportCommandRegister;
import com.shepherdjerred.stteleports.config.TeleportsConfig;
import com.shepherdjerred.stteleports.database.TeleportPlayerDAO;
import com.shepherdjerred.stteleports.listeners.JoinListener;
import com.shepherdjerred.stteleports.listeners.QuitListener;
import com.shepherdjerred.stteleports.listeners.TeleportListener;
import com.shepherdjerred.stteleports.messages.Parser;
import com.shepherdjerred.stteleports.objects.trackers.TeleportPlayers;
import com.shepherdjerred.stteleports.vault.VaultManager;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.codejargon.fluentjdbc.api.FluentJdbc;
import org.codejargon.fluentjdbc.api.FluentJdbcBuilder;
import org.flywaydb.core.Flyway;

import java.util.Arrays;
import java.util.ResourceBundle;

public class Main extends RiotBase {

    private Parser parser;

    private TeleportPlayers teleportPlayers;
    private TeleportController teleportController;

    private HikariDataSource hikariDataSource;
    private FluentJdbc fluentJdbc;
    private TeleportPlayerDAO teleportPlayerDAO;

    private VaultManager vaultManager;
    private TeleportsConfig teleportsConfig;

    @Override
    public void onEnable() {
        setupConfigs();
        setupDatabase();

        // TODO Load from filesystem
        parser = new Parser(ResourceBundle.getBundle("messages"));
        teleportPlayers = new TeleportPlayers();
        vaultManager = new VaultManager(this);
        teleportController = new TeleportController(teleportPlayers, teleportPlayerDAO, vaultManager.getEconomy());

        if (teleportsConfig.isVaultEnabled()) {
            vaultManager.setupEconomy();
        }

        registerCommands();
        registerListeners();

        checkOnlinePlayers();

        startMetrics();
    }

    protected void setupConfigs() {
        copyFile(getResource("config.yml"), getDataFolder().getAbsolutePath() + "/config.yml");
        copyFile(getResource("messages.properties"), getDataFolder().getAbsolutePath() + "/messages.properties");

        teleportsConfig = new TeleportsConfig(getConfig());

        // TODO Load teleport settings from JSON
    }

    private void setupDatabase() {
        copyFile(getResource("hikari.properties"), getDataFolder().getAbsolutePath() + "/hikari.properties");
        copyFile(getResource("db/migration/V1__Initial.sql"), getDataFolder().getAbsolutePath() + "/db/migration/V1__Initial.sql");

        HikariConfig hikariConfig = new HikariConfig(getDataFolder().getAbsolutePath() + "/hikari.properties");
        hikariDataSource = new HikariDataSource(hikariConfig);

        fluentJdbc = new FluentJdbcBuilder().connectionProvider(hikariDataSource).build();

        Flyway flyway = new Flyway();
        flyway.setLocations("filesystem:" + getDataFolder().getAbsolutePath() + "/db/migration/");
        flyway.setDataSource(hikariDataSource);
        flyway.migrate();

        teleportPlayerDAO = new TeleportPlayerDAO(fluentJdbc);
    }

    private void registerCommands() {
        CommandRegister cr = new CommandRegister(parser);
        cr.addCommand(new SetHomeCommand(cr, teleportPlayers, teleportPlayerDAO));
        cr.addCommand(new DelHomeCommand(cr, teleportPlayers, teleportPlayerDAO));
        cr.register(this);
        TeleportCommandRegister tcr = new TeleportCommandRegister(parser, teleportPlayers, teleportController, vaultManager);
        tcr.addCommand(new BackwardCommand(tcr));
        tcr.addCommand(new ForwardCommand(tcr));
        tcr.addCommand(new HomeCommand(tcr));
        tcr.addCommand(new SpawnCommand(tcr));
        tcr.addCommand(new TeleportCommand(tcr));
        tcr.addCommand(new TeleportHereCommand(tcr));
        tcr.addCommand(new TeleportPositionCommand(tcr));
        tcr.register(this);
    }

    private void registerListeners() {
        new ListenerRegister(Arrays.asList(
                new JoinListener(teleportPlayers, teleportPlayerDAO),
                new QuitListener(teleportPlayers),
                new TeleportListener(teleportPlayers)
        )).register(this);
    }

    private void checkOnlinePlayers() {
        // Yeah, this is a bit hacky
        JoinListener joinListener = new JoinListener(teleportPlayers, teleportPlayerDAO);
        for (Player player : Bukkit.getOnlinePlayers()) {
            joinListener.loadPlayer(player.getUniqueId());
        }
    }

}
