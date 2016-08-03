package com.shepherdjerred.stteleports.commands;

import com.shepherdjerred.stteleports.files.FileManager;
import com.shepherdjerred.stteleports.messages.MessageHelper;
import com.shepherdjerred.stteleports.messages.commands.GenericMessages;
import com.shepherdjerred.stteleports.objects.TeleportPlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SetHomeExecutor implements CommandExecutor {

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage(GenericMessages.getNoConsoleMessage());
            return true;
        }

        if (!sender.hasPermission("stTeleports.sethome")) {
            sender.sendMessage(GenericMessages.getNoPermsMessage());
            return true;
        }

        TeleportPlayer teleportPlayer = TeleportPlayer.getTeleportPlayer(((Player) sender).getUniqueId());

        teleportPlayer.setHome(((Player) sender).getLocation());
        sender.sendMessage(MessageHelper.colorMessagesString("commands.sethome.success"));

        FileManager.getInstance().storage.set("homes." + ((Player) sender).getUniqueId() + ".home.x", teleportPlayer.getHome().getX());
        FileManager.getInstance().storage.set("homes." + ((Player) sender).getUniqueId() + ".home.y", teleportPlayer.getHome().getY());
        FileManager.getInstance().storage.set("homes." + ((Player) sender).getUniqueId() + ".home.z", teleportPlayer.getHome().getZ());
        FileManager.getInstance().storage.set("homes." + ((Player) sender).getUniqueId() + ".home.yaw", teleportPlayer.getHome().getYaw());
        FileManager.getInstance().storage.set("homes." + ((Player) sender).getUniqueId() + ".home.pitch", teleportPlayer.getHome().getPitch());
        FileManager.getInstance().storage.set("homes." + ((Player) sender).getUniqueId() + ".home.world", teleportPlayer.getHome().getWorld().getUID());
        FileManager.getInstance().saveFiles(FileManager.FileName.STORAGE);

        return true;

    }


}
