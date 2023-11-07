package me.imlukas.prisoncore.modules.ranking.ranks.registry;

import me.imlukas.prisoncore.modules.database.DatabaseModule;
import me.imlukas.prisoncore.modules.database.impl.PrisonDatabase;
import me.imlukas.prisoncore.modules.ranking.RankingModule;
import me.imlukas.prisoncore.modules.ranking.ranks.PlayerRanking;
import me.imlukas.prisoncore.modules.ranking.prestige.data.PrestigeData;
import me.imlukas.prisoncore.modules.ranking.ranks.data.RankData;
import me.imlukas.prisoncore.utils.collection.CachedRegistry;
import me.imlukas.prisoncore.utils.time.Time;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

public class PlayerRankingRegistry extends CachedRegistry<UUID, PlayerRanking>  {

    private final PrisonDatabase fetchingDatabase;
    private final RankDataRegistry rankDataRegistry;
    private final PrestigeDataRegistry prestigeDataRegistry;

    public PlayerRankingRegistry(RankingModule rankingModule) {
        super(new Time(30, TimeUnit.MINUTES));
        this.fetchingDatabase = rankingModule.getModule(DatabaseModule.class).getDatabaseRegistry().getFetchingDatabase();
        this.rankDataRegistry = rankingModule.getRankDataRegistry();
        this.prestigeDataRegistry = rankingModule.getPrestigeDataRegistry();
    }

    public CompletableFuture<PlayerRanking> fetchAndGet(UUID key) {
        return fetchingDatabase.fetchMultiple(key, Integer.class, "rank", "prestige").thenApply(values -> {
            RankData rankData = rankDataRegistry.get(values.get("rank"));
            PrestigeData prestigeData = prestigeDataRegistry.get(values.get("prestige"));

            PlayerRanking ranking = new PlayerRanking(key, rankData, prestigeData);
            register(key, ranking);
            return ranking;
        });

    }

    @Override
    public void afterFlush(PlayerRanking ranking) {
        if (ranking == null) {
            System.out.println("Ranking is null");
            return;
        }

        fetchingDatabase.storeMutiple(ranking.getPlayerId(), Map.of(
                "rank", ranking.getRank(),
                "prestige", ranking.getPrestige()
        ));
    }
}
