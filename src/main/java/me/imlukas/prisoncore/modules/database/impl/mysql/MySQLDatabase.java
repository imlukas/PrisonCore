package me.imlukas.prisoncore.modules.database.impl.mysql;

import me.imlukas.prisoncore.modules.database.DatabaseModule;
import me.imlukas.prisoncore.modules.database.impl.PrisonDatabase;
import me.imlukas.prisoncore.modules.economy.constants.EconomyType;
import me.imlukas.prisoncore.modules.economy.manager.EconomyManager;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public class MySQLDatabase extends PrisonDatabase {



    protected MySQLDatabase(DatabaseModule databaseModule) {
        super(databaseModule);

    }

    @Override
    public <T> CompletableFuture<T> fetch(UUID playerId, String key, Class<T> clazz) {
        return null;
    }

    @Override
    public <T> CompletableFuture<Map<String, T>> fetchMultiple(UUID playerId, Class<T> clazz, String... keys) {
        return null;
    }

    @Override
    public void store(UUID playerId, String key, Object value) {

    }

    @Override
    public String getIdentifier() {
        return "MySQL";
    }

    @Override
    public boolean connect() {
        return false;
    }

    @Override
    public void disconnect() {

    }

    @Override
    public boolean isConnected() {
        return false;
    }
}
