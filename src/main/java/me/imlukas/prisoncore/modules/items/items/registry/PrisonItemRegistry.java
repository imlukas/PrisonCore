package me.imlukas.prisoncore.modules.items.items.registry;

import me.imlukas.prisoncore.PrisonCore;
import me.imlukas.prisoncore.modules.items.ItemModule;
import me.imlukas.prisoncore.modules.items.enchantments.Enchantment;
import me.imlukas.prisoncore.modules.items.items.fetching.PrisonItemHandler;
import me.imlukas.prisoncore.modules.items.items.impl.PrisonItem;
import me.imlukas.prisoncore.modules.items.items.impl.builder.PrisonItemBuilder;
import me.imlukas.prisoncore.modules.items.items.parser.ParsedItem;
import me.imlukas.prisoncore.utils.PDCUtils.PDCWrapper;
import me.imlukas.prisoncore.utils.item.ItemUtil;
import me.imlukas.prisoncore.utils.text.TextUtils;
import org.bukkit.inventory.ItemStack;

import java.util.*;

/**
 * Stores all the display items for items.
 */
public class PrisonItemRegistry {

    private final PrisonCore plugin;
    private final PrisonItemHandler prisonItemHandler;
    public PrisonItemRegistry(ItemModule itemModule) {
        this.plugin = itemModule.getPlugin();
        this.prisonItemHandler = itemModule.getPrisonItemHandler();
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

        PrisonItem prisonItem = new PrisonItemBuilder(displayItem)
            .uuid(itemId)
            .identifier(identifier)
            .toolType(item.getItemType())
            .enchantable(item.isEnchantable())
            .enchantments(item.getEnchantmentList())
            .build();

        prisonItemHandler.updateItem(prisonItem);
        return prisonItem;
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
}
