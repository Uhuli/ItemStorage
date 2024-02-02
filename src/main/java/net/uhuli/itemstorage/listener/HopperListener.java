package net.uhuli.itemstorage.listener;

import net.uhuli.itemstorage.Main;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Hopper;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.HopperInventorySearchEvent;

import java.util.HashMap;
import java.util.Map;

public class HopperListener implements Listener {

    private final Map<Location, Hopper> hopperMap = new HashMap<>();

    @EventHandler
    public void onHopperSearch(HopperInventorySearchEvent event) {
        if (!event.getSearchBlock().getType().equals(Material.LODESTONE)) return;

            if (!Main.getLocationDatabase().itemstorageExists(event.getSearchBlock().getLocation())) return;

                if (!(event.getBlock().getState() instanceof Hopper hopper)) {return;}

                hopperMap.put(event.getBlock().getLocation(), hopper);
    }

    public Map<Location, Hopper> getHopperMap() {
        return hopperMap;
    }
}