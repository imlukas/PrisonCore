package me.imlukas.prisoncore.modules.ranking.ranks.handler;

import me.imlukas.prisoncore.modules.database.DatabaseModule;
import me.imlukas.prisoncore.modules.database.user.PrisonUser;
import me.imlukas.prisoncore.modules.database.user.UserManager;
import me.imlukas.prisoncore.modules.ranking.RankingModule;
import me.imlukas.prisoncore.modules.ranking.ranks.PlayerRanking;
import me.imlukas.prisoncore.modules.ranking.ranks.registry.RankRegistry;
import org.bukkit.entity.Player;

public class RanksHandler {

    private final UserManager userManager;
    private final RankRegistry rankRegistry;

    public RanksHandler(RankingModule module) {
        DatabaseModule databaseModule = module.getPlugin().getModule(DatabaseModule.class);
        this.userManager = databaseModule.getUserManager();
        this.rankRegistry = module.getRankRegistry();
    }

    public void load(Player player) {
        PrisonUser prisonUser = userManager.get(player.getUniqueId());
        prisonUser.fetchMultiple(Integer.class, "rank", "prestige").thenAccept(fetched -> {
            int rank = fetched.get("rank");
            int prestige = fetched.get("prestige");

            PlayerRanking playerRanking = new PlayerRanking(player.getUniqueId(), rank, prestige);
            rankRegistry.register(player.getUniqueId(), playerRanking);
        });
    }
}
