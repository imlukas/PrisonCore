package me.imlukas.prisoncore.modules.items.items.fetching;

import me.imlukas.prisoncore.modules.items.ItemModule;
import me.imlukas.prisoncore.modules.items.items.impl.PrisonItem;
import me.imlukas.prisoncore.modules.items.items.registry.PrisonItemRegistry;
import org.bukkit.inventory.ItemStack;

public class PrisonItemFetcher {

    private final PrisonItemRegistry prisonItemRegistry;

    public PrisonItemFetcher(ItemModule itemModule) {
        this.prisonItemRegistry = itemModule.getPrisonItemRegistry();
    }

    public PrisonItem fetchItem(ItemStack itemStack) {
        return null;
    }
}
