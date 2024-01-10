package net.uhuli.itemstorage.command;

import net.uhuli.itemstorage.Main;
import net.uhuli.itemstorage.inventory.ItemstorageItem;
import net.uhuli.itemstorage.listener.BlockBreakListener;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;

public class ItemStorageCommand implements CommandExecutor {

    NamespacedKey isItemStorageKey = new NamespacedKey(Main.getInstance(), "isItemStorage");
    NamespacedKey UUIDKey = new NamespacedKey(Main.getInstance(), "uuid");
    NamespacedKey StorageKey = new NamespacedKey(Main.getInstance(), "storage");

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendRichMessage("Only Player allowed!");
            return true;
        }

        if (args.length == 0) {
            player.getInventory().addItem(ItemstorageItem.item().getItemStack());
        }

        if (args.length == 1 && args[0].equals("getData")) {
            if (!player.getInventory().getItemInMainHand().getType().equals(Material.AIR)) {
                ItemMeta itemMeta = player.getInventory().getItemInMainHand().getItemMeta();
                if (itemMeta.getPersistentDataContainer().has(isItemStorageKey, PersistentDataType.BOOLEAN)) {
                    player.sendMessage("--------------------");
                    player.sendMessage("ItemStorage: "
                            + itemMeta.getPersistentDataContainer().get(isItemStorageKey, PersistentDataType.BOOLEAN));
                    player.sendMessage("UUID: "
                            + itemMeta.getPersistentDataContainer().get(UUIDKey, PersistentDataType.STRING));
                    player.sendMessage("Storage: "
                            + itemMeta.getPersistentDataContainer().get(StorageKey, PersistentDataType.INTEGER));
                    player.sendMessage("--------------------");
                } else {
                    player.sendMessage("This is not an ItemStorage");
                }
            } else {
                player.sendMessage("You have to hold an Item in your Hand");
            }
        }

        return true;
    }
}
