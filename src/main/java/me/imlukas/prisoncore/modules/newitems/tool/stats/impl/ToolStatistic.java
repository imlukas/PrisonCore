package me.imlukas.prisoncore.modules.newitems.tool.stats.impl;

import me.imlukas.prisoncore.modules.newitems.tool.stats.ToolStatisticType;
import me.imlukas.prisoncore.modules.newitems.tool.stats.filter.ToolStatisticFilter;

public class ToolStatistic {

    private final AbstractToolStatistic<?> baseToolStatistic;
    private final ToolStatisticFilter<?> filter;

    public ToolStatistic(AbstractToolStatistic<?> baseToolStatistic) {
        this.baseToolStatistic = baseToolStatistic;
        this.filter = baseToolStatistic.getEmptyFilter();
    }

    public AbstractToolStatistic getToolStatistic() {
        return baseToolStatistic;
    }

    public ToolStatisticType getType() {
        return baseToolStatistic.getType();
    }

    public ToolStatisticFilter<?> getFilter() {
        return filter;
    }
}
