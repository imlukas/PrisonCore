package me.imlukas.prisoncore.modules.items.enchantments.listeners;

import me.imlukas.prisoncore.PrisonCore;
import me.imlukas.prisoncore.modules.items.ItemModule;
import me.imlukas.prisoncore.modules.items.enchantments.impl.AbstractEnchantment;
import me.imlukas.prisoncore.modules.items.enchantments.impl.ChanceEnchantment;
import me.imlukas.prisoncore.modules.items.items.cache.PrisonItemCache;
import me.imlukas.prisoncore.modules.items.items.impl.EnchantableItem;
import me.imlukas.prisoncore.modules.items.items.impl.PrisonItem;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

public class EnchantmentTrigger implements Listener {

    private final PrisonCore plugin;
    private final PrisonItemCache prisonItemCache;

    public EnchantmentTrigger(ItemModule itemModule) {
        this.plugin = itemModule.getPlugin();
        this.prisonItemCache = itemModule.getPrisonItemCache();
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();

        ItemStack usedItem = player.getInventory().getItemInMainHand();

        if (usedItem.getType().isAir()) {
            return;
        }

        PrisonItem prisonItem = prisonItemCache.getOrFetch(usedItem);

        if (prisonItem == null) {
            System.out.println("Prison item is null");
            return;
        }

        if (!(prisonItem instanceof EnchantableItem enchantableItem)) {
            System.out.println("Prison item is not enchantable");
            return;
        }

        for (AbstractEnchantment enchantment : enchantableItem.getEnchantments()) {
            if (!(enchantment instanceof ChanceEnchantment chanceEnchantment)) {
                enchantment.trigger(player, usedItem);
                continue;
            }

            boolean triggered = chanceEnchantment.tryTrigger(player, usedItem);

            if (triggered) {
                player.sendMessage("Enchantment: " + enchantment.getName() + " triggered!");
            }
        }

    }
}
