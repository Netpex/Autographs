package info.netpex.autographs.utility;

import info.netpex.autographs.Autographs;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;

public class Items extends JavaPlugin {

    public static ItemStack guiItem(Player player, String path) {
        ItemStack arrow = new ItemStack(Material.valueOf(Config.getString("item", path)));
        ItemMeta meta = arrow.getItemMeta();

        meta.setCustomModelData(Config.getInt("custom-data", path));
        meta.setDisplayName(Placeholders.translate(player, Config.getString("title", path)));

        ArrayList<String> lore = new ArrayList<>();
        for (String s : Autographs.getPlugin().getConfig().getStringList(path.replace("_", ".")+".lore")) {
            lore.add(Placeholders.translate(player, s));
        }
        meta.setLore(lore);
        arrow.setItemMeta(meta);

        return(arrow);
    }
}
