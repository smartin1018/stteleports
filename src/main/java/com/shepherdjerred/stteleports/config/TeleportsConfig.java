package com.shepherdjerred.stteleports.config;

import com.shepherdjerred.riotbase.config.IConfig;
import org.bukkit.configuration.file.FileConfiguration;

public class TeleportsConfig implements IConfig, ITeleportsConfig {

    private final FileConfiguration config;

    public TeleportsConfig(FileConfiguration config) {
        this.config = config;
    }

    public boolean isDebugEnabled() {
        return config.getBoolean("debug");
    }

    public boolean isVaultEnabled() {
        return config.getBoolean("vault");
    }

}
