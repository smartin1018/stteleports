package com.shepherdjerred.stteleports.files;

import com.shepherdjerred.stteleports.Main;
import com.shepherdjerred.stteleports.extensions.Vault;
import com.shepherdjerred.stteleports.objects.Teleport;
import org.bukkit.configuration.file.YamlConfiguration;

public class ConfigHelper {

    public static boolean debug;

    @SuppressWarnings("deprecation")
    public static void loadConfigs() {
        Main.getInstance().saveDefaultConfig();
        Main.getInstance().getConfig().setDefaults(YamlConfiguration.loadConfiguration(Main.getInstance().getResource("config.yml")));
        Main.getInstance().getConfig().options().copyDefaults(true);
        Main.getInstance().saveConfig();

        debug = Main.getInstance().getConfig().getBoolean("debug");
        FileManager.getInstance().loadFiles();

        if (Main.getInstance().getConfig().getBoolean("economy.enabled"))
            if (Vault.setupEconomy())
                Main.getInstance().getLogger().info("Vault support enabled");
            else
                Main.getInstance().getLogger().info("Error enabling economy");

        loadTeleports();
    }

    private static void loadTeleports() {
        Main.getInstance().getConfig().getConfigurationSection("teleports").getKeys(false).forEach(teleport -> new Teleport(
                teleport,
                Main.getInstance().getConfig().getBoolean("teleports." + teleport + ".enabled"),
                Main.getInstance().getConfig().getInt("teleports." + teleport + ".cooldown"),
                Main.getInstance().getConfig().getDouble("teleports." + teleport + ".cost"),
                Main.getInstance().getConfig().getDouble("teleports." + teleport + ".cooldown-multiplier"),
                Main.getInstance().getConfig().getDouble("teleports." + teleport + ".cost-multiplier")
        ));
    }

}
