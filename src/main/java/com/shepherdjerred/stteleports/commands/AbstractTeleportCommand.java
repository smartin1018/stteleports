package com.shepherdjerred.stteleports.commands;

import com.shepherdjerred.riotbase.commands.AbstractCommand;
import com.shepherdjerred.riotbase.commands.CommandInfo;
import com.shepherdjerred.riotbase.messages.AbstractParser;
import com.shepherdjerred.stteleports.actions.TeleportActions;
import com.shepherdjerred.stteleports.objects.trackers.TeleportPlayers;
import com.shepherdjerred.stteleports.vault.VaultManager;

public abstract class AbstractTeleportCommand extends AbstractCommand {

    protected final TeleportPlayers teleportPlayers;
    protected final TeleportActions teleportActions;
    protected final VaultManager vaultManager;

    public AbstractTeleportCommand(AbstractParser parser, CommandInfo commandInfo, TeleportPlayers teleportPlayers, TeleportActions teleportActions, VaultManager vaultManager) {
        super(parser, commandInfo);
        this.teleportPlayers = teleportPlayers;
        this.teleportActions = teleportActions;
        this.vaultManager = vaultManager;
    }

}
