package info.netpex.autographs;

import info.netpex.autographs.listeners.ItemBlockingEvents;
import info.netpex.autographs.listeners.PlayerInteractEvent;
import info.netpex.autographs.listeners.PlayerJoinEvent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

public final class Autographs extends JavaPlugin {

    private static Autographs plugin;

    @Override
    public void onEnable() {
      plugin = this;

      this.saveDefaultConfig();


    if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
        getServer().getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&2PlaceholderAPI loaded!"));
    } else {
        getServer().getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&4Could not find PlaceholderAPI! This plugin is required."));
        Bukkit.getPluginManager().disablePlugin(this);
    }

    if (Bukkit.getPluginManager().getPlugin("HeadDatabase") != null) {
        getServer().getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&2HeadDatabase loaded!"));
    } else {
        getServer().getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&6Could not find HeadDatabase! This plugin is high recommended."));
        this.getConfig().set("options.useHeadDataBase", false);
    }

    this.saveConfig();
    this.getCommand("autographs").setExecutor(new Commands());

      // listeners
        this.getServer().getPluginManager().registerEvents(new PlayerJoinEvent(), this);
        this.getServer().getPluginManager().registerEvents(new PlayerInteractEvent(), this);
        this.getServer().getPluginManager().registerEvents(new ItemBlockingEvents(), this);
    }

    public static Autographs getPlugin() {
        return plugin;
    }
}
