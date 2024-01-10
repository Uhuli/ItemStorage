package net.uhuli.itemstorage;

import net.uhuli.itemstorage.command.ItemStorageCommand;
import net.uhuli.itemstorage.database.LocationDatabase;
import net.uhuli.itemstorage.listener.BlockBreakListener;
import net.uhuli.itemstorage.listener.BlockPlaceListener;
import net.uhuli.itemstorage.listener.HopperListener;
import net.uhuli.itemstorage.listener.PlayerInteractListener;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.SQLException;

public class Main extends JavaPlugin {
    public static Main instance;
    public static LocationDatabase locationDatabase;

    @Override
    public void onEnable() {
        instance = this;

        try {
            // Ensure the plugin's data folder exists
            if (!getDataFolder().exists()) {
                getDataFolder().mkdirs();
            }

            locationDatabase = new LocationDatabase(getDataFolder().getAbsolutePath() + "/itemstorage.db");
        } catch (SQLException e) {
            getLogger().severe("Failed to connect to database! " + e.getMessage());
            Bukkit.getPluginManager().disablePlugin(this);
        }

        ListenerRegistration();
        CommandRegistration();

        getLogger().info("ItemStorage Plugin enabled.");
    }

    @Override
    public void onDisable() {
        try {
            locationDatabase.closeConnection();
        } catch (SQLException e) {
            getLogger().severe("Failed to close database connection! " + e.getMessage());
        }

        getLogger().info("ItemStorage Plugin disabled.");
    }

    public void ListenerRegistration() {
        PluginManager pluginManager = Bukkit.getPluginManager();
        pluginManager.registerEvents(new PlayerInteractListener(), this);
        pluginManager.registerEvents(new BlockPlaceListener(), this);
        pluginManager.registerEvents(new BlockBreakListener(), this);
        pluginManager.registerEvents(new HopperListener(), this);
    }

    public void CommandRegistration() {
        this.getCommand("itemstorage").setExecutor(new ItemStorageCommand());
    }

    public static Main getInstance() {
        return instance;
    }

    public static LocationDatabase getLocationDatabase() {
        return locationDatabase;
    }
}