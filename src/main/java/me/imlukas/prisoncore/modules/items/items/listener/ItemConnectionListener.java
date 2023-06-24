package me.imlukas.prisoncore.modules.items.items.listener;

import me.imlukas.prisoncore.modules.items.ItemModule;
import me.imlukas.prisoncore.modules.items.items.handler.PlayerItemHandler;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class ItemConnectionListener implements Listener {

    private final PlayerItemHandler playerItemHandler;

    public ItemConnectionListener(ItemModule itemModule) {
        this.playerItemHandler = itemModule.getPlayerItemHandler();
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        playerItemHandler.load(player.getUniqueId());
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        playerItemHandler.unload(player.getUniqueId());
    }
}
