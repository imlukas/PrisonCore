package me.imlukas.prisoncore.modules.ranking.ranks;

import me.imlukas.prisoncore.modules.ranking.prestige.data.PrestigeData;
import me.imlukas.prisoncore.modules.ranking.ranks.data.RankData;

import java.util.UUID;

public class PlayerRanking {

    private final UUID playerId;
    private RankData rank;
    private PrestigeData prestige;

    public PlayerRanking(UUID playerId, RankData currentRank, PrestigeData currentPrestige) {
        this.playerId = playerId;
        this.rank = currentRank;
        this.prestige = currentPrestige;
    }

    public UUID getPlayerId() {
        return playerId;
    }

    public PrestigeData getPrestige() {
        return prestige;
    }

    public RankData getRank() {
        return rank;
    }

    public void setPrestige(PrestigeData prestige) {
        this.prestige = prestige;
    }

    public void setRank(RankData rank) {
        this.rank = rank;
    }
}
