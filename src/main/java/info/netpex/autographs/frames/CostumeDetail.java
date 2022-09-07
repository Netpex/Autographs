package info.netpex.autographs.frames;

import com.github.stefvanschie.inventoryframework.gui.GuiItem;
import com.github.stefvanschie.inventoryframework.gui.type.ChestGui;
import com.github.stefvanschie.inventoryframework.pane.PaginatedPane;
import com.github.stefvanschie.inventoryframework.pane.StaticPane;
import info.netpex.autographs.Autographs;
import info.netpex.autographs.utility.Config;
import info.netpex.autographs.utility.Items;
import info.netpex.autographs.utility.Placeholders;
import me.arcaniax.hdb.api.HeadDatabaseAPI;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class CostumeDetail extends JavaPlugin {

    public static ChestGui create(Player player, String path) {

        ChestGui gui = new ChestGui(6, "Costumes");
        gui.setOnGlobalClick(event -> event.setCancelled(true));
        StaticPane details = new StaticPane(0, 0, 9, 5);

        ItemStack head;
        if (Config.getBool("useHeadDataBase", "options")) {
            head = new HeadDatabaseAPI().getItemHead(Config.getString("gui-item", path));
        } else {
            head = new ItemStack(Material.valueOf(Config.getString("gui-item", path)));
        }

        ItemStack rename = new ItemStack(Material.NAME_TAG);
        ItemMeta metarename = rename.getItemMeta();
        metarename.setDisplayName(Placeholders.translate(player, Config.getString("name", path)));
        ArrayList<String> lorerename = new ArrayList<>();
        lorerename.add(Placeholders.translate(player, "&6Click&7 to change &9Displayname"));
        metarename.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        metarename.setLore(lorerename);
        rename.setItemMeta(metarename);

        ItemStack delete = new ItemStack(Material.BARRIER);
        ItemMeta metadelete = rename.getItemMeta();
        metadelete.setDisplayName(Placeholders.translate(player, "&4&lDELETE"));
        ArrayList<String> loredelete = new ArrayList<>();
        loredelete.add(Placeholders.translate(player, "&6Click&7 to &cdelete &r"+Config.getString("name", path)));
        metadelete.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        metadelete.setLore(loredelete);
        delete.setItemMeta(metadelete);

        details.addItem(new GuiItem(head, event -> event.setCancelled(true)), 2,1);
        details.addItem(new GuiItem(Items.costumePiece(player, path, "CHESTPLATE", "&6Click &7to &9change "), event -> {
            ChestGui Details = CostumeColor.create(player, path, "CHESTPLATE");
            player.closeInventory();
            Details.show(player);
        }), 2,2);
        details.addItem(new GuiItem(Items.costumePiece(player, path, "LEGGINGS", "&6Click &7to &9change "), event-> {
            ChestGui Details = CostumeColor.create(player, path, "LEGGINGS");
            player.closeInventory();
            Details.show(player);
        }), 2,3);
        details.addItem(new GuiItem(Items.costumePiece(player, path, "BOOTS", "&6Click &7to &9change "), event -> {
            ChestGui Details = CostumeColor.create(player, path, "BOOTS");
            player.closeInventory();
            Details.show(player);
        }), 2,4);
        details.addItem(new GuiItem(Items.guiItem(player, "worlds_" + Config.getString("park", path)), event -> event.setCancelled(true)), 5,1);
        details.addItem(new GuiItem(rename, event -> {
            System.out.println(path.split("costumes_")[1]);
            TextComponent msg = new TextComponent(Placeholders.translate(player, "&6Click &chere &7to &9rename &r"+Config.getString("name", path)+"&7!"));
            msg.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(Placeholders.translate(player, "&7/ag rename "+Config.getString("name", path)+" true <New name>")).create()));
            msg.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/ag rename "+path.split("costumes_")[1]+" y <New name>"));
            player.spigot().sendMessage(msg);
            player.closeInventory();
        }), 5,2);
        details.addItem(new GuiItem(delete, event -> {

        }), 5,3);

        gui.addPane(details);

        StaticPane a = new StaticPane(Config.getInt("x", "gui_costumes_back-arrow_gui-position"), Config.getInt("y", "gui_costumes_back-arrow_gui-position"), 1, 1);
        StaticPane b = new StaticPane(Config.getInt("x", "gui_costumes_gui-position"), Config.getInt("y", "gui_costumes_gui-position"), 1, 1);

        a.addItem(new GuiItem(Items.guiItem(player, "gui_costumes_back-arrow"), event ->{
            ChestGui g = Costumes.create(player);
            player.closeInventory();
            g.show(player);
        }), 0,0);

        ItemStack hidden = new ItemStack(Material.valueOf(Config.getString("item", "gui_costumes")));
        ItemMeta meta = hidden.getItemMeta();
        meta.setDisplayName("Autographs By: Netpex");
        meta.setCustomModelData(Config.getInt("model-data", "gui_costumes"));
        hidden.setItemMeta(meta);
        b.addItem(new GuiItem(hidden, event ->
                event.setCancelled(true)), 0,0);
        gui.addPane(a);
        gui.addPane(b);

        return(gui);
    }
}
