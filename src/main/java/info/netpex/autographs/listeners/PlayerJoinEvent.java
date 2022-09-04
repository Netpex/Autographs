package info.netpex.autographs.listeners;

import info.netpex.autographs.Autographs;
import info.netpex.autographs.utility.Config;
import info.netpex.autographs.utility.PersistentData;
import info.netpex.autographs.utility.Placeholders;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

public class PlayerJoinEvent implements Listener {

    Autographs plugin = Autographs.getPlugin();

    @EventHandler
    public void onPlayerJoin(org.bukkit.event.player.PlayerJoinEvent e) {
        Player player = e.getPlayer();

        if (PersistentData.has(player, "costume")) {
            String costume = PersistentData.get(player, "costume");

            if (!costume.equalsIgnoreCase("none")) {
                player.sendMessage(Placeholders.translate(player, Config.getString("still-dressed", "options_responses")));
            }
        } else {
            PersistentData.set(player, "costume", "none");
        }

        ItemStack book = new ItemStack(Material.valueOf(Config.getString("item", "item")));
        ItemMeta meta = book.getItemMeta();
        meta.setCustomModelData(Config.getInt("model-data", "item"));

        ArrayList<String> lore = new ArrayList<>();
        for (String s : plugin.getConfig().getStringList("item.lore")) {
            lore.add(Placeholders.translate(player, s));
        }
        meta.setLore(lore);

        book.setItemMeta(meta);

        if (Config.getBool("forceItemSlot", "options")) {
            player.getInventory().setItem(Config.getInt("force-slot", "item"), book);
        } else {
            player.getInventory().addItem(book);
        }
    }
}
