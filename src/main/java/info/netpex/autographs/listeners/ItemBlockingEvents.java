package info.netpex.autographs.listeners;

import info.netpex.autographs.Autographs;
import info.netpex.autographs.utility.PersistentData;
import info.netpex.autographs.utility.Placeholders;
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
        if (item != null && item.getType() != Material.AIR) {
            ItemMeta meta = item.getItemMeta();
            if (meta.getPersistentDataContainer().has(new NamespacedKey(Autographs.getPlugin(), "auto_tool_type"), PersistentDataType.STRING)) {
                if (meta.getPersistentDataContainer().get(new NamespacedKey(Autographs.getPlugin(), "auto_tool_type"), PersistentDataType.STRING).equalsIgnoreCase("disable_costume")) {
                    if (PersistentData.has(player, "costume")) {
                        item.setAmount(-1);
                        String costume = PersistentData.get(player, "costume");
                        if (!costume.equalsIgnoreCase("none")) {
                            for (int size = 0; size < player.getInventory().getSize(); size++) {
                                ItemStack i = player.getInventory().getItem(size);
                                if (i != null && i.getType() != Material.AIR) {
                                    ItemMeta m = i.getItemMeta();
                                    if (m.getPersistentDataContainer().has(new NamespacedKey(Autographs.getPlugin(), "auto_tool_type"), PersistentDataType.STRING)) {
                                        if (m.getPersistentDataContainer().get(new NamespacedKey(Autographs.getPlugin(), "auto_tool_type"), PersistentDataType.STRING).equalsIgnoreCase("disable_costume")) {
                                            player.getInventory().setItem(size, new ItemStack(Material.AIR));
                                        } else if (m.getPersistentDataContainer().get(new NamespacedKey(Autographs.getPlugin(), "auto_tool_type"), PersistentDataType.STRING).equalsIgnoreCase("costume")) {
                                            System.out.println(size);
                                            System.out.println(i);
                                            player.getInventory().setItem(size, new ItemStack(Material.AIR));
                                            if (size == 39) {
                                                player.getInventory().setHelmet(new ItemStack(Material.AIR));
                                            } else if (size == 38) {
                                                player.getInventory().setChestplate(new ItemStack(Material.AIR));
                                            } else if (size == 37) {
                                                player.getInventory().setLeggings(new ItemStack(Material.AIR));
                                            } else if (size == 36) {
                                                player.getInventory().setBoots(new ItemStack(Material.AIR));
                                            }
                                        }
                                    }
                                }
                            }

                                PersistentData.set(player, "costume", "none");
                                player.sendMessage(Placeholders.translate(player, "%prefix% &6Costume " + Autographs.getPlugin().getConfig().getString("costumes." + PersistentData.get(player, "costume") + ".name") + " &7was &cdisabled&7!"));
                        }
                    } else {
                        for (int size = 0; size < player.getInventory().getSize(); size++) {
                            ItemStack i = player.getInventory().getItem(size);
                            if (i != null && i.getType() != Material.AIR) {
                                ItemMeta m = i.getItemMeta();
                                if (m.getPersistentDataContainer().has(new NamespacedKey(Autographs.getPlugin(), "auto_tool_type"), PersistentDataType.STRING)) {
                                    if (m.getPersistentDataContainer().get(new NamespacedKey(Autographs.getPlugin(), "auto_tool_type"), PersistentDataType.STRING).equalsIgnoreCase("disable_costume")) {
                                        player.getInventory().setItem(size, new ItemStack(Material.AIR));
                                    }
                                }
                            }
                        }
                    }
                }
                if (meta.getPersistentDataContainer().has(new NamespacedKey(Autographs.getPlugin(), "block_interact"), PersistentDataType.STRING)) {
                    if (player.getGameMode() == GameMode.CREATIVE) {
                        return;
                    } else {
                        e.setCancelled(true);
                    }
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
