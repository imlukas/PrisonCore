package me.imlukas.prisoncore.modules.newitems.tool.stats.impl;

import me.imlukas.prisoncore.modules.api.event.PrisonBlockBreakEvent;
import me.imlukas.prisoncore.modules.newitems.ItemModule;
import me.imlukas.prisoncore.modules.newitems.tool.base.BaseTool;
import me.imlukas.prisoncore.modules.newitems.tool.stats.ToolStatisticType;
import me.imlukas.prisoncore.modules.newitems.tool.stats.ToolStatistics;
import me.imlukas.prisoncore.modules.newitems.tool.stats.filter.ToolStatisticFilter;
import org.bukkit.Material;
import org.bukkit.block.Block;

public class BlockBreakStatistic extends AbstractToolStatistic<Material> {

    public BlockBreakStatistic(ItemModule module) {
        super(module);

        registerEventHandler(PrisonBlockBreakEvent.class, event -> {
            BaseTool tool = event.getUsedTool();

            if (tool == null) {
                return;
            }

            ToolStatistics statistics = tool.getStatistics();

            if (!statistics.isTracking(getType())) {
                return;
            }

            statistics.increment(getType());
        });
    }

    @Override
    public String getDisplayName() {
        return "Blocks Mined";
    }

    @Override
    public ToolStatisticType getType() {
        return ToolStatisticType.MINED_BLOCKS;
    }

    @Override
    public ToolStatisticFilter<Material> getEmptyFilter() {
        return new ToolStatisticFilter<>();
    }
}
