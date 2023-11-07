package me.imlukas.prisoncore.modules.items.items.fetching;

import me.imlukas.prisoncore.PrisonCore;
import me.imlukas.prisoncore.modules.newitems.ItemModule;
import me.imlukas.prisoncore.modules.items.constants.ToolType;
import me.imlukas.prisoncore.modules.items.enchantments.Enchantment;
import me.imlukas.prisoncore.modules.items.enchantments.impl.AbstractEnchantment;
import me.imlukas.prisoncore.modules.items.enchantments.registry.EnchantmentRegistry;
import me.imlukas.prisoncore.modules.items.items.impl.EnchantableItem;
import me.imlukas.prisoncore.modules.items.items.impl.PrisonItem;
import me.imlukas.prisoncore.modules.items.items.impl.builder.PrisonItemBuilder;
import me.imlukas.prisoncore.utils.PDCUtils.PDCWrapper;
import me.imlukas.prisoncore.utils.item.ItemUtil;
import me.imlukas.prisoncore.utils.text.TextUtils;
import org.bukkit.inventory.ItemStack;

import java.util.*;

public class PrisonItemHandler {

    private final EnchantmentRegistry enchantmentRegistry;
    private final PrisonCore plugin;

    public PrisonItemHandler(ItemModule itemModule) {
        this.plugin = itemModule.getPlugin();
        this.enchantmentRegistry = itemModule.getEnchantmentRegistry();
    }

    public PrisonItem fetchItem(ItemStack itemStack) {
        PDCWrapper wrapper = new PDCWrapper(plugin, itemStack);
        Map<String, Integer> enchantmentMap = wrapper.getMap("enchantments");

        String identifier = wrapper.getString("identifier");
        UUID itemId = wrapper.getUUID("prisonItemId");
        ToolType toolType = ToolType.valueOf(wrapper.getString("toolType"));

        PrisonItemBuilder builder = new PrisonItemBuilder(itemStack)
                .identifier(identifier)
                .uuid(itemId)
                .toolType(toolType);

        if (enchantmentMap != null) {
            List<AbstractEnchantment> enchantments = new ArrayList<>();

            for (Map.Entry<String, Integer> stringIntegerEntry : enchantmentMap.entrySet()) {
                String enchantmentIdentifier = stringIntegerEntry.getKey();
                int level = stringIntegerEntry.getValue();

                AbstractEnchantment enchantment = enchantmentRegistry.get(enchantmentIdentifier);
                enchantment.setLevel(level);
                enchantments.add(enchantment);
            }

            builder.enchantments(enchantments);
        }

        return builder.build();
    }

    public void updateItem(PrisonItem prisonItem) {
        UUID itemId = prisonItem.getUUID();
        ItemStack displayItem = prisonItem.getDisplayItem().clone();

        if (prisonItem instanceof EnchantableItem enchantableItem) {
            displayItem = updateEnchantable(enchantableItem);
        }

        PDCWrapper.modifyItem(plugin, displayItem, wrapper -> {
            wrapper.setString("identifier", prisonItem.getIdentifier());
            wrapper.setString("toolType", prisonItem.getToolType().name());
            wrapper.setUUID("prisonItemId", itemId);
        });

        prisonItem.setDisplayItem(displayItem);
    }

    private ItemStack updateEnchantable(EnchantableItem enchantable) {
        ItemStack displayItem = enchantable.getDisplayItem();
        ItemUtil.clearLore(displayItem);
        for (Enchantment enchantment : enchantable.getEnchantments()) {
            ItemUtil.addLore(displayItem, TextUtils.color("&e" + enchantment.getIdentifier() + " &7| " + "&e" + enchantment.getLevel()));
        }

        for (org.bukkit.enchantments.Enchantment enchantment : displayItem.getEnchantments().keySet()) {
            ItemUtil.addLore(displayItem, TextUtils.color("&e" + enchantment.getKey().getKey() + " &7| " + "&e" + displayItem.getEnchantments().get(enchantment)));
        }

        ItemUtil.setGlowing(displayItem, true);

        PDCWrapper.modifyItem(plugin, displayItem, wrapper -> {
            Map<String, Integer> enchantmentMap = new HashMap<>();

            for (Enchantment enchantment : enchantable.getEnchantments()) {
                enchantmentMap.put(enchantment.getIdentifier(), enchantment.getLevel());
            }

            wrapper.setMap("enchantments", enchantmentMap);
        });

        return displayItem;
    }
}
