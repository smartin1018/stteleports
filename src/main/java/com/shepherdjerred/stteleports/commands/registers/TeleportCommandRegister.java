package com.shepherdjerred.stteleports.commands.registers;

import com.shepherdjerred.riotbase.commands.CommandRegister;
import com.shepherdjerred.stteleports.actions.TeleportActions;
import com.shepherdjerred.stteleports.messages.Parser;
import com.shepherdjerred.stteleports.objects.trackers.TeleportPlayers;
import com.shepherdjerred.stteleports.vault.VaultManager;

public class TeleportCommandRegister extends CommandRegister {

    private final TeleportPlayers teleportPlayers;
    private final TeleportActions teleportActions;
    private final VaultManager vaultManager;

    public TeleportCommandRegister(Parser parser, TeleportPlayers teleportPlayers, TeleportActions teleportActions, VaultManager vaultManager) {
        super(parser);
        this.teleportPlayers = teleportPlayers;
        this.teleportActions = teleportActions;
        this.vaultManager = vaultManager;
    }

    public TeleportPlayers getTeleportPlayers() {
        return teleportPlayers;
    }

    public TeleportActions getTeleportActions() {
        return teleportActions;
    }

    public VaultManager getVaultManager() {
        return vaultManager;
    }
}
