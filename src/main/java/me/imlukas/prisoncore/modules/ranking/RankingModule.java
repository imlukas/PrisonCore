package me.imlukas.prisoncore.modules.ranking;

import lombok.Getter;
import me.imlukas.prisoncore.modules.AbstractModule;
import me.imlukas.prisoncore.modules.ranking.ranks.handler.RanksHandler;
import me.imlukas.prisoncore.modules.ranking.ranks.registry.RankRegistry;

@Getter
public class RankingModule extends AbstractModule {

    private RankRegistry rankRegistry;
    private RanksHandler ranksHandler;

    @Override
    public void onEnable() {
        rankRegistry = new RankRegistry();
        ranksHandler = new RanksHandler(this);
    }

    @Override
    public String getIdentifier() {
        return "ranking";
    }
}
