package com.shepherdjerred.stteleports.commands;

import com.shepherdjerred.riotbase.commands.AbstractCommand;
import com.shepherdjerred.riotbase.commands.CommandInfo;
import com.shepherdjerred.riotbase.messages.AbstractParser;
import com.shepherdjerred.stteleports.actions.TeleportActions;
import com.shepherdjerred.stteleports.objects.trackers.TeleportPlayerTracker;

public abstract class AbstractTeleportCommand extends AbstractCommand {

    protected final TeleportPlayerTracker teleportPlayerTracker;
    protected final TeleportActions teleportActions;

    public AbstractTeleportCommand(AbstractParser parser, CommandInfo commandInfo, TeleportPlayerTracker teleportPlayerTracker, TeleportActions teleportActions) {
        super(parser, commandInfo);
        this.teleportPlayerTracker = teleportPlayerTracker;
        this.teleportActions = teleportActions;
    }

}
