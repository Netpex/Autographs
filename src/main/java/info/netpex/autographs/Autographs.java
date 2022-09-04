package info.netpex.autographs;

import info.netpex.autographs.listeners.PlayerInteractEvent;
import info.netpex.autographs.listeners.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

public final class Autographs extends JavaPlugin {

    private static Autographs plugin;

    @Override
    public void onEnable() {
      plugin = this;

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
