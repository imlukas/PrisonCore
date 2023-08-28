package me.imlukas.prisoncore.modules.economy.manager.impl;

import me.imlukas.prisoncore.PrisonCore;
import me.imlukas.prisoncore.modules.database.DatabaseModule;
import me.imlukas.prisoncore.modules.database.impl.PrisonDatabase;
import me.imlukas.prisoncore.modules.economy.constants.EconomyType;
import me.imlukas.prisoncore.modules.economy.data.impl.TokensData;
import me.imlukas.prisoncore.modules.economy.manager.EconomyManager;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class TokensManager implements EconomyManager<TokensData> {

    private final PrisonDatabase prisonDatabase;
    private final Map<UUID, TokensData> pointsPlayerData = new HashMap<>();

    public TokensManager(PrisonCore plugin) {
        this.prisonDatabase = plugin.getModule(DatabaseModule.class).getDatabaseRegistry().getFetchingDatabase();
    }

    @Override
    public String getCommonIdentifier() {
        return "tokens";
    }

    @Override
    public EconomyType getType() {
        return EconomyType.TOKENS;
    }

    @Override
    public TokensData getData(UUID playerId) {
        if (!isLoaded(playerId)) {
            loadData(playerId);
        }

        return pointsPlayerData.get(playerId);
    }

    @Override
    public void addData(TokensData data) {
        pointsPlayerData.put(data.getPlayerId(), data);
    }

    @Override
    public void removeData(UUID playerId) {
        pointsPlayerData.remove(playerId);
    }

    @Override
    public void saveData(UUID playerId) {
        prisonDatabase.store(playerId, "tokens-balance", getData(playerId).getBalance());
    }

    @Override
    public void loadData(UUID playerId) {
        prisonDatabase.fetch(playerId, "tokens-balance", Integer.class).thenAccept(value -> {
            if (value != null) {
                addData(new TokensData(playerId, value));
            }
        });
    }

    @Override
    public boolean isLoaded(UUID playerId) {
        return pointsPlayerData.containsKey(playerId);
    }


}
