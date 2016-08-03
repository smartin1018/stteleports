package com.shepherdjerred.stteleports.messages.commands;

import com.shepherdjerred.stteleports.messages.MessageHelper;
import org.apache.commons.lang.Validate;
import org.jetbrains.annotations.NotNull;

public class GenericMessages {

    @NotNull
    public static String getNoPermsMessage() {
        return MessageHelper.getMessagePrefix() + MessageHelper.colorMessagesString("generic.noPerms");
    }

    @NotNull
    public static String getNoConsoleMessage() {
        return MessageHelper.getMessagePrefix() + MessageHelper.colorMessagesString("generic.noConsole");
    }

    @NotNull
    public static String getNoArgsMessage(@NotNull String correctArgs) {
        Validate.notNull(correctArgs);
        return MessageHelper.getMessagePrefix() + MessageHelper.colorMessagesString("generic.noArgs.correct")
                .replace("%args%", correctArgs);
    }

    @NotNull
    public static String getInvalidArgsMessage(@NotNull String givenArg, @NotNull String correctArgs) {
        Validate.notNull(givenArg);
        Validate.notNull(correctArgs);
        return MessageHelper.getMessagePrefix() + MessageHelper.colorMessagesString("generic.invalidArg.correct")
                .replace("%arg%", givenArg)
                .replace("%args%", correctArgs);
    }

}
