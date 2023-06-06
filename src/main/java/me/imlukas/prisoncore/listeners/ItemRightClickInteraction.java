package me.imlukas.prisoncore.listeners;

import me.imlukas.prisoncore.PrisonCore;
import me.imlukas.prisoncore.items.BaseItem;
import me.imlukas.prisoncore.items.registry.PrisonItemRegistry;
import me.imlukas.prisoncore.utils.NBTUtil;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;

public class ItemRightClickInteraction implements Listener {

    private final PrisonCore plugin;

    public ItemRightClickInteraction(PrisonCore plugin) {
        this.plugin = plugin;
        this.prisonItemRegistry = plugin.getPrisonItemRegistry();
    }

    private final PrisonItemRegistry prisonItemRegistry;

    @EventHandler
    public void onItemRightClick(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        Action action = event.getAction();

        if (!(action == Action.RIGHT_CLICK_BLOCK || action == Action.RIGHT_CLICK_AIR)) {
            return;
        }

        ItemStack item = player.getInventory().getItemInMainHand();

        if (item.getType().isAir()) {
            return;
        }

        UUID itemId = NBTUtil.getItemId(item);

        if (itemId == null) {
            return;
        }

        BaseItem baseItem = prisonItemRegistry.getItem(player.getUniqueId(), itemId);

        if (baseItem == null) {
            return;
        }

        baseItem.onRightClick().accept(event.getPlayer());
    }
}
