package info.netpex.autographs.utility;

import info.netpex.autographs.Autographs;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;

public class PersistentData extends JavaPlugin {

    public static Boolean has(Player player, String key) {
        return(player.getPersistentDataContainer().has(new NamespacedKey(Autographs.getPlugin(),  key), PersistentDataType.STRING));
    }

    public static String get(Player player, String key) {
        return(player.getPersistentDataContainer().get(new NamespacedKey(Autographs.getPlugin(),  key), PersistentDataType.STRING));
    }

    public static void set(Player player, String key, String value) {
        player.getPersistentDataContainer().set(new NamespacedKey(Autographs.getPlugin(),  key), PersistentDataType.STRING, value);
    }

    public static boolean isNumeric(String strNum) {
        if (strNum == null) {
            return false;
        }
        try {
            double d = Double.parseDouble(strNum);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }
}
