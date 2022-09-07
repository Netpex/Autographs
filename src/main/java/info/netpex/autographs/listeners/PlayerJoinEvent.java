package info.netpex.autographs.listeners;

import info.netpex.autographs.Autographs;
import info.netpex.autographs.utility.PersistentData;
import info.netpex.autographs.utility.Placeholders;
import org.bukkit.Material;
import org.bukkit.configuration.Configuration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

public class PlayerJoinEvent implements Listener {

    Autographs plugin = Autographs.getPlugin();
    Configuration config = plugin.getConfig();

    @EventHandler
    public void onPlayerJoin(org.bukkit.event.player.PlayerJoinEvent e) {
        Player player = e.getPlayer();

        if (PersistentData.has(player, "costume")) {
            String costume = PersistentData.get(player, "costume");

            if (!costume.equalsIgnoreCase("none")) {
                player.sendMessage(Placeholders.translate(player, config.getString("options.responses.still-dressed")));
            }
        } else {
            PersistentData.set(player, "costume", "none");
        }

        ItemStack book = new ItemStack(Material.valueOf(config.getString("item.item")));
        ItemMeta meta = book.getItemMeta();
        meta.setCustomModelData(config.getInt("item.model-data"));

        ArrayList<String> lore = new ArrayList<>();
        for (String s : plugin.getConfig().getStringList("item.lore")) {
            lore.add(Placeholders.translate(player, s));
        }
        meta.setLore(lore);

        book.setItemMeta(meta);

        if (config.getBoolean("options.forceItemSlot")) {
            player.getInventory().setItem(config.getInt("item.force-slot"), book);
        } else {
            player.getInventory().addItem(book);
        }
    }
}
