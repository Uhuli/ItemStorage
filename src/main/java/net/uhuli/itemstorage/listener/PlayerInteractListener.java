package net.uhuli.itemstorage.listener;

import dev.triumphteam.gui.builder.item.ItemBuilder;
import dev.triumphteam.gui.guis.Gui;
import net.kyori.adventure.text.Component;
import net.uhuli.itemstorage.Main;
import net.uhuli.itemstorage.database.LocationDatabase;
import net.uhuli.itemstorage.util.Message;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

import java.sql.SQLException;

public class PlayerInteractListener implements Listener {
    private final LocationDatabase database = Main.getLocationDatabase();

   @EventHandler
   public void onPlayerInteract(PlayerInteractEvent event) throws SQLException {
       if (event.getClickedBlock() == null) {
           return;
       }
       if (event.getClickedBlock().getType().equals(Material.LODESTONE) && event.getAction().isRightClick()) {
           if (database.itemstorageExists(event.getClickedBlock().getLocation())) {
               event.setCancelled(true);
               ItemStorageGUI(event.getPlayer());

               //DEBUG:
               event.getPlayer().sendMessage("Clicked with " + event.getAction() + " on " + event.getClickedBlock().getType());
           }
       }
   }

   private void ItemStorageGUI(HumanEntity player) {
       Gui gui = Gui.gui()
               .title(Message.miniMessage("            <gray><b><i:false>ItemStorage</b></gray>"))
               .rows(3)
               .disableAllInteractions()
               .create();

       gui.getFiller().fill(ItemBuilder.from(Material.GRAY_STAINED_GLASS_PANE).name(Component.empty()).asGuiItem());

       gui.setItem(11, ItemBuilder.from(Material.BARREL)
               .name(Message.miniMessage("<i:false><gold>Speicher erweitern</gold>")).asGuiItem());
       gui.setItem(13, ItemBuilder.from(Material.LODESTONE)
               .name(Message.miniMessage("<i:false><gold>ItemStorage</gold>")).asGuiItem());
       gui.setItem(15, ItemBuilder.from(Material.TOTEM_OF_UNDYING)
               .name(Message.miniMessage("<i:false><gold>Herausnehmen</gold>")).asGuiItem());

       //DEBUG:
       player.sendMessage("Opening GUI");

       gui.open(player);
   }
}