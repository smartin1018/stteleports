package com.shepherdjerred.stteleports;

import com.shepherdjerred.stteleports.commands.*;
import com.shepherdjerred.stteleports.files.ConfigHelper;
import com.shepherdjerred.stteleports.listeners.JoinListener;
import com.shepherdjerred.stteleports.listeners.QuitListener;
import com.shepherdjerred.stteleports.metrics.MetricsLite;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;

public class Main extends JavaPlugin {

    public static boolean debug;

    private static Main instance;

    public Main() {
        instance = this;
    }

    public static Main getInstance() {
        return instance;
    }

    @Override
    public void onEnable() {

        ConfigHelper.loadConfigs();

        this.getCommand("stteleports").setExecutor(new MainExecutor());

        this.getCommand("teleport").setExecutor(new TPExecutor());
        this.getCommand("tpa").setExecutor(new TPAExecutor());
        this.getCommand("tpaaccept").setExecutor(new TPAAcceptExecutor());
        this.getCommand("tpadecline").setExecutor(new TPADeclineExecutor());
        this.getCommand("sethome").setExecutor(new SetHomeExecutor());
        this.getCommand("home").setExecutor(new HomeExecutor());

        getServer().getPluginManager().registerEvents(new JoinListener(), this);
        getServer().getPluginManager().registerEvents(new QuitListener(), this);

        try {
            MetricsLite metrics = new MetricsLite(this);
            metrics.start();
        } catch (IOException e) {

        }

    }

}
