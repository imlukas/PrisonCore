package me.imlukas.prisoncore.modules.database.impl.mysql.table;

import me.imlukas.prisoncore.modules.database.impl.mysql.MySQLDatabase;
import me.imlukas.prisoncore.modules.database.impl.mysql.column.ColumnData;
import me.imlukas.prisoncore.modules.database.impl.mysql.column.SQLColumn;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.*;
import java.util.concurrent.CompletableFuture;

public class SQLTable {

    private final String identifier;
    private final MySQLDatabase provider;
    private final Map<String, SQLColumn> columns = new HashMap<>();

    public SQLTable(String identifier, MySQLDatabase provider) {
        this.identifier = identifier;
        this.provider = provider;
    }

    public CompletableFuture<Void> addColumn(ColumnData... columnList) {
        List<CompletableFuture<Void>> futures = new ArrayList<>();
        for (ColumnData data : columnList) {
            columns.put(identifier, new SQLColumn(this, data));

            futures.add(provider.getConnection().thenAccept(connection -> {
                try {
                    // if the column already exists, don't add it
                    if (connection.getMetaData().getColumns(null, null, identifier, data.getName()).next())
                        return;

                    Object value = data.getData();

                    // value can be the length of a varchar, or the precision of a decimal, or just null
                    String valueString = value == null ? "" : "(" + value + ")";

                    connection.createStatement().executeUpdate("ALTER TABLE " + identifier + " ADD COLUMN " + data.getName() + " " + data.getType().name() + valueString);
                    // mariadb is giving me a headache
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }));
        }

        return CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]));
    }

    public CompletableFuture<Void> create(String creationQuery) {
        return provider.getConnection().thenAccept(connection -> {
            try {
                connection.createStatement().executeUpdate(creationQuery);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public CompletableFuture<Void> create() {
        return create("CREATE TABLE IF NOT EXISTS " + identifier + " (id INT NOT NULL AUTO_INCREMENT, PRIMARY KEY (id))");
    }

    public String getColumns(Set<String> columns) {
        StringBuilder builder = new StringBuilder();

        for (String column : columns) {
            builder.append(column).append(", ");
        }

        return builder.substring(0, builder.length() - 2);
    }

    public String getValues(Collection<Object> values) {
        StringBuilder builder = new StringBuilder();

        for (Object value : values) {

            if (value instanceof String) {
                value = "'" + value + "'";
            }
            builder.append(value).append(", ");
        }

        return builder.substring(0, builder.length() - 2);
    }


    public CompletableFuture<Void> insertOnDuplicate(Map<String, Object> data, String onDuplicate) {
        return provider.getConnection().thenAccept(connection -> {
            try {
                connection.createStatement().executeUpdate("INSERT INTO " + identifier + " (" + getColumns(data.keySet()) + ") " +
                        "VALUES (" + getValues(data.values()) + ") ON DUPLICATE KEY UPDATE " + onDuplicate);
            } catch (Exception e) {
                System.err.println("Error inserting data into table " + identifier + ": " + e.getMessage());
            }
        });
    }

    public CompletableFuture<Void> insert(Map<String, Object> data, String whereClause) {
        return provider.getConnection().thenAccept(connection -> {
            try {
                connection.createStatement().executeUpdate("INSERT INTO " + identifier + " (" + getColumns(data.keySet()) + ") " +
                        "VALUES (" + getValues(data.values()) + ") WHERE " + whereClause);
            } catch (Exception e) {
                System.err.println("Error inserting data into table " + identifier + ": " + e.getMessage());
            }
        });
    }

    public CompletableFuture<Void> insert(Map<String, Object> data) {
        return provider.getConnection().thenAccept(connection -> {
            try {
                connection.createStatement().executeUpdate("INSERT INTO " + identifier + " (" + getColumns(data.keySet()) + ")" +
                        " VALUES (" + getValues(data.values()) + ")");
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public CompletableFuture<ResultSet> executeQuery(String query, Object... args) {
        return provider.getConnection().thenApply(connection -> {
            try {
                PreparedStatement statement = connection.prepareStatement(query);

                for (int index = 0; index < args.length; index++) {
                    Object queryArgument = args[index];

                    if (queryArgument instanceof String stringArgument) {
                        statement.setString(index + 1, stringArgument);
                        continue;
                    }

                    statement.setObject(index + 1, args[index]);
                }

                if (query.contains("SELECT") || query.contains("select") || query.contains("Select")) {
                    return statement.executeQuery();
                }

                statement.executeUpdate();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        });
    }


}
