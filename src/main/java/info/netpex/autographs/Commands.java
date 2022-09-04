package info.netpex.autographs;

import com.github.stefvanschie.inventoryframework.gui.GuiItem;
import com.github.stefvanschie.inventoryframework.gui.type.ChestGui;
import com.github.stefvanschie.inventoryframework.pane.PaginatedPane;
import com.github.stefvanschie.inventoryframework.pane.StaticPane;
import info.netpex.autographs.utility.Config;
import info.netpex.autographs.utility.Items;
import info.netpex.autographs.utility.PersistentData;
import info.netpex.autographs.utility.Placeholders;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;

public class Commands implements CommandExecutor {

    Autographs plugin = Autographs.getPlugin();

    public static ArrayList<String> commandList = new ArrayList<String>(); //Empty array for tab completer

    static { //Adding commands to array for tab completer
        commandList.add("getData");
        commandList.add("costumes");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (sender instanceof Player) {
            Player player = (Player) sender;

            if (args.length > 0) {

                if (args[0].equalsIgnoreCase("getData")) {

                    if (args.length > 1) {

                        Player p = Bukkit.getPlayer(args[1]);
                        if (p instanceof Player || p != null) {
                            player.sendMessage(Placeholders.translate(p, "%prefix% Data retrieved for " + p.getName() + ": " + PersistentData.get(p, "Costume")));
                        } else {
                            player.sendMessage(Placeholders.translate(player, "%prefix% Player not found, is player offline?"));
                        }
                    } else {
                        player.sendMessage(Placeholders.translate(player, "%prefix% Data retrieved for " + player.getName() + ": " + PersistentData.get(player, "Costume")));
                    }
                } else if (args[0].equalsIgnoreCase("costumes")) {
                    ChestGui gui = new ChestGui(6, "Costumes");
                    PaginatedPane pages = new PaginatedPane(0, 0, 9, 5);
                    pages.populateWithItemStacks(Arrays.asList(
                            new ItemStack(Material.GOLDEN_SWORD),
                            new ItemStack(Material.LIGHT_GRAY_GLAZED_TERRACOTTA, 16),
                            new ItemStack(Material.COOKED_COD, 64)
                    ));
                    pages.setOnClick(event -> {
                        //buy item
                    });

                    gui.addPane(pages);

                    StaticPane a = new StaticPane(Config.getInt("x", "gui_costumes_left-arrow"), Config.getInt("y", "gui_costumes_left-arrow"), 1, 1);
                    StaticPane b = new StaticPane(Config.getInt("x", "gui_costumes_right-arrow"), Config.getInt("y", "gui_costumes_right-arrow"), 1, 1);
                    StaticPane c = new StaticPane(Config.getInt("x", "gui_costumes_back-arrow"), Config.getInt("y", "gui_costumes_back-arrow"), 1, 1);
                    StaticPane d = new StaticPane(Config.getInt("x", "gui_costumes-"), Config.getInt("y", "gui_costumes"), 1, 1);
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
                    gui.addPane(a);
                    gui.addPane(b);
                    gui.addPane(c);

                    gui.show(player);
                }
                return true;
            }
        }
        return true;
    }
}
