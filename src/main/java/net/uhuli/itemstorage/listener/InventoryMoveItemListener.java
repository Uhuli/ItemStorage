package net.uhuli.itemstorage.listener;

import org.bukkit.Location;
import org.bukkit.block.Hopper;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryMoveItemEvent;

import java.util.Map;

public class InventoryMoveItemListener implements Listener {

    private final Map<Location, Hopper> hopperMap = new HopperListener().getHopperMap();

    @EventHandler
    public void onInventoryMoveItem(InventoryMoveItemEvent event) {
        if (event.getSource().getHolder() instanceof Hopper hopper) {
            if (hopperMap.containsValue(hopper)) {
            }
        }
    }
}
