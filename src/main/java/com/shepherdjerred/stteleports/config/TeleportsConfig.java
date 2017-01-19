package com.shepherdjerred.stteleports.config;

import com.shepherdjerred.riotbase.config.AbstractConfig;
import org.apache.commons.configuration2.Configuration;

public class TeleportsConfig extends AbstractConfig {

    public TeleportsConfig(Configuration config) {
        super(config);
    }

    public boolean isVaultEnabled() {
        return config.getBoolean("vault");
    }

}
