package com.shepherdjerred.stteleports.commands.mainsubcommands;

import com.shepherdjerred.stteleports.Main;
import com.shepherdjerred.stteleports.files.ConfigHelper;
import com.shepherdjerred.stteleports.messages.commands.GenericMessages;
import com.shepherdjerred.stteleports.messages.commands.MainCommandMessages;
import com.shepherdjerred.stteleports.mysql.TableManager;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ReloadSubCommand {

    public static void Executor(CommandSender sender, String[] args) {

        if (!sender.hasPermission("stTowns.reload")) {
            sender.sendMessage(GenericMessages.getNoPermsMessage());
            return;
        }

        Main.getInstance().reloadConfig();
        ConfigHelper.loadConfigs();

        TableManager.checkConnection();

        TableManager.loadDatabase();

        for (Player loopPlayer : Bukkit.getOnlinePlayers()) {
            // TODO Reload player data
        }

        sender.sendMessage(MainCommandMessages.getReloadMessage());

    }

}
