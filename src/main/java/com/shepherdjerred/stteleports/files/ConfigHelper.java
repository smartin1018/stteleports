package com.shepherdjerred.stteleports.files;

import com.shepherdjerred.stteleports.Main;
import com.shepherdjerred.stteleports.objects.Teleport;
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
