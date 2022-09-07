package info.netpex.autographs.utility;

import info.netpex.autographs.Autographs;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.Arrays;

public class Config extends JavaPlugin {

    public static String getString(String value, String location) {

        String[] path = location.split("_");
        ArrayList<String> values = new ArrayList<>(Arrays.asList(path));

        ConfigurationSection val = Autographs.getPlugin().getConfig().getConfigurationSection(values.get(0));

        if (values.size() > 0) {
            values.remove(values.get(0));

            for (String arg : values) {

                val = val.getConfigurationSection(arg);
            }
        }

        return val.getString(value);
    }

    public static Integer getInt(String value, String location) {

        String[] path = location.split("_");
        ArrayList<String> values = new ArrayList<>(Arrays.asList(path));

        ConfigurationSection val = Autographs.getPlugin().getConfig().getConfigurationSection(values.get(0));

        if (values.size() > 0) {
            values.remove(values.get(0));

            for (String arg : values) {

                val = val.getConfigurationSection(arg);
            }
        }

        return val.getInt(value);
    }

    public static Boolean getBool(String value, String location) {

        String[] path = location.split("_");
        ArrayList<String> values = new ArrayList<>(Arrays.asList(path));

        ConfigurationSection val = Autographs.getPlugin().getConfig().getConfigurationSection(values.get(0));

        if (values.size() > 0) {
            values.remove(values.get(0));

            for (String arg : values) {

                val = val.getConfigurationSection(arg);
            }
        }
        return val.getBoolean(value);
    }
}
