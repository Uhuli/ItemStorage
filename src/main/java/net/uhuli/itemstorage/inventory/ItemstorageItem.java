package net.uhuli.itemstorage.inventory;

import dev.triumphteam.gui.builder.item.ItemBuilder;
import dev.triumphteam.gui.guis.GuiItem;
import net.uhuli.itemstorage.Main;
import net.uhuli.itemstorage.util.Message;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.persistence.PersistentDataType;

import java.util.UUID;

public class ItemstorageItem {

    public static GuiItem item() {
        NamespacedKey isItemStorageKey = new NamespacedKey(Main.getInstance(), "isItemStorage");
        NamespacedKey UUIDKey = new NamespacedKey(Main.getInstance(), "uuid");
        NamespacedKey StorageKey = new NamespacedKey(Main.getInstance(), "storage");
        GuiItem item = ItemBuilder.from(Material.LODESTONE).name(Message.miniMessage("<i:false><gold><b>ItemStorage</b></gold>"))
                .enchant(Enchantment.DURABILITY, 1)
                .flags(ItemFlag.HIDE_ENCHANTS)
                .pdc(persistentDataContainer -> {
                    persistentDataContainer.set(isItemStorageKey, PersistentDataType.BOOLEAN, true);
                    persistentDataContainer.set(UUIDKey, PersistentDataType.STRING, UUID.randomUUID().toString());
                    persistentDataContainer.set(StorageKey, PersistentDataType.INTEGER, 64);
                }).asGuiItem();
        return item;
    }
}
