package me.imlukas.prisoncore.modules.ranking.ranks;

import java.util.UUID;

public class PlayerRanking {

    private final UUID playerId;
    private int rank;
    private int prestige;

    public PlayerRanking(UUID playerId, int currentRank, int currentPrestige) {
        this.playerId = playerId;
        this.rank = currentRank;
        this.prestige = currentPrestige;
    }

    public UUID getPlayerId() {
        return playerId;
    }

    public int getPrestige() {
        return prestige;
    }

    public int getRank() {
        return rank;
    }

    public void setPrestige(int prestige) {
        this.prestige = prestige;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }
}
