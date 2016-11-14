package com.shepherdjerred.stteleports.commands.mainsubcommands;

import com.shepherdjerred.stteleports.Main;
import com.shepherdjerred.stteleports.files.ConfigHelper;
import com.shepherdjerred.stteleports.messages.commands.GenericMessages;
import com.shepherdjerred.stteleports.messages.commands.MainCommandMessages;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ReloadSubCommand {

    public static void executor(CommandSender sender, String[] args) {

        if (!sender.hasPermission("stTeleports.admin.reload")) {
            sender.sendMessage(GenericMessages.getNoPermsMessage());
            return;
        }

        Main.getInstance().reloadConfig();
        ConfigHelper.loadConfigs();

        for (Player loopPlayer : Bukkit.getOnlinePlayers()) {
            // TODO Reload player data
        }

        sender.sendMessage(MainCommandMessages.getReloadMessage());

    }

}
