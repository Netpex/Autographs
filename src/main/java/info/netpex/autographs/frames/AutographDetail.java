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

public class AutographDetail extends JavaPlugin {

    public static ChestGui create(Player player, String path) {

        ChestGui gui = new ChestGui(6, "Autograph - Details");
        gui.setOnGlobalClick(event -> event.setCancelled(true));
        StaticPane details = new StaticPane(0, 0, 9, 5);
        Configuration config = Autographs.getPlugin().getConfig();

        ItemStack head;
        if (config.getBoolean("options.useHeadDataBase")) {
            head = new HeadDatabaseAPI().getItemHead(config.getString(path+".gui-item"));
        } else {
            head = new ItemStack(Material.valueOf(config.getString(path+".gui-item")));
        }

        ItemStack signedby = new ItemStack(Material.FEATHER);
        ItemMeta metasignedby = signedby.getItemMeta();
        metasignedby.setDisplayName(Placeholders.translate(player, "&6Signed &7by:"));
        ArrayList<String> loresignedby = new ArrayList<>();
        loresignedby.add(Placeholders.translate(player, "&8%player_who_signed%"));
        metasignedby.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        metasignedby.setLore(loresignedby);
        signedby.setItemMeta(metasignedby);

        ItemStack date = new ItemStack(Material.FEATHER);
        ItemMeta metadate = date.getItemMeta();
        metadate.setDisplayName(Placeholders.translate(player, "&7Date &6Signed&7:"));
        ArrayList<String> loredate = new ArrayList<>();
        loredate.add(Placeholders.translate(player, "&8%date_signed%"));
        metadate.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        metadate.setLore(loredate);
        date.setItemMeta(metadate);

        ItemStack signednum = new ItemStack(Material.BARRIER);
        ItemMeta metasignednum = signednum.getItemMeta();
        metasignednum.setDisplayName(Placeholders.translate(player, "&6Rarity&7:"));
        ArrayList<String> loresignednum = new ArrayList<>();
        loresignednum.add(Placeholders.translate(player, "&8%number_of_signs"));
        metasignednum.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        metasignednum.setLore(loresignednum);
        signednum.setItemMeta(metasignednum);

        details.addItem(new GuiItem(head, event -> event.setCancelled(true)), 3,1);
        details.addItem(new GuiItem(Items.costumePiece(player, path, "CHESTPLATE", " ", false)), 3,2);
        details.addItem(new GuiItem(Items.costumePiece(player, path, "LEGGINGS", " ", false)), 3,3);
        details.addItem(new GuiItem(Items.costumePiece(player, path, "BOOTS", " ", false)), 3,4);
        details.addItem(new GuiItem(Items.guiItem(player, "worlds." + config.getString(path+".park"), false), event -> event.setCancelled(true)), 5,1);
        details.addItem(new GuiItem(signedby), 5,2);
        details.addItem(new GuiItem(date), 5,3);
        details.addItem(new GuiItem(signednum), 5,4);

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
