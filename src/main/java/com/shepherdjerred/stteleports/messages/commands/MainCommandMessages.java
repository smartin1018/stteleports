package com.shepherdjerred.stteleports.messages.commands;

import com.shepherdjerred.stteleports.messages.MessageHelper;
import org.jetbrains.annotations.NotNull;

public class MainCommandMessages {

    @NotNull
    public static String getReloadMessage() {
        return MessageHelper.getMessagePrefix() + MessageHelper.colorMessagesString("commands.main.reload");
    }

}
