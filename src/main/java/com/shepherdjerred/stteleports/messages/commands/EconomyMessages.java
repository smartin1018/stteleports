package com.shepherdjerred.stteleports.messages.commands;

import com.shepherdjerred.stteleports.messages.MessageHelper;

public class EconomyMessages {
    public static String getMoneyTakenMessage(double amount) {
        return MessageHelper.getMessagePrefix() + MessageHelper.colorMessagesString("economy.moneyTaken")
                .replace("%amount%", String.valueOf(amount));
    }

    public static String notEnoughMoney(double amount) {
        return MessageHelper.getMessagePrefix() + MessageHelper.colorMessagesString("economy.notEnoughMoney")
                .replace("%amount%", String.valueOf(amount));
    }

    public static String economyNotEnabled() {
        return MessageHelper.getMessagePrefix() + MessageHelper.colorMessagesString("economy.notEnabled");
    }
}
