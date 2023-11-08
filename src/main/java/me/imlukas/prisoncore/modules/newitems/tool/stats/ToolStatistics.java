package me.imlukas.prisoncore.modules.newitems.tool.stats;

import me.imlukas.prisoncore.modules.newitems.ItemModule;
import me.imlukas.prisoncore.modules.newitems.tool.stats.impl.AbstractToolStatistic;
import me.imlukas.prisoncore.modules.newitems.tool.stats.registry.ToolStatisticRegistry;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ToolStatistics {

    private final ToolStatisticRegistry registry;
    private final List<AbstractToolStatistic<?>> statistics = new ArrayList<>();

    public ToolStatistics(ItemModule module) {
        this.registry = module.getStatisticRegistry();
    }


    public <T extends AbstractToolStatistic<?>> T track(ToolStatisticType statisticType, int defaultValue) {
        AbstractToolStatistic<?> statistic = registry.supply(statisticType);
        statistics.add(statistic);
        statistic.setTrackedValue(defaultValue);
        return (T) statistic;
    }

    public <T extends AbstractToolStatistic<?>> T track(ToolStatisticType statisticType) {
        return track(statisticType, 0);
    }

    public <T extends AbstractToolStatistic<?>> T track(AbstractToolStatistic<?> statistic) {
        return track(statistic.getType());
    }

    public void untrack(ToolStatisticType statisticType) {
        statistics.removeIf(toolStatistic -> toolStatistic.getType() == statisticType);
    }

    public boolean isTracking(ToolStatisticType statisticType) {
        for (AbstractToolStatistic<?> toolStatistic : statistics) {
            if (toolStatistic.getType() == statisticType) {
                return true;
            }
        }
        return false;
    }

    public <T extends AbstractToolStatistic<?>> T get(ToolStatisticType statisticType) {
        for (AbstractToolStatistic<?> toolStatistic : statistics) {
            if (toolStatistic.getType() == statisticType) {
                return (T) toolStatistic;
            }
        }
        return null;
    }
}
