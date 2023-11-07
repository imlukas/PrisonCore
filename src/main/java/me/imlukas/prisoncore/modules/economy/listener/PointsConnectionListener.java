package me.imlukas.prisoncore.modules.economy.listener;

import me.imlukas.prisoncore.modules.economy.EconomyModule;
import me.imlukas.prisoncore.modules.economy.manager.EconomyManager;
import me.imlukas.prisoncore.modules.economy.manager.impl.BaseManager;
import me.imlukas.prisoncore.modules.economy.manager.registry.EconomyManagerRegistry;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.List;

public class PointsConnectionListener implements Listener {

    private final EconomyManagerRegistry managerRegistry;

    public PointsConnectionListener(EconomyModule economyModule) {
        this.managerRegistry = economyModule.getEconomyManagerRegistry();
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        List<BaseManager> economyManagers = managerRegistry.getEconomyManagers();

        for (BaseManager economyManager : economyManagers) {
            economyManager.loadData(event.getPlayer().getUniqueId());
        }

    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        List<BaseManager> economyManagers = managerRegistry.getEconomyManagers();

        for (BaseManager economyManager : economyManagers) {
            economyManager.saveData(event.getPlayer().getUniqueId());
        }
    }
}
