package info.netpex.autographs.utility;

import info.netpex.autographs.Autographs;
import me.arcaniax.hdb.api.HeadDatabaseAPI;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.Configuration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;

public class Costumes extends JavaPlugin {

    static boolean foundSlot;

    public static void  enable(Player player, String costume) {

        String path = "costumes."+costume;
        Configuration config = Autographs.getPlugin().getConfig();

        System.out.println(path);
        ItemStack chest = Items.costumePiece(player, path, "CHESTPLATE", "&6This &7is a part of "+ config.getString(path+".name"), true);
        ItemStack legs = Items.costumePiece(player, path, "LEGGINGS", "&6This &7is a part of "+ config.getString(path+".name"), true);
        ItemStack boots = Items.costumePiece(player, path, "BOOTS", "&6This &7is a part of "+ config.getString(path+".name"), true);

        ItemStack head;
        if (config.getBoolean("options.useHeadDataBase")) {
            head = new HeadDatabaseAPI().getItemHead(config.getString(path+".gui-item"));
        } else {
            head = new ItemStack(Material.valueOf(config.getString(path+".gui-item")));
        }

        ItemMeta meta = head.getItemMeta();
        meta.setDisplayName(Placeholders.translate(player, config.getString(path+".name")));
        ArrayList<String> lore = new ArrayList<>();
        lore.add(Placeholders.translate(player, "&6This &7is a part of "+ config.getString(path+".name")));
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        PersistentDataContainer d = meta.getPersistentDataContainer();
        d.set(new NamespacedKey(Autographs.getPlugin(), "block_interact"), PersistentDataType.STRING,  "true");
        d.set(new NamespacedKey(Autographs.getPlugin(), "auto_tool_type"), PersistentDataType.STRING,  "costume");
        meta.setLore(lore);
        head.setItemMeta(meta);

        player.getInventory().setChestplate(chest);
        player.getInventory().setLeggings(legs);
        player.getInventory().setBoots(boots);
        player.getInventory().setHelmet(head);

        ItemStack delete = new ItemStack(Material.BARRIER);
        ItemMeta metadelete = delete.getItemMeta();
        metadelete.setDisplayName(Placeholders.translate(player, "&4&lRemove"));
        ArrayList<String> loredelete = new ArrayList<>();
        loredelete.add(Placeholders.translate(player, "&6Click&7 to &cremove &7costume &r"+config.getString(path+".name")));
        metadelete.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        PersistentDataContainer data = metadelete.getPersistentDataContainer();
        data.set(new NamespacedKey(Autographs.getPlugin(), "block_interact"), PersistentDataType.STRING,  "true");
        data.set(new NamespacedKey(Autographs.getPlugin(), "auto_tool_type"), PersistentDataType.STRING,  "disable_costume");
        metadelete.setLore(loredelete);
        delete.setItemMeta(metadelete);

        foundSlot = false;
        for (int size = 0; size<player.getInventory().getSize(); size++) {
            ItemStack i = player.getInventory().getItem(8);
            if (player.getInventory().getItem(size) == null) {
                if (size <= 53) {
                    player.getInventory().setItem(size, i);
                    System.out.println(size);
                    player.getInventory().setItem(8, delete);
                    foundSlot = true;
                    break;
                }
            }
        }

        System.out.println(foundSlot);
        if (!foundSlot) {
            player.sendMessage(Placeholders.translate(player, "%prefix% &6"+ player.getInventory().getItem(8).getType()+" &7was &creplaced &7with another &citem&7!"));
            player.getInventory().setItem(8, delete);
        }

        PersistentData.set(player, "costume", costume);
        player.sendMessage(Placeholders.translate(player, "%prefix% &6Costume "+config.getString(path+".name")+" &7was &aenabled&7!"));
    }
}
