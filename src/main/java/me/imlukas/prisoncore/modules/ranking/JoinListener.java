package me.imlukas.prisoncore.modules.ranking;

import me.imlukas.prisoncore.modules.ranking.ranks.handler.PlayerRankingHandler;
import me.imlukas.prisoncore.modules.ranking.ranks.registry.PlayerRankingRegistry;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class JoinListener implements Listener {
    private final PlayerRankingHandler rankingHandler;
    private final PlayerRankingRegistry rankRegistry;

    public JoinListener(RankingModule module) {
        this.rankingHandler = module.getPlayerRankingHandler();
        this.rankRegistry = module.getRankRegistry();
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        rankingHandler.load(event.getPlayer());
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        rankRegistry.unregister(event.getPlayer().getUniqueId());
    }
}
