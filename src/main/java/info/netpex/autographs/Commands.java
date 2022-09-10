package info.netpex.autographs;

import com.github.stefvanschie.inventoryframework.gui.type.ChestGui;
import info.netpex.autographs.frames.CostumeDetail;
import info.netpex.autographs.frames.CostumesFrame;
import info.netpex.autographs.utility.PersistentData;
import info.netpex.autographs.utility.Placeholders;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

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
                    ChestGui gui = CostumesFrame.create(player);
                    gui.show(player);
                } else if (args[0].equalsIgnoreCase("rename")) {
                    Collection<String> costumesPath = Autographs.getPlugin().getConfig().getConfigurationSection("costumes").getKeys(false); //Iterable collection of costumes

                    ArrayList<String> costumes = new ArrayList<String>(Arrays.asList()); //Empty array for costumes

                    for (String c : costumesPath) {
                        if (args[1].equalsIgnoreCase(c)) {
                            if (args.length > 1) {
                                System.out.println(args[2]);
                                String argsToOneString = String.join(" ", Arrays.asList(args).subList(3, args.length).toArray(new String[]{}));
                                Autographs.getPlugin().getConfig().set("costumes." + c + ".name", argsToOneString);
                                Autographs.getPlugin().saveConfig();
                                player.sendMessage(Placeholders.translate(player, "%prefix% &eDisplay-name &7for &r" + c + " &6updated &7to &r" + argsToOneString + "&7!"));
                                if (args[2].equalsIgnoreCase("y")) {
                                    ChestGui Details = CostumeDetail.create(player, "costumes." + c);
                                    player.closeInventory();
                                    Details.show(player);
                                }
                            }
                        }
                    }
                } else if (args[0].equalsIgnoreCase("delete")) {
                    Collection<String> costumesPath = Autographs.getPlugin().getConfig().getConfigurationSection("costumes").getKeys(false); //Iterable collection of costumes

                    ArrayList<String> costumes = new ArrayList<String>(Arrays.asList()); //Empty array for costumes

                    for (String c : costumesPath) {
                        if (args[1].equalsIgnoreCase(c)) {
                            if (args.length > 1) {
                                System.out.println(args[2]);
                                Autographs.getPlugin().getConfig().set("costumes." + c, null);
                                Autographs.getPlugin().saveConfig();
                                player.sendMessage(Placeholders.translate(player, "%prefix% &eCostume &r" + c + " &6was &asuccessfully &cDeleted&7!"));
                                if (args[2].equalsIgnoreCase("y")) {
                                    ChestGui gui = CostumesFrame.create(player);
                                    player.closeInventory();
                                    gui.show(player);
                                }
                            }
                        }
                    }
                }
                return true;
            }
        }
        return true;
    }
}
