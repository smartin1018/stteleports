package com.shepherdjerred.stteleports.vault;

import net.milkbowl.vault.economy.Economy;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

public enum VaultManager {
    INSTANCE;

    private Economy economy;

    public void setupEconomy(JavaPlugin plugin) {
        if (economy == null) {
            RegisteredServiceProvider<Economy> economyProvider = plugin.getServer().getServicesManager().getRegistration(net.milkbowl.vault.economy.Economy.class);
            if (economyProvider != null) {
                economy = economyProvider.getProvider();
            }
        }
    }

    public Economy getEconomy() {
        return economy;
    }

}
