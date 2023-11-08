package me.imlukas.prisoncore.modules.newitems.tool.stats.impl;

import lombok.Data;
import me.imlukas.prisoncore.modules.newitems.ItemModule;
import me.imlukas.prisoncore.modules.newitems.event.SimpleEventBus;
import me.imlukas.prisoncore.modules.newitems.tool.stats.ToolStatisticType;
import me.imlukas.prisoncore.modules.newitems.tool.stats.filter.ToolStatisticFilter;
import org.bukkit.Material;
import org.bukkit.event.Event;

import java.util.function.Consumer;

@Data
public abstract class AbstractToolStatistic<F> {

    protected final ItemModule itemModule;
    protected final ToolStatisticFilter<F> filter;

    private int trackedValue;

    protected AbstractToolStatistic(ItemModule itemModule) {
        this.itemModule = itemModule;
        this.filter = new ToolStatisticFilter<>();
    }

    public abstract String getDisplayName();

    public abstract ToolStatisticType getType();

    public ToolStatisticFilter<F> getFilter() {
        return filter;
    }

    public int getTrackedValue() {
        return trackedValue;
    }

    public void incrementBy(int value) {
        trackedValue += value;
    }

    public void increment() {
        incrementBy(1);
    }
}
