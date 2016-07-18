package com.shepherdjerred.stteleports.commands.mainsubcommands;

import com.shepherdjerred.stteleports.messages.commands.GenericMessages;
import org.bukkit.command.CommandSender;

public class HelpSubCommand {

    public static void Executor(CommandSender sender, String[] args) {

        if (!sender.hasPermission("plugin.name.help")) {
            sender.sendMessage(GenericMessages.getNoPermsMessage());
            return;
        }

        sender.sendMessage("This command isn't ready yet");

    }

}
