package info.netpex.autographs.listeners;

import info.netpex.autographs.Autographs;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.*;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Set;

public class ItemBlockingEvents implements Listener {

    @EventHandler
    public void InventoryClickEvent(InventoryClickEvent  e) {
        Player player = (Player) e.getWhoClicked();
        PlayerInventory playerInventory = e.getWhoClicked().getInventory();

        ItemStack item = e.getCurrentItem();
        if(item != null && item.getType() != Material.AIR) {
            ItemMeta meta = item.getItemMeta();
            if (meta.getPersistentDataContainer().has(new NamespacedKey(Autographs.getPlugin(), "block_interact"), PersistentDataType.STRING)) {
              if (player.getGameMode() == GameMode.CREATIVE) {
                  return;
              } else {
                  e.setCancelled(true);
              }
            }
        }
    }
    @EventHandler
    public void PlayerDropItemEvent(PlayerDropItemEvent e) {
        Player player = e.getPlayer();

        ItemStack item = e.getItemDrop().getItemStack();
        if(item != null && item.getType() != Material.AIR) {
            ItemMeta meta = item.getItemMeta();
            if (meta.getPersistentDataContainer().has(new NamespacedKey(Autographs.getPlugin(), "block_interact"), PersistentDataType.STRING)) {
                e.setCancelled(true);
            }
        }
    }
    @EventHandler
    public void PlayerSwapHandItemsEvent(PlayerSwapHandItemsEvent e) {
        Player player = e.getPlayer();

        ItemStack item = e.getOffHandItem();
        if(item != null && item.getType() != Material.AIR) {
            ItemMeta meta = item.getItemMeta();
            if (meta.getPersistentDataContainer().has(new NamespacedKey(Autographs.getPlugin(), "block_interact"), PersistentDataType.STRING)) {
                e.setCancelled(true);
            }
        }
    }
}
