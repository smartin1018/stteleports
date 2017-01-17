package com.shepherdjerred.stteleports.commands;

import com.shepherdjerred.riotbase.commands.AbstractCommand;
import com.shepherdjerred.riotbase.commands.CommandInfo;
import com.shepherdjerred.riotbase.messages.AbstractParser;
import com.shepherdjerred.stteleports.actions.TeleportAction;
import com.shepherdjerred.stteleports.objects.trackers.TeleportPlayerTracker;

public abstract class AbstractTeleportCommand extends AbstractCommand {

    protected final TeleportPlayerTracker teleportPlayerTracker;
    protected final TeleportAction teleportAction;

    public AbstractTeleportCommand(AbstractParser parser, CommandInfo commandInfo, TeleportPlayerTracker teleportPlayerTracker, TeleportAction teleportAction) {
        super(parser, commandInfo);
        this.teleportPlayerTracker = teleportPlayerTracker;
        this.teleportAction = teleportAction;
    }

}
