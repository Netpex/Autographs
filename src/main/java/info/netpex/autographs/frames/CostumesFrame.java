package info.netpex.autographs.frames;

import com.github.stefvanschie.inventoryframework.pane.PaginatedPane;
import com.github.stefvanschie.inventoryframework.pane.StaticPane;
import info.netpex.autographs.Autographs;
import com.github.stefvanschie.inventoryframework.gui.GuiItem;
import com.github.stefvanschie.inventoryframework.gui.type.ChestGui;
import info.netpex.autographs.utility.Costumes;
import info.netpex.autographs.utility.Items;
import info.netpex.autographs.utility.PersistentData;
import info.netpex.autographs.utility.Placeholders;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.Configuration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

public class CostumesFrame extends JavaPlugin {

    public static ChestGui create(Player player) {

        ChestGui gui = new ChestGui(6, "Costumes");
        gui.setOnGlobalClick(event -> event.setCancelled(true));
        PaginatedPane pages = new PaginatedPane(0, 0, 9, 5);

        Configuration config = Autographs.getPlugin().getConfig();
        Collection<String> costumesPath = Autographs.getPlugin().getConfig().getConfigurationSection("costumes").getKeys(false);
        ArrayList<ItemStack> costumes = new ArrayList<ItemStack>(Arrays.asList());

        for (String c : costumesPath) {
            ItemStack item = Items.costumeItem(player, "costumes." + c, false);
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
           if (event.isRightClick()) {
               ChestGui Details = CostumeDetail.create(player, "costumes."+event.getCurrentItem().getItemMeta().getPersistentDataContainer().get(new NamespacedKey(Autographs.getPlugin(), "path"), PersistentDataType.STRING));
               player.closeInventory();
               Details.show(player);
           } else {
               if (PersistentData.has(player, "costume")) {
                   String costume = PersistentData.get(player, "costume");
                   if (costume.equalsIgnoreCase("none")) {
                       Costumes.enable(player,event.getCurrentItem().getItemMeta().getPersistentDataContainer().get(new NamespacedKey(Autographs.getPlugin(), "path"), PersistentDataType.STRING));
                   }
               }
            }
    });

        gui.addPane(pages);

        StaticPane a = new StaticPane(config.getInt("gui.costumes.left-arrow.gui-position.x"), config.getInt("gui.costumes.left-arrow.gui-position.y"), 1, 1);
        StaticPane b = new StaticPane(config.getInt("gui.costumes.right-arrow.gui-position.x"), config.getInt("gui.costumes.right-arrow.gui-position.y"), 1, 1);
        StaticPane c = new StaticPane(config.getInt("gui.costumes.back-arrow.gui-position.x"), config.getInt("gui.costumes.back-arrow.gui-position.y"), 1, 1);
        StaticPane d = new StaticPane(config.getInt("gui.costumes.remove.gui-position.x"), config.getInt("gui.costumes.remove-position.y"), 1, 1);
        a.addItem(new GuiItem(Items.guiItem(player, "gui.costumes.left-arrow", false),event -> {
            if (pages.getPage() > 0) {
                pages.setPage(pages.getPage() - 1);

                gui.update();
            }
        }), 0,0);

        b.addItem(new GuiItem(Items.guiItem(player, "gui.costumes.right-arrow", false), event -> {
            if (pages.getPage() < pages.getPages() - 1) {
                pages.setPage(pages.getPage() + 1);

                gui.update();
            }
        }), 0,0);

        c.addItem(new GuiItem(Items.guiItem(player, "gui.costumes.back-arrow", false), event ->
                event.getWhoClicked().closeInventory()), 0,0);

        d.addItem(new GuiItem(Items.guiItem(player, "gui.costumes.remove", false), event ->{
            if (PersistentData.has(player, "costume")) {
                String costume = PersistentData.get(player, "costume");
                if (!costume.equalsIgnoreCase("none")) {
                    player.getEquipment().setHelmet(new ItemStack(Material.AIR));
                    player.getEquipment().setChestplate(new ItemStack(Material.AIR));
                    player.getEquipment().setLeggings(new ItemStack(Material.AIR));
                    player.getEquipment().setBoots(new ItemStack(Material.AIR));
                    player.getInventory().setItem(8, new ItemStack(Material.AIR));

                    PersistentData.set(player, "costume", "none");
                    player.sendMessage(Placeholders.translate(player, "%prefix% &6Costume " + config.getString("costumes." + PersistentData.get(player, "costume") + ".name") + " &7was &cdisabled&7!"));
                }
            }
        }), 0,0);

        gui.addPane(a);
        gui.addPane(b);
        gui.addPane(c);
        gui.addPane(d);

        return(gui);
    }
}
