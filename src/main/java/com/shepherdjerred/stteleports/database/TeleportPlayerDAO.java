package com.shepherdjerred.stteleports.database;

import com.shepherdjerred.stteleports.objects.TeleportPlayer;
import com.zaxxer.hikari.HikariDataSource;
import org.bukkit.Bukkit;
import org.bukkit.Location;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class TeleportPlayerDAO {

    private final HikariDataSource hikariDataSource;

    public TeleportPlayerDAO(HikariDataSource hikariDataSource) {
        this.hikariDataSource = hikariDataSource;
    }

    public void insert(TeleportPlayer player) {
        String sql = "INSERT INTO players (?,?,?,?,?,?)";

        try (Connection conn = hikariDataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, player.getUuid().toString());
            ps.setLong(2, player.getNextAvaliableTeleport());
            ps.setDouble(3, player.getCooldownMultiplier());
            ps.setDouble(4, player.getCostMultiplier());
            ps.setDouble(5, player.getCooldownMultiplierModifier());
            ps.setDouble(6, player.getCostMultiplierModifier());

            ps.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void addHome(TeleportPlayer player, String home) {

        String sql = "INSERT INTO player_homes (?,?,?,?,?,?,?)";

        Location location = player.getHome(home);

        try (Connection conn = hikariDataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, player.getUuid().toString());
            ps.setString(2, home);
            ps.setString(3, location.getWorld().getUID().toString());
            ps.setInt(4, location.getBlockX());
            ps.setInt(5, location.getBlockY());
            ps.setInt(6, location.getBlockZ());
            ps.setFloat(7, location.getYaw());
            ps.setFloat(8, location.getPitch());

            ps.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void deleteHome(TeleportPlayer player, String home) {

        String sql = "DELETE FROM player_homes WHERE player_uuid = ? AND home_name = ?";

        try (Connection conn = hikariDataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, player.getUuid().toString());
            ps.setString(2, home);

            ps.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void loadHomes(TeleportPlayer player) {

        String sql = "SELECT * FROM player_homes WHERE player_uuid = ?";

        try (Connection conn = hikariDataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, player.getUuid().toString());

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Location location = new Location(
                        Bukkit.getWorld(UUID.fromString(rs.getString("world"))),
                        rs.getInt("x"),
                        rs.getInt("y"),
                        rs.getInt("z"),
                        rs.getFloat("yaw"),
                        rs.getFloat("pitch")
                );

                player.addHome(rs.getString("home_name"), location);
            }

            rs.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public TeleportPlayer load(UUID uuid) {

        String sql = "SELECT * FROM players WHERE player_uuid = ?";
        TeleportPlayer teleportPlayer = null;

        try (Connection conn = hikariDataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, uuid.toString());

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                teleportPlayer = new TeleportPlayer(
                        UUID.fromString(rs.getString("player_uuid")),
                        rs.getLong("next_teleport"),
                        rs.getDouble("cooldown_multiplier"),
                        rs.getDouble("cost_multiplier"),
                        rs.getDouble("cooldown_multiplier_modifier"),
                        rs.getDouble("cost_multiplier_modifier")
                );
            }

            rs.close();

            loadHomes(teleportPlayer);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return teleportPlayer;

    }

}
