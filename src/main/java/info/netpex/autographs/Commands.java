package info.netpex.autographs;

import com.github.stefvanschie.inventoryframework.gui.GuiItem;
import com.github.stefvanschie.inventoryframework.gui.type.ChestGui;
import com.github.stefvanschie.inventoryframework.pane.PaginatedPane;
import com.github.stefvanschie.inventoryframework.pane.StaticPane;
import info.netpex.autographs.frames.Costumes;
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
                    ChestGui gui = Costumes.create(player);
                    gui.show(player);
                }
                return true;
            }
        }
        return true;
    }
}
