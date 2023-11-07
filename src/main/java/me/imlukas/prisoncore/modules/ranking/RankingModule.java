package me.imlukas.prisoncore.modules.ranking;

import lombok.Getter;
import me.imlukas.prisoncore.modules.AbstractModule;
import me.imlukas.prisoncore.modules.database.DatabaseModule;
import me.imlukas.prisoncore.modules.ranking.ranks.handler.PlayerRankingHandler;
import me.imlukas.prisoncore.modules.ranking.ranks.handler.RankDataHandler;
import me.imlukas.prisoncore.modules.ranking.ranks.registry.PlayerRankingRegistry;
import me.imlukas.prisoncore.modules.ranking.ranks.registry.PrestigeDataRegistry;
import me.imlukas.prisoncore.modules.ranking.ranks.registry.RankDataRegistry;

@Getter
public class RankingModule extends AbstractModule {

    private PlayerRankingRegistry rankRegistry;
    private RankDataRegistry rankDataRegistry;
    private PrestigeDataRegistry prestigeDataRegistry;
    private RankDataHandler rankDataHandler;
    private PlayerRankingHandler playerRankingHandler;


    @Override
    public void onEnable() {
        rankRegistry = new PlayerRankingRegistry(this);
        rankDataRegistry = new RankDataRegistry();
        prestigeDataRegistry = new PrestigeDataRegistry();
        rankDataHandler = new RankDataHandler(this);
        playerRankingHandler = new PlayerRankingHandler(this);

        registerListener(new JoinListener(this));

        System.out.println("[PrisonCore] RankingModule enabled");
    }

    @Override
    public String getIdentifier() {
        return "ranking";
    }
}
