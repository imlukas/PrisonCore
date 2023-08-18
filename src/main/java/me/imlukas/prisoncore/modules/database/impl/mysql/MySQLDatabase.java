package me.imlukas.prisoncore.modules.database.impl.mysql;

import me.imlukas.prisoncore.modules.database.DatabaseModule;
import me.imlukas.prisoncore.modules.database.impl.FetchingDatabase;
import me.imlukas.prisoncore.modules.economy.constants.EconomyType;
import org.bukkit.entity.Player;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public class MySQLDatabase extends FetchingDatabase {

    protected MySQLDatabase(DatabaseModule databaseModule) {
        super(databaseModule);

    }

    @Override
    public CompletableFuture<Integer> fetchBalance(UUID playerId, EconomyType economyType) {
        return null;
    }

    @Override
    public void storeEconomyData(UUID playerId, EconomyType economyType, double value) {

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
