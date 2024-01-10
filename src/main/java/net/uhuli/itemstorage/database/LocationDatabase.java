package net.uhuli.itemstorage.database;

import net.uhuli.itemstorage.Main;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.sql.*;
import java.util.UUID;

public class LocationDatabase {

    private final Connection connection;
    NamespacedKey UUIDKey = new NamespacedKey(Main.getInstance(), "uuid");
    NamespacedKey StorageKey = new NamespacedKey(Main.getInstance(), "storage");

    public LocationDatabase(String path) throws SQLException {
        connection = DriverManager.getConnection("jdbc:sqlite:" + path);
        try (Statement statement = connection.createStatement()) {
            statement.execute("CREATE TABLE IF NOT EXISTS itemstorages (" +
                    "location TEXT PRIMARY KEY, " +
                    "uuid TEXT NOT NULL, " +
                    "storage INTEGER NOT NULL DEFAULT 64, " +
                    "items TEXT NOT NULL DEFAULT '')");
        }
    }

    public void closeConnection() throws SQLException {
        if (connection != null && !connection.isClosed()) {
            connection.close();
        }
    }

    public void addItemstorage(Location location, ItemMeta itemMeta) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO itemstorages (location, uuid, storage) VALUES (?, ?, ?)")) {
            preparedStatement.setString(1, location.toString());
            preparedStatement.setString(2, itemMeta.getPersistentDataContainer().get(UUIDKey, PersistentDataType.STRING));
            preparedStatement.setInt(3, itemMeta.getPersistentDataContainer().get(StorageKey, PersistentDataType.INTEGER));
            preparedStatement.executeUpdate();
        }
    }

    public boolean itemstorageExists(Location location) {
        try {
            try (PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM itemstorages WHERE location = ?")) {
                preparedStatement.setString(1, location.toString());
                ResultSet resultSet = preparedStatement.executeQuery();
                return resultSet.next();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean itemstorageExists(UUID uuid) {
        try {
            try (PreparedStatement preparedStatement = connection.prepareStatement("SELECT material FROM itemstorages WHERE uuid = ?")) {
                preparedStatement.setString(1, uuid.toString());
                ResultSet resultSet = preparedStatement.executeQuery();
                return resultSet.next();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void removeItemstorage(Location location) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM itemstorages WHERE location = ?")) {
            preparedStatement.setString(1, location.toString());
            preparedStatement.executeUpdate();
        }
    }

    public Material getMaterial(Location location) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement("SELECT material FROM itemstorages WHERE location = ?")) {
            preparedStatement.setString(1, location.toString());
            ResultSet resultSet = preparedStatement.executeQuery();
            return Material.getMaterial(resultSet.getString(4));
        }
    }
}
