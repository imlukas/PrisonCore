package me.imlukas.prisoncore.modules.economy.listener;

import me.imlukas.prisoncore.modules.economy.EconomyModule;
import me.imlukas.prisoncore.modules.economy.manager.EconomyManager;
import me.imlukas.prisoncore.modules.economy.manager.EconomyManagerRegistry;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.List;

public class PointsConnectionListener implements Listener {

    private final EconomyManagerRegistry managerRegistry;

    public PointsConnectionListener(EconomyModule economyModule) {
        this.managerRegistry = economyModule.getEconomyManagerRegistry();
    }

    public void onJoin(PlayerJoinEvent event) {
        List<EconomyManager<?>> economyManagers = managerRegistry.getEconomyManagers();

        for (EconomyManager<?> economyManager : economyManagers) {
            economyManager.loadData(event.getPlayer().getUniqueId());
        }
    }

    public void onQuit(PlayerQuitEvent event) {
        List<EconomyManager<?>> economyManagers = managerRegistry.getEconomyManagers();

        for (EconomyManager<?> economyManager : economyManagers) {
            economyManager.saveData(event.getPlayer().getUniqueId());
        }
    }
}
