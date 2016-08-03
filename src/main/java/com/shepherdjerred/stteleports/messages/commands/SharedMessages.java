package com.shepherdjerred.stteleports.messages.commands;

import com.shepherdjerred.stteleports.messages.MessageHelper;
import org.jetbrains.annotations.NotNull;

public class SharedMessages {

    @NotNull
    public static String getTargetNotOnlineMessage(@NotNull String target) {
        return MessageHelper.getMessagePrefix() + MessageHelper.colorMessagesString("commands.shared")
                .replace("%player%", target);
    }

}
