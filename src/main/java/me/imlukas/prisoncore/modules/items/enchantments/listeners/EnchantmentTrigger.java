package me.imlukas.prisoncore.modules.items.enchantments.listeners;

import me.imlukas.prisoncore.PrisonCore;
import me.imlukas.prisoncore.modules.items.ItemModule;
import me.imlukas.prisoncore.modules.items.enchantments.Enchantment;
import me.imlukas.prisoncore.modules.items.enchantments.impl.ChanceEnchantment;
import me.imlukas.prisoncore.modules.items.items.data.PlayerItemData;
import me.imlukas.prisoncore.modules.items.items.impl.EnchantableItem;
import me.imlukas.prisoncore.modules.items.items.impl.PrisonItem;
import me.imlukas.prisoncore.modules.items.items.registry.PlayerItemDataRegistry;
import me.imlukas.prisoncore.utils.PDCUtils.PDCWrapper;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;

public class EnchantmentTrigger implements Listener {

    private final PrisonCore plugin;
    private final PlayerItemDataRegistry playerItemDataRegistry;

    public EnchantmentTrigger(ItemModule itemModule) {
        this.plugin = itemModule.getPlugin();
        this.playerItemDataRegistry = itemModule.getPlayerItemDataRegistry();
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        PlayerItemData playerItemData = playerItemDataRegistry.get(player.getUniqueId());

        if (playerItemData == null) {
            return;
        }

        ItemStack usedItem = player.getInventory().getItemInMainHand();

        if (usedItem.getType().isAir()) {
            return;
        }

        UUID itemId = new PDCWrapper(plugin, usedItem).getUUID("prison-item-id");

        if (itemId == null) {
            System.out.println("Item id is null");
            return;
        }

        PrisonItem prisonItem = playerItemData.get(itemId);

        if (prisonItem == null) {
            System.out.println("Prison item is null");
            return;
        }

        if (!(prisonItem instanceof EnchantableItem enchantableItem)) {
            System.out.println("Prison item is not enchantable");
            return;
        }

        for (Enchantment enchantment : enchantableItem.getEnchantments()) {
            if (!(enchantment instanceof ChanceEnchantment chanceEnchantment)) {
                System.out.println("Enchantment is not a chance enchantment");
                continue;
            }

            boolean triggered = chanceEnchantment.tryTrigger(player, usedItem);

            if (triggered) {
                player.sendMessage("Enchantment: " + enchantment.getName() + " triggered!");
            }
        }

    }
}
