package com.shepherdjerred.stteleports.commands.mainsubcommands;

import com.shepherdjerred.stteleports.messages.commands.GenericMessages;
import org.bukkit.command.CommandSender;

public class HelpSubCommand {

    public static void executor(CommandSender sender, String[] args) {

        if (!sender.hasPermission("stTeleports.help")) {
            sender.sendMessage(GenericMessages.getNoPermsMessage());
            return;
        }

        sender.sendMessage("This command isn't ready yet");

    }

}
