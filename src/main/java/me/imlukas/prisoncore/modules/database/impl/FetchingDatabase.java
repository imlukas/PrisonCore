package me.imlukas.prisoncore.modules.database.impl;

import me.imlukas.prisoncore.modules.database.Database;
import me.imlukas.prisoncore.modules.database.DatabaseModule;
import me.imlukas.prisoncore.modules.economy.constants.EconomyType;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public abstract class FetchingDatabase implements Database {

    protected DatabaseModule databaseModule;

    protected FetchingDatabase(DatabaseModule databaseModule) {
        this.databaseModule = databaseModule;
    }

    public abstract CompletableFuture<Integer> fetchBalance(UUID playerId, EconomyType economyType);

    public abstract void storeEconomyData(UUID playerId, EconomyType economyType, double value);
}
