package me.imlukas.prisoncore.modules.database.impl;

import me.imlukas.prisoncore.modules.database.Database;
import me.imlukas.prisoncore.modules.database.DatabaseModule;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public abstract class PrisonDatabase implements Database {

    protected DatabaseModule databaseModule;

    protected PrisonDatabase(DatabaseModule databaseModule) {
        this.databaseModule = databaseModule;
    }

    public abstract <T> CompletableFuture<T> fetch(UUID playerId, String key, Class<T> clazz);

    public abstract void store(UUID playerId, String key, Object value);
}
