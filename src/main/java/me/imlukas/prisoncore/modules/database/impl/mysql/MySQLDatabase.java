package me.imlukas.prisoncore.modules.database.impl.mysql;

import me.imlukas.prisoncore.modules.database.DatabaseModule;
import me.imlukas.prisoncore.modules.database.impl.PrisonDatabase;
import me.imlukas.prisoncore.modules.database.impl.mysql.table.SQLTable;
import org.bukkit.configuration.ConfigurationSection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.*;
import java.util.concurrent.CompletableFuture;

public class MySQLDatabase extends PrisonDatabase {

    private final String host, database, username, password;
    private final int port;
    private final Map<String, SQLTable> tables = new HashMap<>();
    private Connection connection;

    public MySQLDatabase(DatabaseModule module) {
        super(module);

        ConfigurationSection section = module.getConfig().getConfigurationSection("database");
        this.host = section.getString("host");
        this.port = section.getInt("port");
        this.database = section.getString("database");
        this.username = section.getString("username");
        this.password = section.getString("password");
        connect();
    }

    public List<SQLTable> createTable(String... names) {
        List<SQLTable> tables = new ArrayList<>();
        for (String name : names) {
            tables.add(getOrCreateTable(name));
        }
        return tables;
    }

    public SQLTable createTable(String name, String creationQuery) {
        SQLTable table = new SQLTable(name, this);
        table.create(creationQuery);
        tables.put(name, table);
        return table;
    }

    public SQLTable getOrCreateTable(String name) {
        if (tables.containsKey(name)) {
            return tables.get(name);
        }

        SQLTable table = new SQLTable(name, this);
        table.create();
        tables.put(name, table);
        return table;
    }

    @Override
    public <T> CompletableFuture<T> fetchOrDefault(UUID playerId, String key, Class<T> clazz, T defaultValue) {
        return fetch(playerId, key, clazz).thenApply(value -> {
            if (value == null) {
                return defaultValue;
            }
            return value;
        });
    }

    @Override
    public <T> CompletableFuture<T> fetch(UUID playerId, String key, Class<T> clazz) {
        String tableName = key.split(".")[0];
        return getOrCreateTable(tableName).executeQuery("SELECT " + key + " FROM " + tableName + " WHERE uuid = " + playerId.toString()).thenApply(resultSet -> {
            try {
                if (resultSet.next()) {
                    return (T) resultSet.getObject(key);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        });
    }

    @Override
    public <T> CompletableFuture<Map<String, T>> fetchMultiple(UUID playerId, Class<T> clazz, String... keys) {
        StringBuilder keysString = new StringBuilder();

        for (String key : keys) {
            keysString.append(key).append(", ");
        }

        String tableName = keys[0].split(".")[0];

        return getOrCreateTable(tableName).executeQuery("SELECT " + keysString + " FROM " + tableName + " WHERE uuid = " + playerId.toString()).thenApply(resultSet -> {
            try {
                Map<String, T> map = new HashMap<>();
                if (resultSet.next()) {
                    for (String key : keys) {
                        map.put(key, (T) resultSet.getObject(key));
                    }
                    return map;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        });
    }

    @Override
    public <T> CompletableFuture<Map<String, T>> fetchMultiple(UUID playerId, Class<T> clazz, T defaultValue, String... keys) {
        return fetchMultiple(playerId, clazz, keys).thenApply(values -> {
            Map<String, T> map = new HashMap<>();

            for (Map.Entry<String, T> valuesEntry : values.entrySet()) {
                if (valuesEntry.getValue() == null) {
                    map.put(valuesEntry.getKey(), defaultValue);
                }

                map.put(valuesEntry.getKey(), valuesEntry.getValue());
            }

            return map;
        });
    }

    @Override
    public void store(UUID uuid, String columnName, Object value) {
        String tableName = columnName.split(".")[0];
        getOrCreateTable(tableName).insert(Map.of(columnName, value));
    }

    @Override
    public void storeMutiple(UUID uuid, Map<String, Object> values) {
        String tableName = values.keySet().iterator().next().split(".")[0];
        getOrCreateTable(tableName).insert(values);
    }

    @Override
    public String getIdentifier() {
        return "MySQL";
    }

    public CompletableFuture<Connection> getConnection() {
        if (connection == null) {
            createConnection().thenAccept(connection -> this.connection = connection);
        }

        // if the current is not valid, create a new one
        return validateConnection(connection).thenCompose(valid -> {
            if (valid) {
                return CompletableFuture.completedFuture(connection);
            }
            return createConnection();
        });
    }

    private CompletableFuture<Boolean> validateConnection(Connection connection) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                return connection.isValid(1);
            } catch (Exception e) {
                e.printStackTrace();
            }

            return false;
        });
    }

    private CompletableFuture<Connection> createConnection() {
        return CompletableFuture.supplyAsync(() -> {
            try {
                Class.forName("com.mysql.jdbc.Driver");
                Connection connection = DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/" + database, username, password);
                System.out.println("Connected to MySQL server | Using database: " + database);

                return connection;
            } catch (Exception e) {
                System.out.println("Failed to connect to MySQL server.");
                e.printStackTrace();
            }
            return null;
        });
    }

    @Override
    public boolean connect() {
        createConnection().thenAccept(connection -> this.connection = connection);
        return true;
    }

    @Override
    public void disconnect() {
        connection = null;
    }

    @Override
    public boolean isConnected() {
        return connection != null;
    }
}
