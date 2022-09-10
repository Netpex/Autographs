package info.netpex.autographs.frames;

import com.github.stefvanschie.inventoryframework.gui.GuiItem;
import com.github.stefvanschie.inventoryframework.gui.type.ChestGui;
import com.github.stefvanschie.inventoryframework.pane.OutlinePane;
import com.github.stefvanschie.inventoryframework.pane.StaticPane;
import info.netpex.autographs.Autographs;
import info.netpex.autographs.utility.Items;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.configuration.Configuration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public class CostumeColor extends JavaPlugin {

    static DyeColor currentColor;

    public static ChestGui create(Player player, String path, String type) {

        ChestGui gui = new ChestGui(3, "Color Selector");
        gui.setOnGlobalClick(event -> event.setCancelled(true));
        Configuration config = Autographs.getPlugin().getConfig();

        List<Material> palette = new ArrayList<>();
        StaticPane a =  new StaticPane(config.getInt("gui.costumes.back-arrow.gui-position.x"), config.getInt("gui.costumes.back-arrow.gui-position.y"), 1, 1);
        StaticPane b = new StaticPane(config.getInt("gui.costumes.gui-position.x"), config.getInt("gui.costumes.gui-position.y"), 1, 1);
        StaticPane c = new StaticPane(0, 2, 1, 1);
        StaticPane d = new StaticPane(8, 2, 1, 1);
        StaticPane e = new StaticPane(config.getInt("gui.color-selector.focus-item.gui-position.x"), config.getInt("gui.color-selector.focus-item.gui-position.y"), 1, 1);

        ItemStack currentChoice = Items.costumePiece(player, path, type, "&6Select &7a &3color &7to &9change", false);

        e.addItem(new GuiItem(currentChoice, event -> event.setCancelled(true)), 0,0);

        palette.add(Material.RED_DYE);
        palette.add(Material.CYAN_DYE);
        palette.add(Material.GRAY_DYE);
        palette.add(Material.LIME_DYE);
        palette.add(Material.BLUE_DYE);
        palette.add(Material.GREEN_DYE);
        palette.add(Material.BROWN_DYE);
        palette.add(Material.PURPLE_DYE);
        palette.add(Material.PINK_DYE);
        palette.add(Material.ORANGE_DYE);
        palette.add(Material.YELLOW_DYE);
        palette.add(Material.MAGENTA_DYE);
        palette.add(Material.LIGHT_GRAY_DYE);
        palette.add(Material.LIGHT_BLUE_DYE);

        c.addItem(new GuiItem(new ItemStack(Material.WHITE_DYE), event -> {
            currentColor = DyeColor.valueOf(Material.WHITE_DYE.name().split("_DYE")[0]);
            LeatherArmorMeta meta = (LeatherArmorMeta) currentChoice.getItemMeta();
            meta.setColor(currentColor.getColor());
            currentChoice.setItemMeta(meta);
            gui.update();
        }), 0 ,0);
        d.addItem(new GuiItem(new ItemStack(Material.BLACK_DYE), event -> {
            currentColor = DyeColor.valueOf(Material.BLACK_DYE.name().split("_DYE")[0]);
            LeatherArmorMeta meta = (LeatherArmorMeta) currentChoice.getItemMeta();
            meta.setColor(currentColor.getColor());
            currentChoice.setItemMeta(meta);
            gui.update();
        }), 0,0);

        OutlinePane selection = new OutlinePane(1, 1, 7, 2);

        a.addItem(new GuiItem(Items.guiItem(player, "gui.costumes.back-arrow", false), event -> {
            Autographs.getPlugin().getConfig().set(path+"."+type.toLowerCase(), currentColor.name().substring(0, 1).toUpperCase() + currentColor.name().substring(1));
            Autographs.getPlugin().saveConfig();
            ChestGui Details = CostumeDetail.create(player, path);
            player.closeInventory();
            Details.show(player);
        }), 0,0);


        for (Material color : palette) {
            selection.addItem(new GuiItem(new ItemStack(color), event -> {
                currentColor = DyeColor.valueOf(color.name().split("_DYE")[0]);
                LeatherArmorMeta meta = (LeatherArmorMeta) currentChoice.getItemMeta();
                meta.setColor(currentColor.getColor());
                currentChoice.setItemMeta(meta);
                gui.update();
            }));
        }

        ItemStack hidden = new ItemStack(Material.valueOf(config.getString("gui.color-selector.item")));
        ItemMeta meta = hidden.getItemMeta();
        meta.setDisplayName("Autographs By: Netpex");
        meta.setCustomModelData(config.getInt("gui.color-selector.model-data"));
        hidden.setItemMeta(meta);
        b.addItem(new GuiItem(hidden, event ->
                event.setCancelled(true)), 0,0);
        gui.addPane(a);
        gui.addPane(b);
        gui.addPane(c);
        gui.addPane(d);
        gui.addPane(e);
        gui.addPane(selection);

        return(gui);
    }
}
