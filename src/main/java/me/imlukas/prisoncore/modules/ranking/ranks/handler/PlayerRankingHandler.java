package me.imlukas.prisoncore.modules.ranking.ranks.handler;

import me.imlukas.prisoncore.modules.database.DatabaseModule;
import me.imlukas.prisoncore.modules.database.user.PrisonUser;
import me.imlukas.prisoncore.modules.database.user.UserManager;
import me.imlukas.prisoncore.modules.ranking.RankingModule;
import me.imlukas.prisoncore.modules.ranking.ranks.PlayerRanking;
import me.imlukas.prisoncore.modules.ranking.prestige.data.PrestigeData;
import me.imlukas.prisoncore.modules.ranking.ranks.data.RankData;
import me.imlukas.prisoncore.modules.ranking.ranks.registry.PlayerRankingRegistry;
import me.imlukas.prisoncore.modules.ranking.ranks.registry.PrestigeDataRegistry;
import me.imlukas.prisoncore.modules.ranking.ranks.registry.RankDataRegistry;
import org.bukkit.entity.Player;

public class PlayerRankingHandler {

    private final UserManager userManager;
    private final PlayerRankingRegistry playerRankingRegistry;
    private final RankDataRegistry rankDataRegistry;
    private final PrestigeDataRegistry prestigeDataRegistry;

    public PlayerRankingHandler(RankingModule module) {
        DatabaseModule databaseModule = module.getPlugin().getModule(DatabaseModule.class);
        this.userManager = databaseModule.getUserManager();
        this.playerRankingRegistry = module.getRankRegistry();
        this.rankDataRegistry = module.getRankDataRegistry();
        this.prestigeDataRegistry = module.getPrestigeDataRegistry();
    }

    public void load(Player player) {
        PrisonUser prisonUser = userManager.get(player.getUniqueId());
        prisonUser.fetchMultiple(Integer.class, 1, "rank", "prestige", "xp").thenAccept(fetched -> {
            RankData rankData = rankDataRegistry.get(fetched.get("rank"));

            //TODO fix thiseee
            System.out.println("Loaded rank " + rankData.getName() + " for " + player.getName());

            PrestigeData prestigeData = prestigeDataRegistry.get(fetched.get("prestige"));

            System.out.println("Loaded prestige " + prestigeData.getName() + " for " + player.getName());

            PlayerRanking playerRanking = new PlayerRanking(player.getUniqueId(), rankData, prestigeData);
            playerRankingRegistry.register(player.getUniqueId(), playerRanking);
        });
    }
}
