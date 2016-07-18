package com.shepherdjerred.stteleports;

import com.shepherdjerred.stteleports.commands.MainExecutor;
import com.shepherdjerred.stteleports.files.ConfigHelper;
import com.shepherdjerred.stteleports.listeners.SampleListener;
import com.shepherdjerred.stteleports.metrics.MetricsLite;
import com.shepherdjerred.stteleports.mysql.ErrorManager;
import com.shepherdjerred.stteleports.mysql.HikariManager;
import com.shepherdjerred.stteleports.mysql.TableManager;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

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

        Thread.setDefaultUncaughtExceptionHandler(new ExceptionHandler());

        ConfigHelper.loadConfigs();

        HikariManager.getInstance().setupPool();
        TableManager.checkConnection();
        TableManager.loadDatabase();

        this.getCommand("stsk").setExecutor(new MainExecutor());

        getServer().getPluginManager().registerEvents(new SampleListener(), this);

        try {
            MetricsLite metrics = new MetricsLite(this);
            metrics.start();
        } catch (IOException e) {

        }

    }

    private class ExceptionHandler implements Thread.UncaughtExceptionHandler {

        @Override
        public void uncaughtException(@NotNull Thread aThread, @NotNull Throwable aThrowable) {

            aThrowable.printStackTrace();

            if (ExceptionUtils.getStackTrace(aThrowable).contains("stTeleports"))
                ErrorManager.logError(aThrowable);

        }

    }


}
