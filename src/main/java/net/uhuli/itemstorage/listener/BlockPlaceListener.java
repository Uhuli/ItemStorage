package net.uhuli.itemstorage.listener;

import net.uhuli.itemstorage.Main;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.persistence.PersistentDataType;

import java.sql.SQLException;

public class BlockPlaceListener implements Listener {
    NamespacedKey isItemStorageKey = new NamespacedKey(Main.getInstance(), "isItemStorage");

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        if (event.getBlockPlaced().getType().equals(Material.LODESTONE)) {
            if (!event.getItemInHand().getItemMeta().getPersistentDataContainer().has(isItemStorageKey, PersistentDataType.BOOLEAN)) {
                return;
            }
            if (event.getItemInHand().getItemMeta().getPersistentDataContainer().get(isItemStorageKey, PersistentDataType.BOOLEAN)) {
                try {
                    Main.getLocationDatabase().addItemstorage(event.getBlockPlaced().getLocation(), event.getItemInHand().getItemMeta());
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}
