package com.shepherdjerred.stteleports.commands;

import com.shepherdjerred.stteleports.commands.mainsubcommands.HelpSubCommand;
import com.shepherdjerred.stteleports.commands.mainsubcommands.ReloadSubCommand;
import com.shepherdjerred.stteleports.messages.commands.GenericMessages;
import org.apache.commons.lang3.EnumUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class MainExecutor implements CommandExecutor {

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (args.length < 1) {
            sender.sendMessage(GenericMessages.getNoArgsMessage("<help>"));
            return true;
        }

        if (!EnumUtils.isValidEnum(SubCommands.class, args[0].toUpperCase())) {
            sender.sendMessage(GenericMessages.getInvalidArgsMessage(args[0], "<help>"));
            return true;
        }

        switch (SubCommands.valueOf(args[0].toUpperCase())) {
            case HELP:
                HelpSubCommand.executor(sender, args);
                return true;
            case RELOAD:
                ReloadSubCommand.executor(sender, args);
                return true;
        }

        return true;

    }

    enum SubCommands {

        HELP, RELOAD

    }

}