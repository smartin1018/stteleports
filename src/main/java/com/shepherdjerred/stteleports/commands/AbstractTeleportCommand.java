package com.shepherdjerred.stteleports.commands;

import com.shepherdjerred.riotbase.commands.AbstractCommand;
import com.shepherdjerred.riotbase.commands.CommandInfo;
import com.shepherdjerred.riotbase.messages.AbstractParser;
import com.shepherdjerred.stteleports.actions.TeleportActions;
import com.shepherdjerred.stteleports.objects.trackers.TeleportPlayers;

public abstract class AbstractTeleportCommand extends AbstractCommand {

    protected final TeleportPlayers teleportPlayers;
    protected final TeleportActions teleportActions;

    public AbstractTeleportCommand(AbstractParser parser, CommandInfo commandInfo, TeleportPlayers teleportPlayers, TeleportActions teleportActions) {
        super(parser, commandInfo);
        this.teleportPlayers = teleportPlayers;
        this.teleportActions = teleportActions;
    }

}
