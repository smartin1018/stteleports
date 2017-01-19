package com.shepherdjerred.stteleports.config;

import org.bukkit.configuration.file.FileConfiguration;

public class ConfigFacade {

    private final FileConfiguration config;

    public ConfigFacade(FileConfiguration config) {
        this.config = config;
    }

    public boolean isVaultEnabled() {
        return config.getBoolean("vault");
    }

}
