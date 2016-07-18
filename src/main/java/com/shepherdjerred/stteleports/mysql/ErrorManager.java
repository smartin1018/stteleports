package com.shepherdjerred.stteleports.mysql;

import com.shepherdjerred.stteleports.Main;
import org.apache.commons.lang.Validate;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.bukkit.Bukkit;
import org.jetbrains.annotations.NotNull;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.UUID;

public class ErrorManager {

    public static void logError(@NotNull Throwable aThrowable) {

        Validate.notNull(aThrowable);

        Bukkit.getServer().getScheduler().runTaskAsynchronously(Main.getInstance(), () -> {

            try {

                Connection connection = HikariManager.getInstance().getConnection();
                PreparedStatement statement;

                String query = "INSERT INTO " + HikariManager.prefix + "errors (error_uuid, error_text) VALUES (?, ?);";
                statement = connection.prepareStatement(query);

                statement.setString(1, String.valueOf(UUID.randomUUID()));
                statement.setString(2, ExceptionUtils.getStackTrace(aThrowable));

                QueryHelper.getInstance().runAsyncUpdate(connection, statement);

            } catch (SQLException e) {
                e.printStackTrace();
            }

        });

    }

}
