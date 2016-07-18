package com.shepherdjerred.stteleports.mysql;

import com.shepherdjerred.stteleports.Main;
import org.apache.commons.lang.Validate;
import org.jetbrains.annotations.NotNull;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class QueryManager {

    private static QueryManager instance;

    private QueryManager() {
        instance = this;
    }

    public static QueryManager getInstance() {
        if (instance == null) {
            instance = new QueryManager();
        }
        return instance;
    }

    // MySQL check if tables exist
    void checkTables(@NotNull consumer<Boolean> consumer) {

        Validate.notNull(consumer);

        Connection connection = null;
        ResultSet result = null;
        List<String> tables = new ArrayList<>();

        try {

            connection = HikariManager.getInstance().getConnection();
            String[] types = {"TABLE"};

            result = connection.getMetaData().getTables(null, null, "%", types);

            while (result.next()) {
                if (Main.debug)
                    Main.getInstance().getLogger().info("Table exists: " + result.getString("TABLE_NAME"));
                tables.add(result.getString("TABLE_NAME"));
            }

            consumer.accept(tables.containsAll(Arrays.asList(
                    HikariManager.prefix + "errors"
            )));

        } catch (SQLException e) {
            e.printStackTrace();
            ErrorManager.logError(e);
        } finally {
            HikariManager.getInstance().close(connection, null, result);
        }
    }

    public interface consumer<T> {
        void accept(T result);
    }

}
