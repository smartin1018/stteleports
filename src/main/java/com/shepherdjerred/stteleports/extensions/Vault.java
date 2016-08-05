package com.shepherdjerred.stteleports.extensions;

import com.shepherdjerred.stteleports.Main;
import com.shepherdjerred.stteleports.messages.commands.EconomyMessages;
import org.apache.commons.lang.Validate;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.jetbrains.annotations.NotNull;

public class Vault {

    public static boolean enabled = false;
    private static net.milkbowl.vault.economy.Economy economy;

    public static boolean setupEconomy() {

        RegisteredServiceProvider<net.milkbowl.vault.economy.Economy> economyProvider = Main.getInstance().getServer().getServicesManager().getRegistration(net.milkbowl.vault.economy.Economy.class);

        if (economyProvider != null) {
            economy = economyProvider.getProvider();
            enabled = true;
        }

        return (economy != null);

    }

    public static boolean withdraw(@NotNull Player sender, @NotNull Double amount) {

        Validate.notNull(sender);
        Validate.notNull(amount);
        Validate.isTrue(amount > 0);

        if (Vault.economy.has(sender, amount)) {
            Vault.economy.withdrawPlayer(sender, amount);
            sender.sendMessage(EconomyMessages.getMoneyTakenMessage(amount));
            return true;
        } else {
            sender.sendMessage(EconomyMessages.notEnoughMoney(amount));
            return false;
        }
    }


}
