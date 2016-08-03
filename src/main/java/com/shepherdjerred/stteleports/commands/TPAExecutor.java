package com.shepherdjerred.stteleports.commands;

import com.shepherdjerred.stteleports.commands.tpasubcommands.AcceptSubCommand;
import com.shepherdjerred.stteleports.commands.tpasubcommands.DeclineSubCommand;
import com.shepherdjerred.stteleports.commands.tpasubcommands.SendSubCommand;
import com.shepherdjerred.stteleports.messages.commands.GenericMessages;
import org.apache.commons.lang3.EnumUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class TPAExecutor implements CommandExecutor {


    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (args.length < 1) {
            sender.sendMessage(GenericMessages.getNoArgsMessage("<send, accept, decline>"));
            return true;
        }

        if (!EnumUtils.isValidEnum(MainExecutor.SubCommands.class, args[0].toUpperCase())) {
            sender.sendMessage(GenericMessages.getInvalidArgsMessage(args[0], "<send, accept, decline>"));
            return true;
        }

        switch (SubCommands.valueOf(args[0].toUpperCase())) {
            case SEND:
                SendSubCommand.Executor(sender, args);
                return true;
            case ACCEPT:
                AcceptSubCommand.Executor(sender, args);
                return true;
            case DECLINE:
                DeclineSubCommand.Executor(sender, args);
                return true;
        }

        return true;

    }

    enum SubCommands {
        SEND, ACCEPT, DECLINE
    }

}