package info.netpex.autographs;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.*;

public class Data extends JavaPlugin {

    private final Autographs plugin;

    public Data(Autographs plugin, MySQL connectionPoolManager) {
        this.plugin = plugin;
    }

    Map<UUID, HashMap> dataCache = new HashMap<>();

    class autograph {
        public autograph(UUID identifier, String dateSigned, Integer signedCount, UUID signer) {
            this.player = Bukkit.getPlayer(identifier);
            this.date = new SimpleDateFormat("dd/MM/yyyy").parse(dateSigned);
            this.count = signedCount;
            this.signer = Bukkit.getPlayer(signer);
        }

        public Player getPlayer() {return this.player;}
        public Date getDate() {return this.date;}
        public Integer getCount() {return this.count;}
        public Player getSigner() {return this.signer;}

        private Player player;
        private Date date;
        private Integer count;
        private Player signer;
    }

    public static String createTableSyntax(String tableName, LinkedHashMap<String, String> columns) {

        StringBuilder line = new StringBuilder("CREATE TABLE IF NOT EXISTS " + tableName + "(");
        boolean primaryDone = false;
        for (String key : columns.keySet()) {

            line.append(key);
            line.append(" ");
            line.append(columns.get(key));

            if (!primaryDone) {
                line.append(" PRIMARY KEY");
                primaryDone = true;
            }

            line.append(",");

        }
        line.setCharAt(line.length()-1, ')');

        return String.valueOf(line);

    }

    public static String addRowSyntax(String tableName, String keyName, String keyValue) {
        return "INSERT INTO " + tableName + "(" + keyName + ") " + "VALUES (" + keyValue + ")";
    }

    public static String checkIfKeyExistsSyntax(String tableName, String keyName, String keyValue) {
        return "SELECT * FROM " + tableName + " WHERE " + keyName + "=" + keyValue;
    }

    public static String removeRowSyntax(String tableName, String keyName, String keyValue) {
        return "DELETE FROM " + tableName + " WHERE " + keyName + "=" + keyValue;
    }

    public void createTable(String name, LinkedHashMap<String, String> columns) {
        new BukkitRunnable() {
            @Override
            public void run() {
                Connection connection = null;
                PreparedStatement ps = null;

                try {
                    connection = MySQL.getConnection();
                    ps = connection.prepareStatement(createTableSyntax(name, columns));
                    ps.executeUpdate();
                } catch (SQLException e) {
                    e.printStackTrace();
                } finally {
                    MySQL.close(connection, ps, null);
                }
            }
        }.runTaskAsynchronously(plugin);
    }

    public void addRow(String tableName, String keyName, String keyValue) {
        new BukkitRunnable() {
            @Override
            public void run() {
                Connection connection = null;
                PreparedStatement ps = null;

                try {
                    connection = MySQL.getConnection();
                    ps = connection.prepareStatement(addRowSyntax(tableName, keyName, keyValue));
                    ps.executeUpdate();
                } catch (SQLException e) {
                    e.printStackTrace();
                } finally {
                    MySQL.close(connection, ps, null);
                }

            }
        }.runTaskAsynchronously(plugin);
    }

    public void removeRow(String tableName, String keyName, String keyValue) {
        new BukkitRunnable() {
            @Override
            public void run() {
                Connection connection = null;
                PreparedStatement ps = null;

                try {
                    connection = MySQL.getConnection();
                    ps = connection.prepareStatement(removeRowSyntax(tableName, keyName, keyValue));
                    ps.executeUpdate();
                } catch (SQLException e) {
                    e.printStackTrace();
                } finally {
                    MySQL.close(connection, ps, null);
                }

            }
        }.runTaskAsynchronously(plugin);
    }

    public void createData(String rowName, ArrayList<String> columns, ResultSet resultSet ) {
        HashMap<String, String> costumeData = new HashMap<>();
        for (String key : columns) {
            costumeData.put(key, resultSet.getString(key));
        }
        dataCache.put(UUID.fromString(rowName), costumeData);
    }

    public static Map getData(Player player) {

    }

    public ResultSet getValues(String tableName, ArrayList<String> columns, String keyName, String keyValue) {
        ResultSet resultSet = null;
        Connection connection = null;
        PreparedStatement ps = null;
        try {
            connection = MySQL.getConnection();
            ps = connection.prepareStatement(checkIfKeyExistsSyntax(tableName, keyName, keyValue));
            resultSet = ps.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            MySQL.close(connection, ps, null);
        }
        return resultSet;
    }


}
