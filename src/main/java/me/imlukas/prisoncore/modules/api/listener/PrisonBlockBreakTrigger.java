package me.imlukas.prisoncore.modules.api.listener;

import me.imlukas.prisoncore.modules.api.event.PrisonBlockBreakEvent;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class PrisonBlockBreakTrigger implements Listener {

    @EventHandler
    public void onBreak(BlockBreakEvent event) {

        PrisonBlockBreakEvent prisonEvent = new PrisonBlockBreakEvent(null, null, event.getBlock(), event.getPlayer());

        if (prisonEvent.isCancelled()) {
            event.setCancelled(true);
            return;
        }

        Bukkit.getPluginManager().callEvent(prisonEvent);
    }
}
