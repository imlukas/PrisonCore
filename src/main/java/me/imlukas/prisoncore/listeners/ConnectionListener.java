package me.imlukas.prisoncore.listeners;

import me.imlukas.prisoncore.PrisonCore;
import me.imlukas.prisoncore.items.handler.ItemFileHandler;
import me.imlukas.prisoncore.items.registry.PrisonItemRegistry;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class ConnectionListener implements Listener {

    private final ItemFileHandler itemFileHandler;
    private final PrisonItemRegistry prisonItemRegistry;

    public ConnectionListener(PrisonCore plugin) {
        this.itemFileHandler = plugin.getItemFileHandler();
        this.prisonItemRegistry = plugin.getPrisonItemRegistry();
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        itemFileHandler.loadFor(player);
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        itemFileHandler.save(player, prisonItemRegistry.getPlayerItems(player)).thenRun(() -> {
            prisonItemRegistry.unregisterAll(player.getUniqueId());

            System.out.println("Unregistered all items for " + player.getName());
        });
    }
}
