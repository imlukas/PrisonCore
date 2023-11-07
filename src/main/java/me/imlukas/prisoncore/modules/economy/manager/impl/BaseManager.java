package me.imlukas.prisoncore.modules.economy.manager.impl;

import me.imlukas.prisoncore.PrisonCore;
import me.imlukas.prisoncore.modules.database.DatabaseModule;
import me.imlukas.prisoncore.modules.database.impl.PrisonDatabase;
import me.imlukas.prisoncore.modules.economy.data.PlayerEconomyData;
import me.imlukas.prisoncore.modules.economy.manager.EconomyManager;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public abstract class BaseManager implements EconomyManager {

    private final PrisonDatabase prisonDatabase;
    private final Map<UUID, PlayerEconomyData> playerData = new HashMap<>();

    protected BaseManager(PrisonCore plugin) {
        this.prisonDatabase = plugin.getModule(DatabaseModule.class).getDatabaseRegistry().getFetchingDatabase();
    }

    @Override
    public PlayerEconomyData getDataInstance(UUID playerId, Integer balance) {
        return new PlayerEconomyData(playerId, balance, getSign());
    }

    @Override
    public CompletableFuture<PlayerEconomyData> getData(UUID playerId) {
        if (!isLoaded(playerId)) {
            return loadData(playerId);
        }

        return CompletableFuture.completedFuture(playerData.get(playerId));
    }

    @Override
    public void saveData(UUID playerId) {
        getData(playerId).thenAccept(data -> {
            if (data == null) {
                return;
            }

            prisonDatabase.store(playerId, getIdentifier() + "-balance", data.getBalance());
        });
    }

    @Override
    public CompletableFuture<PlayerEconomyData> loadData(UUID playerId) {
        return prisonDatabase.fetch(playerId, getIdentifier() + "-balance", Integer.class).thenApply(value -> {
            PlayerEconomyData data = getDataInstance(playerId, Objects.requireNonNullElse(value, 0));
            addData(data);
            return data;
        });
    }

    @Override
    public void addData(PlayerEconomyData data) {
        playerData.put(data.getPlayerId(), data);
        System.out.println("Added EconomyData for " + data.getPlayerId());
    }

    @Override
    public void removeData(UUID playerId) {
        playerData.remove(playerId);
    }

    @Override
    public boolean isLoaded(UUID playerId) {
        return playerData.containsKey(playerId);
    }
}
