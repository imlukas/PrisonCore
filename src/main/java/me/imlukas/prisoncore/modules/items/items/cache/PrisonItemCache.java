package me.imlukas.prisoncore.modules.items.items.cache;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.RemovalListener;
import me.imlukas.prisoncore.PrisonCore;
import me.imlukas.prisoncore.modules.items.ItemModule;
import me.imlukas.prisoncore.modules.items.items.fetching.PrisonItemHandler;
import me.imlukas.prisoncore.modules.items.items.impl.PrisonItem;
import me.imlukas.prisoncore.utils.PDCUtils.PDCWrapper;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * Caches items for 30 minutes, if the item is not interacted with for 30 minutes, it will be removed from the cache.
 * This avoids potential memory leaks and makes the items easier to access.
 */
public class PrisonItemCache {

    private final PrisonCore plugin;
    private final PrisonItemHandler itemHandler;
    private final Cache<UUID, PrisonItem> cache; // TODO: add a removal listener to store item data.

    public PrisonItemCache(ItemModule itemModule) {
        this.plugin = itemModule.getPlugin();
        this.itemHandler = itemModule.getPrisonItemHandler();
        RemovalListener<UUID, PrisonItem> listener = notification -> {
            if (notification.getValue() != null) {
                itemHandler.updateItem(notification.getValue());
            }
        };
        this.cache = CacheBuilder.newBuilder().expireAfterAccess(30, TimeUnit.MINUTES)
                .removalListener(listener)
                .build();
    }

    public void put(PrisonItem item) {
        cache.put(item.getUUID(), item);
    }

    public PrisonItem getOrFetch(ItemStack itemStack) {
        UUID itemId = new PDCWrapper(plugin, itemStack).getUUID("prisonItemId");

        if (itemId == null) {
            return null;
        }

        if (contains(itemId)) {
            return cache.getIfPresent(itemId);
        }

        return itemHandler.fetchItem(itemStack);
    }


    public void remove(UUID uuid) {
        cache.invalidate(uuid);
    }

    public boolean contains(UUID uuid) {
        return cache.asMap().containsKey(uuid);
    }

    public void clear() {
        cache.invalidateAll();
    }
}
