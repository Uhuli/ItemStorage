package net.uhuli.itemstorage.listener;

import com.destroystokyo.paper.event.block.BlockDestroyEvent;
import com.destroystokyo.paper.event.entity.CreeperIgniteEvent;
import io.papermc.paper.event.block.BlockBreakBlockEvent;
import net.uhuli.itemstorage.Main;
import net.uhuli.itemstorage.inventory.ItemstorageItem;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockExplodeEvent;
import org.bukkit.event.block.BlockFadeEvent;
import org.bukkit.event.entity.EntityChangeBlockEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.SQLException;

public class BlockBreakListener implements Listener {
    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        if (execute(event.getBlock())) {
            event.setCancelled(true);
            JavaPlugin.getPlugin(Main.class).getLogger().info("BlockBreakEvent");
        }
    }

    @EventHandler
    public void onBlockDestroy(BlockDestroyEvent event) {
        if (execute(event.getBlock())) {
            event.setCancelled(true);
            JavaPlugin.getPlugin(Main.class).getLogger().info("BlockDestroyEvent");
        }
    }

    @EventHandler
    public void onBlockBreakBlock(BlockBreakBlockEvent event) {
        if (execute(event.getBlock())) {
            JavaPlugin.getPlugin(Main.class).getLogger().info("BlockBreakBlockEvent");
        }
    }

    @EventHandler
    public void onEntityChangeBlock(EntityChangeBlockEvent event) {
        if (execute(event.getBlock())) {
            event.setCancelled(true);
            JavaPlugin.getPlugin(Main.class).getLogger().info("EntityChangeBlockEvent");
        }
    }

    @EventHandler
    public void onBlockExplode(BlockExplodeEvent event) {
        if (execute(event.getBlock())) {
            event.setCancelled(true);
            JavaPlugin.getPlugin(Main.class).getLogger().info("BlockExplodeEvent");
        }
    }

    @EventHandler
    public void onBlockFade(BlockFadeEvent event) {
        if (execute(event.getBlock())) {
            event.setCancelled(true);
            JavaPlugin.getPlugin(Main.class).getLogger().info("BlockFadeEvent");
        }
    }

    @EventHandler
    public void onEntityExplode(EntityExplodeEvent event) {
        for (Block block : event.blockList()) {
            if (execute(block)) {
                JavaPlugin.getPlugin(Main.class).getLogger().info("EntityExplodeEvent");
            }
        }
    }

    public boolean execute(Block block) {
        if (block.getType().equals(Material.LODESTONE)) {
            if (Main.getLocationDatabase().itemstorageExists(block.getLocation())) {
                try {
                    Main.getLocationDatabase().removeItemstorage(block.getLocation());
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }

                block.setType(Material.AIR);

                Bukkit.getWorld(block.getWorld().getName()).dropItemNaturally(block.getLocation(), ItemstorageItem.item().getItemStack());
                return true;
            }
        }
        return false;
    }
}
