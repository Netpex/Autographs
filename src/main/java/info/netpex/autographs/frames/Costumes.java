package info.netpex.autographs.frames;

import com.github.stefvanschie.inventoryframework.pane.PaginatedPane;
import com.github.stefvanschie.inventoryframework.pane.StaticPane;
import com.sun.org.apache.xml.internal.utils.NameSpace;
import info.netpex.autographs.Autographs;
import com.github.stefvanschie.inventoryframework.gui.GuiItem;
import com.github.stefvanschie.inventoryframework.gui.type.ChestGui;
import info.netpex.autographs.utility.Config;
import info.netpex.autographs.utility.Items;
import info.netpex.autographs.utility.PersistentData;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

public class Costumes extends JavaPlugin {

    Autographs plugin = Autographs.getPlugin();

    public static ChestGui create(Player player) {

        ChestGui gui = new ChestGui(6, "Costumes");
        gui.setOnGlobalClick(event -> event.setCancelled(true));
        PaginatedPane pages = new PaginatedPane(0, 0, 9, 5);

        Collection<String> costumesPath = Autographs.getPlugin().getConfig().getConfigurationSection("costumes").getKeys(false);
        ArrayList<ItemStack> costumes = new ArrayList<ItemStack>(Arrays.asList());

        for (String c : costumesPath) {
            ItemStack item = Items.costumeItem(player, "costumes_" + c);
            if (item != null) {
            ItemMeta meta = item.getItemMeta();
            PersistentDataContainer data = meta.getPersistentDataContainer();
            data.set(new NamespacedKey(Autographs.getPlugin(), "path"), PersistentDataType.STRING,  c);
            item.setItemMeta(meta);
            costumes.add(item);
            }
        }
        pages.populateWithItemStacks(costumes);
        pages.setOnClick(event -> {
            ChestGui Details = CostumeDetail.create(player, "costumes_"+event.getCurrentItem().getItemMeta().getPersistentDataContainer().get(new NamespacedKey(Autographs.getPlugin(), "path"), PersistentDataType.STRING));
            player.closeInventory();
            Details.show(player);
        });

        gui.addPane(pages);

        StaticPane a = new StaticPane(Config.getInt("x", "gui_costumes_left-arrow_gui-position"), Config.getInt("y", "gui_costumes_left-arrow_gui-position"), 1, 1);
        StaticPane b = new StaticPane(Config.getInt("x", "gui_costumes_right-arrow_gui-position"), Config.getInt("y", "gui_costumes_right-arrow_gui-position"), 1, 1);
        StaticPane c = new StaticPane(Config.getInt("x", "gui_costumes_back-arrow_gui-position"), Config.getInt("y", "gui_costumes_back-arrow_gui-position"), 1, 1);
        StaticPane d = new StaticPane(Config.getInt("x", "gui_costumes_gui-position"), Config.getInt("y", "gui_costumes_gui-position"), 1, 1);
        a.addItem(new GuiItem(Items.guiItem(player, "gui_costumes_left-arrow"),event -> {
            if (pages.getPage() > 0) {
                pages.setPage(pages.getPage() - 1);

                gui.update();
            }
        }), 0,0);

        b.addItem(new GuiItem(Items.guiItem(player, "gui_costumes_right-arrow"), event -> {
            if (pages.getPage() < pages.getPages() - 1) {
                pages.setPage(pages.getPage() + 1);

                gui.update();
            }
        }), 0,0);

        c.addItem(new GuiItem(Items.guiItem(player, "gui_costumes_back-arrow"), event ->
                event.getWhoClicked().closeInventory()), 0,0);

        ItemStack hidden = new ItemStack(Material.valueOf(Config.getString("item", "gui_costumes")));
        ItemMeta meta = hidden.getItemMeta();
        meta.setDisplayName("Autographs By: Netpex");
        meta.setCustomModelData(Config.getInt("model-data", "gui_costumes"));
        hidden.setItemMeta(meta);
        d.addItem(new GuiItem(hidden, event ->
                event.setCancelled(true)), 0,0);
        gui.addPane(a);
        gui.addPane(b);
        gui.addPane(c);
        gui.addPane(d);

        return(gui);
    }
}
