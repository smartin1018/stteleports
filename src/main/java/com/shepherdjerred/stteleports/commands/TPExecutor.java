package com.shepherdjerred.stteleports.commands;

import com.shepherdjerred.stteleports.messages.commands.GenericMessages;
import com.shepherdjerred.stteleports.objects.Teleport;
import com.shepherdjerred.stteleports.objects.TeleportPlayer;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Calendar;

public class TPExecutor implements CommandExecutor {

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage("Players only");
            return true;
        }

        if (!sender.hasPermission("stTeleports.teleport")) {
            sender.sendMessage("No permission");
            return true;
        }

        if (args.length < 1) {
            sender.sendMessage(GenericMessages.getNoArgsMessage("<target1> [target2]"));
            return true;
        }

        Teleport teleport = Teleport.getTeleport("tp");
        TeleportPlayer teleportPlayer = TeleportPlayer.getTeleportPlayer(((Player) sender).getUniqueId());
        boolean success = false;

        if (teleportPlayer.getCooldown() < Calendar.getInstance().getTimeInMillis() / 1000L) {
            sender.sendMessage("Still cooling down");
            sender.sendMessage("Curent cooldown: " + teleportPlayer.getCooldown());
        }

        if (args.length < 2) {
            if (Bukkit.getPlayer(args[1]) != null) {
                ((Player) sender).teleport(Bukkit.getPlayer(args[1]));
                success = true;
            } else
                sender.sendMessage(args[1] + " is not online");
        } else {
            if (Bukkit.getPlayer(args[1]) != null)
                if (Bukkit.getPlayer(args[2]) != null) {
                    (Bukkit.getPlayer(args[2])).teleport(Bukkit.getPlayer(args[1]));
                    success = true;
                } else
                    sender.sendMessage(args[2] + " is not online");
            else
                sender.sendMessage(args[1] + " is not online");
        }

        if (success) {
            int cooldown = teleport.getCooldown()
                    + (int) (teleport.getCooldownMultiplier() * teleportPlayer.getCooldownMultiplier());

            teleportPlayer.setCooldownMultiplier(teleportPlayer.getCooldownMultiplier() + teleport.getCooldownMultiplier());
            sender.sendMessage("New cooldown multiplier: " + teleportPlayer.getCooldownMultiplier());

            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.SECOND, cooldown);

            teleportPlayer.setCooldown(calendar.getTimeInMillis() / 1000L);
            sender.sendMessage("New cooldown: " + teleportPlayer.getCooldown());
        }

        return true;

    }


}
