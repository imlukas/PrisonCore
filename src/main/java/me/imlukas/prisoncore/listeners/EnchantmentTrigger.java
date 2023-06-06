package me.imlukas.prisoncore.listeners;

import me.imlukas.prisoncore.PrisonCore;
import me.imlukas.prisoncore.enchantments.ChanceEnchantment;
import me.imlukas.prisoncore.enchantments.Enchantment;
import me.imlukas.prisoncore.items.BaseItem;
import me.imlukas.prisoncore.items.EnchantableItem;
import me.imlukas.prisoncore.items.registry.PrisonItemRegistry;
import me.imlukas.prisoncore.utils.NBTUtil;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;

public class EnchantmentTrigger implements Listener {

    private final PrisonItemRegistry prisonItemRegistry;

    public EnchantmentTrigger(PrisonCore plugin) {
        this.prisonItemRegistry = plugin.getPrisonItemRegistry();
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        ItemStack usedItem = player.getInventory().getItemInMainHand();

        if (usedItem.getType().isAir()) {
            return;
        }

        UUID itemId = NBTUtil.getItemId(usedItem);

        if (itemId == null) {
            return;
        }

        BaseItem baseItem = prisonItemRegistry.getItem(itemId);

        if (baseItem == null) {
            return;
        }

        if (!(baseItem instanceof EnchantableItem enchantableItem)) {
            return;
        }

        for (Enchantment enchantment : enchantableItem.getEnchantments()) {
            if (!(enchantment instanceof ChanceEnchantment chanceEnchantment)) {
                continue;
            }

            boolean triggered = chanceEnchantment.tryTrigger(player, usedItem);

            if (triggered) {
                player.sendMessage("Enchantment: " + enchantment.getName() + " triggered!");
            }
        }

    }
}
