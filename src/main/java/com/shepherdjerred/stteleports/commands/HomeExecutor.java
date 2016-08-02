package com.shepherdjerred.stteleports.commands;

import com.shepherdjerred.stteleports.objects.Teleport;
import com.shepherdjerred.stteleports.objects.TeleportPlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Calendar;

public class HomeExecutor implements CommandExecutor {

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage("Players only");
            return true;
        }

        if (!sender.hasPermission("stTeleports.home")) {
            sender.sendMessage("No permission");
            return true;
        }

        TeleportPlayer teleportPlayer = TeleportPlayer.getTeleportPlayer(((Player) sender).getUniqueId());
        Teleport teleport = Teleport.getTeleport("home");

        if (teleportPlayer.getCooldown() < Calendar.getInstance().getTimeInMillis() / 1000L) {
            sender.sendMessage("Still cooling down");
            sender.sendMessage("Curent cooldown: " + teleportPlayer.getCooldown());
            return true;
        }

        ((Player) sender).teleport(teleportPlayer.getHome());
        sender.sendMessage("Teleported home");

        int cooldown = teleport.getCooldown()
                + (int) (teleport.getCooldownMultiplier() * teleportPlayer.getCooldownMultiplier());

        teleportPlayer.setCooldownMultiplier(teleportPlayer.getCooldownMultiplier() + teleport.getCooldownMultiplier());
        sender.sendMessage("New cooldown multiplier: " + teleportPlayer.getCooldownMultiplier());

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.SECOND, cooldown);

        teleportPlayer.setCooldown(calendar.getTimeInMillis() / 1000L);
        sender.sendMessage("New cooldown: " + teleportPlayer.getCooldown());

        return true;

    }

}
