package me.imlukas.prisoncore.modules.economy.manager.impl;

import me.imlukas.prisoncore.PrisonCore;
import me.imlukas.prisoncore.modules.database.DatabaseModule;
import me.imlukas.prisoncore.modules.database.impl.FetchingDatabase;
import me.imlukas.prisoncore.modules.economy.constants.EconomyType;
import me.imlukas.prisoncore.modules.economy.data.impl.PointsPlayerData;
import me.imlukas.prisoncore.modules.economy.manager.EconomyManager;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class TokensManager implements EconomyManager<PointsPlayerData> {

    private final FetchingDatabase fetchingDatabase;
    private final Map<UUID, PointsPlayerData> pointsPlayerData = new HashMap<>();

    public TokensManager(PrisonCore plugin) {
        this.fetchingDatabase = plugin.getModule(DatabaseModule.class).getDatabaseRegistry().getFetchingDatabase();
    }

    @Override
    public String getCommonIdentifier() {
        return "points";
    }

    @Override
    public EconomyType getType() {
        return EconomyType.TOKENS;
    }

    @Override
    public PointsPlayerData getData(UUID playerId) {
        if (!isLoaded(playerId)) {
            loadData(playerId);
        }

        return pointsPlayerData.get(playerId);
    }

    @Override
    public void addData(PointsPlayerData data) {
        pointsPlayerData.put(data.getPlayerId(), data);
    }

    @Override
    public void removeData(UUID playerId) {
        pointsPlayerData.remove(playerId);
    }

    @Override
    public void saveData(UUID playerId) {
        fetchingDatabase.storeEconomyData(playerId, getType(), getData(playerId).getBalance());
    }

    @Override
    public void loadData(UUID playerId) {
        fetchingDatabase.fetchBalance(playerId, getType()).thenAccept(value -> {
            if (value != null) {
                addData(new PointsPlayerData(playerId, value));
            }
        });
    }

    @Override
    public boolean isLoaded(UUID playerId) {
        return pointsPlayerData.containsKey(playerId);
    }


}
