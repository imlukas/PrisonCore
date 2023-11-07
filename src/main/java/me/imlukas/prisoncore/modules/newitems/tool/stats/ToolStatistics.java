package me.imlukas.prisoncore.modules.newitems.tool.stats;

import me.imlukas.prisoncore.modules.newitems.ItemModule;
import me.imlukas.prisoncore.modules.newitems.tool.stats.impl.AbstractToolStatistic;
import me.imlukas.prisoncore.modules.newitems.tool.stats.impl.ToolStatistic;
import me.imlukas.prisoncore.modules.newitems.tool.stats.registry.ToolStatisticRegistry;

import java.util.HashMap;
import java.util.Map;

public class ToolStatistics {

    private final ToolStatisticRegistry registry;
    private final Map<ToolStatistic, Integer> statistics = new HashMap<>();

    public ToolStatistics(ItemModule module) {
        this.registry = module.getStatisticRegistry();
    }

    public void track(ToolStatisticType statisticType) {
        AbstractToolStatistic statistic = registry.get(statisticType);

        statistics.putIfAbsent(new ToolStatistic(statistic), 0);
    }

    public void track(ToolStatistic statistic) {
        statistics.putIfAbsent(statistic, 0);
    }

    public void track(ToolStatisticType statisticType, int defaultValue) {
        AbstractToolStatistic statistic = registry.get(statisticType);

        statistics.putIfAbsent(new ToolStatistic(statistic), defaultValue);
    }

    public void untrack(ToolStatisticType statisticType) {
        for (ToolStatistic toolStatistic : statistics.keySet()) {
            if (toolStatistic.getType() == statisticType) {
                statistics.remove(toolStatistic);
            }
        }
    }

    public void incrementBy(ToolStatisticType type, int value) {
        ToolStatistic statistic = null;

        for (ToolStatistic toolStatistic : statistics.keySet()) {
            if (toolStatistic.getType() == type) {
                statistic = toolStatistic;
            }
        }

        if (statistic == null) {
            throw new IllegalArgumentException("Statistic not tracked");
        }


        int newValue = statistics.getOrDefault(statistic, 0) + value;
        statistics.put(statistic, newValue);
    }

    public void increment(ToolStatisticType type) {
        incrementBy(type, 1);
    }

    public boolean isTracking(ToolStatisticType statisticType) {
        for (ToolStatistic toolStatistic : statistics.keySet()) {
            if (toolStatistic.getType() == statisticType) {
                return true;
            }
        }
        return false;
    }
}
