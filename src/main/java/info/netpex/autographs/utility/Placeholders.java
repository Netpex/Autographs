package info.netpex.autographs.utility;

import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;

public class Placeholders extends JavaPlugin {

    public static String translate(Player p, String s) {
        Map<String, String> placeholders = new HashMap<String, String>();
        placeholders.put("%prefix%", Config.getString("prefix","options" ));
        placeholders.put("%character%", PersistentData.get(p, "Costume"));

        for (Map.Entry<String, String> placeholder : placeholders.entrySet()) {
            s = s.replace(placeholder.getKey(), placeholder.getValue());
        }
        return PlaceholderAPI.setPlaceholders(p, org.bukkit.ChatColor.translateAlternateColorCodes('&', s));
    }
}
