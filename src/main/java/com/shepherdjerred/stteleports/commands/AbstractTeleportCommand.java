package com.shepherdjerred.stteleports.commands;

import com.shepherdjerred.riotbase.commands.AbstractCommand;
import com.shepherdjerred.riotbase.commands.CommandInfo;
import com.shepherdjerred.riotbase.messages.AbstractParser;
import com.shepherdjerred.stteleports.actions.TeleportAction;

public abstract class AbstractTeleportCommand extends AbstractCommand {

    protected final TeleportAction teleportAction;

    public AbstractTeleportCommand(AbstractParser parser, CommandInfo commandInfo, TeleportAction teleportAction) {
        super(parser, commandInfo);
        this.teleportAction = teleportAction;
    }

}
