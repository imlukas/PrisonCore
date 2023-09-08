package me.imlukas.prisoncore.modules.ranking.ranks.registry;

import me.imlukas.prisoncore.modules.ranking.ranks.PlayerRanking;
import me.imlukas.prisoncore.utils.collection.CachedRegistry;
import me.imlukas.prisoncore.utils.time.Time;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class RankRegistry extends CachedRegistry<UUID, PlayerRanking>  {

    public RankRegistry() {
        super(new Time(30, TimeUnit.MINUTES));

        afterFlush(playerRanking -> {

        });
    }
}
