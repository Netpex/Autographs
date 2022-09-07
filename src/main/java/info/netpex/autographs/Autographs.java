package info.netpex.autographs;

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
        System.out.println(ChatColor.translateAlternateColorCodes('&', "&2PlaceholderAPI loaded!"));
    } else {
        System.out.println(ChatColor.translateAlternateColorCodes('&', "&4Could not find PlaceholderAPI! This plugin is required."));
        Bukkit.getPluginManager().disablePlugin(this);
    }

    if (Bukkit.getPluginManager().getPlugin("HeadDatabase") != null) {
        System.out.println(ChatColor.translateAlternateColorCodes('&', "&2HeadDatabase loaded!"));
    } else {
        System.out.println(ChatColor.translateAlternateColorCodes('&', "&6Could not find HeadDatabase! This plugin is high recommended."));
        this.getConfig().set("options.userHeadDataBase", false);
    }

    this.saveDefaultConfig();
      this.getCommand("autographs").setExecutor(new Commands());

      // listeners
        this.getServer().getPluginManager().registerEvents(new PlayerJoinEvent(), this);
        this.getServer().getPluginManager().registerEvents(new PlayerInteractEvent(), this);
    }

    public static Autographs getPlugin() {
        return plugin;
    }
}
