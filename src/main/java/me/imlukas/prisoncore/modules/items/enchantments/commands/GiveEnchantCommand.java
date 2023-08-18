package me.imlukas.prisoncore.modules.items.enchantments.commands;

import me.imlukas.prisoncore.PrisonCore;
import me.imlukas.prisoncore.modules.items.ItemModule;
import me.imlukas.prisoncore.modules.items.constants.ToolType;
import me.imlukas.prisoncore.modules.items.enchantments.handler.EnchantmentHandler;
import me.imlukas.prisoncore.modules.items.enchantments.impl.AbstractEnchantment;
import me.imlukas.prisoncore.modules.items.items.cache.PrisonItemCache;
import me.imlukas.prisoncore.modules.items.items.fetching.PrisonItemFetcher;
import me.imlukas.prisoncore.modules.items.items.impl.EnchantableItem;
import me.imlukas.prisoncore.modules.items.items.impl.PrisonItem;
import me.imlukas.prisoncore.utils.PDCUtils.PDCWrapper;
import me.imlukas.prisoncore.utils.command.SimpleCommand;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public class GiveEnchantCommand implements SimpleCommand {

    private final PrisonCore plugin;
    private final PrisonItemCache prisonItemCache;
    private final PrisonItemFetcher prisonItemFetcher;
    private final EnchantmentHandler enchantmentHandler;

    public GiveEnchantCommand(ItemModule itemModule) {
        this.plugin = itemModule.getPlugin();
        this.prisonItemCache = itemModule.getPrisonItemCache();
        this.prisonItemFetcher = itemModule.getPrisonItemFetcher();
        this.enchantmentHandler = itemModule.getEnchantmentHandler();
    }

    @Override
    public Map<Integer, List<String>> tabCompleteWildcards() {
        return Map.of(1, enchantmentHandler.getEnchantmentIdentifiers());
    }

    @Override
    public String getIdentifier() {
        return "pcore.enchant.*.*";
    }

    @Override
    public void execute(CommandSender sender, String... args) {
        Player player = (Player) sender;
        ItemStack itemInMainHand = player.getInventory().getItemInMainHand();

        if (itemInMainHand.getType().isAir()) {
            return;
        }

        PrisonItem prisonItem = prisonItemCache.getOrFetch(itemInMainHand);

        if (prisonItem == null) {
            prisonItem = prisonItemFetcher.fetchItem(itemInMainHand);
            return;
        }

        if (!(prisonItem instanceof EnchantableItem enchantableItem)) {
            player.sendMessage("This item is not enchantable!");
            return;
        }

        String enchantmentName = args[0];

        int level = 1;
        if (!args[1].isEmpty()) {
            level = Integer.parseInt(args[1]);
        }

        AbstractEnchantment enchantment = enchantmentHandler.getEnchantment(enchantmentName, level);

        if (enchantment == null) {
            player.sendMessage("This enchantment does not exist!");
            return;
        }

        ToolType enchantmentToolType = enchantment.getToolType();

        if (enchantmentToolType != ToolType.ALL && enchantmentToolType != enchantableItem.getToolType()) {
            player.sendMessage("This enchantment cannot be applied to this item!");
            return;
        }

        enchantableItem.addEnchantment(enchantment);
    }
}
