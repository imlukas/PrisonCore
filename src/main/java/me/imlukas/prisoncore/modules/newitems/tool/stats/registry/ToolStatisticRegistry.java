package me.imlukas.prisoncore.modules.newitems.tool.stats.registry;

import me.imlukas.prisoncore.modules.newitems.tool.stats.ToolStatisticType;
import me.imlukas.prisoncore.modules.newitems.tool.stats.impl.AbstractToolStatistic;
import me.imlukas.prisoncore.utils.collection.DefaultRegistry;

public class ToolStatisticRegistry extends DefaultRegistry<ToolStatisticType, AbstractToolStatistic> {

    public void register(AbstractToolStatistic statistic) {
        super.register(statistic.getType(), statistic);
    }
}
