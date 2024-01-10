package net.uhuli.itemstorage.listener;

import dev.triumphteam.gui.builder.item.ItemBuilder;
import dev.triumphteam.gui.guis.Gui;
import dev.triumphteam.gui.guis.PaginatedGui;
import net.kyori.adventure.text.Component;
import net.uhuli.itemstorage.Main;
import net.uhuli.itemstorage.util.Message;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Hopper;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.HopperInventorySearchEvent;

public class HopperListener implements Listener {

    @EventHandler
    public void onHopperSearch(HopperInventorySearchEvent e) {
        if (e.getSearchBlock().getType().equals(Material.LODESTONE)) {
            if (Main.getLocationDatabase().itemstorageExists(e.getSearchBlock().getLocation())) {
                if (!(e.getBlock().getState() instanceof Hopper hopper)) return;

                PaginatedGui gui = Gui.paginated()
                        .title(Message.miniMessage("            <gray><b><i:false>ItemStorage</b></gray>"))
                        .rows(6)
                        .pageSize(45)
                        .create();

                gui.setItem(6, 3, ItemBuilder.from(Material.PAPER)
                        .name(Message.miniMessage("Previous"))
                        .asGuiItem(event -> {
                            event.setCancelled(true);
                            gui.previous();
                        }));

                gui.setItem(6, 7, ItemBuilder.from(Material.PAPER)
                        .name(Message.miniMessage("Next"))
                        .asGuiItem(event -> {
                            event.setCancelled(true);
                            gui.next();
                        }));

                e.setInventory(gui.getInventory());

                gui.open(Bukkit.getPlayer("Uhuli"));
            }
        }
    }
}
