package me.imlukas.prisoncore.modules.items.items.registry;

import me.imlukas.prisoncore.PrisonCore;
import me.imlukas.prisoncore.modules.items.ItemModule;
import me.imlukas.prisoncore.modules.items.enchantments.Enchantment;
import me.imlukas.prisoncore.modules.items.items.impl.PrisonItem;
import me.imlukas.prisoncore.modules.items.items.impl.builder.PrisonItemBuilder;
import me.imlukas.prisoncore.modules.items.items.parser.ParsedItem;
import me.imlukas.prisoncore.utils.PDCUtils.PDCWrapper;
import me.imlukas.prisoncore.utils.item.ItemUtil;
import me.imlukas.prisoncore.utils.text.TextUtils;
import org.bukkit.entity.ItemFrame;
import org.bukkit.inventory.ItemStack;

import java.util.*;

/**
 * Stores all the display items for items.
 */
public class PrisonItemRegistry {

    private final PrisonCore plugin;
    public PrisonItemRegistry(ItemModule itemModule) {
        this.plugin = itemModule.getPlugin();
    }

    private final Map<String, ParsedItem> map = new HashMap<>();

    public List<String> getItemIdentifiers() {
        return new ArrayList<>(getData().keySet());
    }

    public PrisonItem get(String identifier) {
        if (!map.containsKey(identifier)) {
            return null;
        }

        ParsedItem item = map.get(identifier);

        ItemStack displayItem = item.getDisplayItem();
        UUID itemId = UUID.randomUUID();

        displayItem = setupItem(displayItem, item, itemId);

        return new PrisonItemBuilder(displayItem)
                .uuid(itemId)
                .itemType(identifier)
                .toolType(item.getItemType())
                .enchantable(item.isEnchantable())
                .enchantments(item.getEnchantmentList())
                .build();
    }

    public Map<String, ParsedItem> getData() {
        return map;
    }

    public void put(String key, ParsedItem value) {
        map.put(key, value);
    }

    public void remove(String key) {
        map.remove(key);
    }

    public boolean contains(String key) {
        return map.containsKey(key);
    }

    public ItemStack setupItem(ItemStack displayItem, ParsedItem prisonItem, UUID itemId) {
        ItemUtil.clearLore(displayItem);
        for (Enchantment enchantment : prisonItem.getEnchantmentList()) {
            ItemUtil.addLore(displayItem, TextUtils.color("&e" + enchantment.getIdentifier() + " &7| " + "&e" + enchantment.getLevel()));
        }

        for (org.bukkit.enchantments.Enchantment enchantment : displayItem.getEnchantments().keySet()) {
            ItemUtil.addLore(displayItem, TextUtils.color("&e" + enchantment.getKey().getKey() + " &7| " + "&e" + displayItem.getEnchantments().get(enchantment)));
        }

        if (prisonItem.isEnchantable()) {
            ItemUtil.setGlowing(displayItem, true);
        }

        PDCWrapper.modifyItem(plugin, displayItem, wrapper -> {

            Map<String, Integer> enchantmentMap = new HashMap<>();

            for (Enchantment enchantment : prisonItem.getEnchantmentList()) {
                enchantmentMap.put(enchantment.getIdentifier(), enchantment.getLevel());
            }

            wrapper.setMap("enchantments", enchantmentMap);
            wrapper.setUUID("prison-item-id", itemId);
        });

        return displayItem;
    }
}
