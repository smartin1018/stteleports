package com.shepherdjerred.stteleports.commands;

import com.shepherdjerred.riotbase.commands.AbstractCommand;
import com.shepherdjerred.riotbase.commands.CommandInfo;
import com.shepherdjerred.stteleports.controllers.TeleportController;
import com.shepherdjerred.stteleports.commands.registers.TeleportCommandRegister;
import com.shepherdjerred.stteleports.objects.trackers.TeleportPlayers;
import com.shepherdjerred.stteleports.vault.VaultManager;

public abstract class AbstractTeleportCommand extends AbstractCommand {

    protected final TeleportPlayers teleportPlayers;
    protected final TeleportController teleportController;
    protected final VaultManager vaultManager;

    public AbstractTeleportCommand(TeleportCommandRegister register, CommandInfo commandInfo) {
        super(register, commandInfo);
        this.teleportPlayers = register.getTeleportPlayers();
        this.teleportController = register.getTeleportController();
        this.vaultManager = register.getVaultManager();
    }

}
