package me.imlukas.prisoncore.modules.ranking.ranks.handler;

import me.imlukas.prisoncore.modules.AbstractModule;
import me.imlukas.prisoncore.modules.ranking.RankingModule;
import me.imlukas.prisoncore.modules.ranking.prestige.data.PrestigeData;
import me.imlukas.prisoncore.modules.ranking.ranks.data.RankData;
import me.imlukas.prisoncore.modules.ranking.ranks.registry.PrestigeDataRegistry;
import me.imlukas.prisoncore.modules.ranking.ranks.registry.RankDataRegistry;
import me.imlukas.prisoncore.utils.storage.FileHandler;
import org.bukkit.configuration.ConfigurationSection;

public class RankDataHandler extends FileHandler {

    private final RankDataRegistry rankDataRegistry;
    private final PrestigeDataRegistry prestigeDataRegistry;

    public RankDataHandler(RankingModule module) {
        super(module, "ranks.yml");
        this.rankDataRegistry = module.getRankDataRegistry();
        this.prestigeDataRegistry = module.getPrestigeDataRegistry();
        load();
    }

    @Override
    public void load() {
        ConfigurationSection rankSection = getConfiguration().getConfigurationSection("ranks");

        for (String key : rankSection.getKeys(false)) {
            ConfigurationSection section = rankSection.getConfigurationSection(key);
            int id = Integer.parseInt(key);
            RankData rankData = new RankData(
                    id,
                    section.getString("name"),
                    section.getInt("price"),
                    section.getString("prefix"),
                    section.getString("suffix")
            );

            rankDataRegistry.register(id, rankData);
            System.out.println("Registered rank " + rankData.getName() + " with id " + id);
        }

        ConfigurationSection prestigeSection = getConfiguration().getConfigurationSection("prestiges");

        for (String key : prestigeSection.getKeys(false)) {
            ConfigurationSection section = prestigeSection.getConfigurationSection(key);

            int id = Integer.parseInt(key);
            PrestigeData prestigeData = new PrestigeData(
                    id,
                    section.getString("name"),
                    section.getInt("price"),
                    section.getString("prefix"),
                    section.getString("suffix")
            );

            prestigeDataRegistry.register(id, prestigeData);
            System.out.println("Registered prestige " + prestigeData.getName() + " with id " + id);
        }
    }
}
