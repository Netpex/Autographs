package info.netpex.autographs;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.bukkit.configuration.Configuration;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MySQL extends JavaPlugin {

    private final Autographs plugin;

    static private HikariDataSource dataSource;

    public MySQL(Autographs plugin) {
        this.plugin = plugin;
    }

    public void setupPool() {
        // Get database config options
        Configuration config = plugin.getConfig();
        String hostname = config.getString("database.host");
        String port = config.getString("database.port");
        String database = config.getString("database.database");
        String username = config.getString("database.username");
        String password = config.getString("database.password");
        int minimumConnections = config.getInt("database.min-connections");
        int maximumConnections = config.getInt("database.max-connections");
        int connectionTimeout = config.getInt("database.connection-timeout")*1000;
        int idleTimeout = config.getInt("database.idle-timeout")*60000;
        int keepaliveTime = config.getInt("database.keepaliveTime")*60000;

        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setJdbcUrl(
                "jdbc:mysql://" +
                        hostname +
                        ":" +
                        port +
                        "/" +
                        database
        );
        hikariConfig.setUsername(username);
        hikariConfig.setPassword(password);
        hikariConfig.setMinimumIdle(minimumConnections);
        hikariConfig.setMaximumPoolSize(maximumConnections);
        hikariConfig.setIdleTimeout(idleTimeout);
        hikariConfig.setKeepaliveTime(keepaliveTime);
        hikariConfig.setConnectionTimeout(connectionTimeout);
        dataSource = new HikariDataSource(hikariConfig);
    }

    public static Connection getConnection() {
        try {
            return dataSource.getConnection();
        } catch (SQLException e) {
            return null;
        }
    }

    public Boolean isConnected() {
        return getConnection() != null;
    }

    public static void close(Connection conn, PreparedStatement ps, ResultSet res) { //
//        try (conn; ps; res) {} catch (SQLException ignored) {}
        if (conn != null) try { conn.close(); } catch (SQLException ignored) {}
        if (ps != null) try { ps.close(); } catch (SQLException ignored) {}
        if (res != null) try { res.close(); } catch (SQLException ignored) {}
    }

    public void closePool() {
        if (dataSource != null && !dataSource.isClosed()) dataSource.close();
    }

}
