package info.netpex.autographs.frames;

import com.github.stefvanschie.inventoryframework.gui.GuiItem;
import com.github.stefvanschie.inventoryframework.gui.type.ChestGui;
import com.github.stefvanschie.inventoryframework.pane.StaticPane;
import info.netpex.autographs.Autographs;
import info.netpex.autographs.utility.Items;
import info.netpex.autographs.utility.Placeholders;
import me.arcaniax.hdb.api.HeadDatabaseAPI;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Material;
import org.bukkit.configuration.Configuration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;

public class CostumeDetail extends JavaPlugin {

    public static ChestGui create(Player player, String path) {

        ChestGui gui = new ChestGui(6, "Costume - Details");
        gui.setOnGlobalClick(event -> event.setCancelled(true));
        StaticPane details = new StaticPane(0, 0, 9, 5);
        Configuration config = Autographs.getPlugin().getConfig();

        ItemStack head;
        if (config.getBoolean("options.useHeadDataBase")) {
            head = new HeadDatabaseAPI().getItemHead(config.getString(path+".gui-item"));
        } else {
            head = new ItemStack(Material.valueOf(config.getString(path+".gui-item")));
        }

        ItemStack rename = new ItemStack(Material.NAME_TAG);
        ItemMeta metarename = rename.getItemMeta();
        metarename.setDisplayName(Placeholders.translate(player, config.getString(path+".name")));
        ArrayList<String> lorerename = new ArrayList<>();
        lorerename.add(Placeholders.translate(player, "&6Click&7 to change &9Displayname"));
        metarename.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        metarename.setLore(lorerename);
        rename.setItemMeta(metarename);

        ItemStack delete = new ItemStack(Material.BARRIER);
        ItemMeta metadelete = delete.getItemMeta();
        metadelete.setDisplayName(Placeholders.translate(player, "&4&lDELETE"));
        ArrayList<String> loredelete = new ArrayList<>();
        loredelete.add(Placeholders.translate(player, "&6Click&7 to &cdelete &r"+config.getString(path+".name")));
        metadelete.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        metadelete.setLore(loredelete);
        delete.setItemMeta(metadelete);

        details.addItem(new GuiItem(head, event -> event.setCancelled(true)), 3,1);
        details.addItem(new GuiItem(Items.costumePiece(player, path, "CHESTPLATE", "&6Click &7to &9change ", false), event -> {
            ChestGui Details = CostumeColor.create(player, path, "CHESTPLATE");
            player.closeInventory();
            Details.show(player);
        }), 3,2);
        details.addItem(new GuiItem(Items.costumePiece(player, path, "LEGGINGS", "&6Click &7to &9change ", false), event-> {
            ChestGui Details = CostumeColor.create(player, path, "LEGGINGS");
            player.closeInventory();
            Details.show(player);
        }), 3,3);
        details.addItem(new GuiItem(Items.costumePiece(player, path, "BOOTS", "&6Click &7to &9change ", false), event -> {
            ChestGui Details = CostumeColor.create(player, path, "BOOTS");
            player.closeInventory();
            Details.show(player);
        }), 3,4);
        details.addItem(new GuiItem(Items.guiItem(player, "worlds." + config.getString(path+".park"), false), event -> event.setCancelled(true)), 5,1);
        details.addItem(new GuiItem(rename, event -> {
            TextComponent msg = new TextComponent(Placeholders.translate(player, "&6Click &chere &7to &9rename &r"+config.getString(path+".name")+"&7!"));
            msg.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(Placeholders.translate(player, "&7/ag rename "+config.getString(path+".name")+" y <New name>")).create()));
            msg.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/ag rename "+path.split("costumes.")[1]+" y <New name>"));
            player.spigot().sendMessage(msg);
            player.closeInventory();
        }), 5,2);
        details.addItem(new GuiItem(delete, event -> {
            TextComponent msg = new TextComponent(Placeholders.translate(player, "&6Click &chere &7to &cdelete &r"+config.getString(path+".name")+"&7!"));
            msg.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(Placeholders.translate(player, "&7/ag rename "+config.getString(path+".name")+" y <New name>")).create()));
            msg.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/ag delete "+path.split("costumes.")[1]+" y"));
            player.spigot().sendMessage(msg);
            player.closeInventory();
        }), 5,3);

        gui.addPane(details);

        StaticPane a =  new StaticPane(0, 5, 9, 1);

        a.addItem(new GuiItem(Items.guiItem(player, "gui.back-arrow", false), event ->{
            ChestGui g = CostumesFrame.create(player);
            player.closeInventory();
            g.show(player);
        }), 0,0);

        gui.addPane(a);

        return(gui);
    }
}
