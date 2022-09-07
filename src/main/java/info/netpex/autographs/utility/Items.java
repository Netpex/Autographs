package info.netpex.autographs.utility;

import info.netpex.autographs.Autographs;
import me.arcaniax.hdb.api.HeadDatabaseAPI;
import org.bukkit.ChatColor;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;

public class Items extends JavaPlugin {

    public static ItemStack guiItem(Player player, String path) {
        ItemStack arrow = new ItemStack(Material.valueOf(Config.getString("item", path)));
        ItemMeta meta = arrow.getItemMeta();

        meta.setCustomModelData(Config.getInt("model-data", path));
        meta.setDisplayName(Placeholders.translate(player, Config.getString("title", path)));

        ArrayList<String> lore = new ArrayList<>();
        for (String s : Autographs.getPlugin().getConfig().getStringList(path.replace("_", ".")+".lore")) {
            lore.add(Placeholders.translate(player, s));
        }
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        meta.setLore(lore);
        arrow.setItemMeta(meta);

        return(arrow);
    }

    public static ItemStack costumeItem(Player player, String path) {
        ItemStack head;
        if (Config.getBool("useHeadDataBase", "options")) {
             head = new HeadDatabaseAPI().getItemHead(Config.getString("gui-item", path));
        } else {
            try {
                if (PersistentData.isNumeric(Config.getString("gui-item", path))) {
                    Autographs.getPlugin().getServer().getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&4Could not create head alternative. &6Double check &7'&9useHeadDataBase&7'&6 is true &7to use numeric values."));
                    return null;
                }
                head = new ItemStack(Material.valueOf(Config.getString("gui-item", path)));
            } catch(Exception e) {
                Autographs.getPlugin().getServer().getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&4Could not create head alternative."));
                return null;
            }
        }
        ItemMeta meta = head.getItemMeta();

        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        meta.setDisplayName(Placeholders.translate(player, Config.getString("name", path)));
        ArrayList<String> lore = new ArrayList<>();
        for (String s : Autographs.getPlugin().getConfig().getStringList(path.replace("_", ".")+".lore")) {
            lore.add(Placeholders.translate(player, s));
        }
        lore.add(Placeholders.translate(player, "&aLeft &6click &7to &3toggle&7."));
        lore.add(Placeholders.translate(player, "&cRight &6click &7to &3inspect&7."));
        meta.setLore(lore);
        head.setItemMeta(meta);

        return(head);
    }

    public static ItemStack costumePiece(Player player, String path, String type, String l) {
        ItemStack armor = new ItemStack(Material.valueOf("LEATHER_"+type));
        ArrayList<String> lore = new ArrayList<>();
        lore.add(Placeholders.translate(player, l));
        LeatherArmorMeta meta = (LeatherArmorMeta) armor.getItemMeta();
        meta.setDisplayName(Placeholders.translate(player, type.substring(0, 1).toUpperCase() + type.substring(1)));
        meta.addItemFlags(ItemFlag.HIDE_DYE);
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        meta.setLore(lore);
        meta.setColor(DyeColor.valueOf(Config.getString(type.toLowerCase(), path).toUpperCase()).getColor());
        armor.setItemMeta(meta);

        return(armor);
    }
}
