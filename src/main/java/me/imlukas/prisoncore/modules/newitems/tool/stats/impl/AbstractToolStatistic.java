package me.imlukas.prisoncore.modules.newitems.tool.stats.impl;

import lombok.Data;
import me.imlukas.prisoncore.modules.newitems.ItemModule;
import me.imlukas.prisoncore.modules.newitems.event.SimpleEventBus;
import me.imlukas.prisoncore.modules.newitems.tool.stats.ToolStatisticType;
import me.imlukas.prisoncore.modules.newitems.tool.stats.filter.ToolStatisticFilter;
import org.bukkit.event.Event;

import java.util.function.Consumer;

@Data
public abstract class AbstractToolStatistic<FilterType> {

    protected final ItemModule itemModule;
    private final SimpleEventBus eventBus;

    protected AbstractToolStatistic(ItemModule itemModule) {
        this.itemModule = itemModule;
        this.eventBus = itemModule.getEventBus();
    }

    public abstract String getDisplayName();

    public abstract ToolStatisticType getType();

    public abstract ToolStatisticFilter<FilterType> getEmptyFilter();

    public final <T extends Event> void registerEventHandler(Class<T> eventClass, Consumer<T> handler) {
        eventBus.subscribe(eventClass, handler);
    }
}
