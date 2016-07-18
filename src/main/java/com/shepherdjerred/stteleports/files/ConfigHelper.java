package com.shepherdjerred.stteleports.files;

import com.shepherdjerred.stteleports.Main;
import com.shepherdjerred.stteleports.mysql.HikariManager;
import org.bukkit.configuration.file.YamlConfiguration;

public class ConfigHelper {

    @SuppressWarnings("deprecation")
    public static void loadConfigs() {
        Main.getInstance().saveDefaultConfig();
        Main.getInstance().getConfig().setDefaults(YamlConfiguration.loadConfiguration(Main.getInstance().getResource("config.yml")));
        Main.getInstance().getConfig().options().copyDefaults(true);
        Main.getInstance().saveConfig();

        Main.debug = Main.getInstance().getConfig().getBoolean("debug");
        FileManager.getInstance().loadFiles();
        ConfigHelper.setMysqlVariables();
    }

    private static void setMysqlVariables() {
        HikariManager.host = Main.getInstance().getConfig().getString("mysql.hostname");
        HikariManager.port = Main.getInstance().getConfig().getString("mysql.port");
        HikariManager.database = Main.getInstance().getConfig().getString("mysql.database");
        HikariManager.username = Main.getInstance().getConfig().getString("mysql.username");
        HikariManager.password = Main.getInstance().getConfig().getString("mysql.password");
        HikariManager.prefix = Main.getInstance().getConfig().getString("mysql.prefix");
        HikariManager.connectionTimeout = Main.getInstance().getConfig().getLong("mysql.pool.connectionTimeout");
        HikariManager.idleTimeout = Main.getInstance().getConfig().getLong("mysql.pool.idleTimeout");
        HikariManager.min = Main.getInstance().getConfig().getInt("mysql.pool.minIdleConnections");
        HikariManager.max = Main.getInstance().getConfig().getInt("mysql.pool.maxConnections");
    }

}
