package com.shepherdjerred.stteleports.commands;

import com.shepherdjerred.riotbase.commands.AbstractCommand;
import com.shepherdjerred.riotbase.commands.CommandInfo;
import com.shepherdjerred.stteleports.actions.TeleportActions;
import com.shepherdjerred.stteleports.commands.registers.TeleportCommandRegister;
import com.shepherdjerred.stteleports.objects.trackers.TeleportPlayers;
import com.shepherdjerred.stteleports.vault.VaultManager;

public abstract class AbstractTeleportCommand extends AbstractCommand {

    protected final TeleportPlayers teleportPlayers;
    protected final TeleportActions teleportActions;
    protected final VaultManager vaultManager;

    public AbstractTeleportCommand(TeleportCommandRegister register, CommandInfo commandInfo) {
        super(register, commandInfo);
        this.teleportPlayers = register.getTeleportPlayers();
        this.teleportActions = register.getTeleportActions();
        this.vaultManager = register.getVaultManager();
    }

}
