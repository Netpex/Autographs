package info.netpex.autographs.utility;

import info.netpex.autographs.Autographs;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.configuration.Configuration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;

public class Placeholders extends JavaPlugin {

    public static String translate(Player p, String s) {
        Configuration config = Autographs.getPlugin().getConfig();
        Map<String, String> placeholders = new HashMap<String, String>();
        placeholders.put("%prefix%", config.getString("options.prefix" ));
        placeholders.put("%character%", PersistentData.get(p, "Costume"));

        for (Map.Entry<String, String> placeholder : placeholders.entrySet()) {
            s = s.replace(placeholder.getKey(), placeholder.getValue());
        }
        return PlaceholderAPI.setPlaceholders(p, org.bukkit.ChatColor.translateAlternateColorCodes('&', s));
    }
}
