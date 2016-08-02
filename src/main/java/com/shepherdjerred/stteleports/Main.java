package com.shepherdjerred.stteleports;

import com.shepherdjerred.stteleports.commands.MainExecutor;
import com.shepherdjerred.stteleports.files.ConfigHelper;
import com.shepherdjerred.stteleports.listeners.SampleListener;
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

        this.getCommand("teleport").setExecutor(new MainExecutor());

        getServer().getPluginManager().registerEvents(new SampleListener(), this);

        try {
            MetricsLite metrics = new MetricsLite(this);
            metrics.start();
        } catch (IOException e) {

        }

    }

}
