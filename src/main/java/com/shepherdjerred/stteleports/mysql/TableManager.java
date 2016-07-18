package com.shepherdjerred.stteleports.mysql;

import com.shepherdjerred.stteleports.Main;
import com.shepherdjerred.stteleports.files.FileManager;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TableManager {

    public static void checkConnection() {
        try {
            if (HikariManager.getInstance().getConnection().isValid(30))
                Main.getInstance().getLogger().info("Connection to MySQL database was successful!");
        } catch (SQLException e) {
            Main.getInstance().getLogger().severe("Connection to MySQL database failed!");
            e.printStackTrace();
        }
    }

    public static void loadDatabase() {

        QueryManager.consumer<Boolean> checkTables = (result) -> {

            if (result)
                Main.getInstance().getLogger().info("All tables exist, loading data");
            else {

                Main.getInstance().getLogger().info("Some tables don't exist, creating them now");

                List<String> tables = new ArrayList<>();

                tables.add("CREATE TABLE IF NOT EXISTS " + HikariManager.prefix + "errors " +
                        "(error_date DATETIME DEFAULT CURRENT_TIMESTAMP, error_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP, error_uuid CHAR(36), error_text LONGTEXT, UNIQUE (error_uuid));");


                QueryHelper.getInstance().massRunUpdates(tables);

            }

            updateTables();

            // Load from MySQL

        };

        QueryManager.getInstance().checkTables(checkTables);

    }

    private static void updateTables() {

        int tableVersion = FileManager.getInstance().storage.getInt("mysqlTableVersion");
        List<String> updates = new ArrayList<>();

        switch (tableVersion) {

            case 1:

        }

        if (!updates.isEmpty()) {
            QueryHelper.getInstance().massRunUpdates(updates);
            FileManager.getInstance().storage.set("mysqlTableVersion", 3);
        }

    }


}
