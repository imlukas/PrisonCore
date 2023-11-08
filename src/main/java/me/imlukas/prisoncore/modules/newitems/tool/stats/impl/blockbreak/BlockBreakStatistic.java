package me.imlukas.prisoncore.modules.newitems.tool.stats.impl.blockbreak;

import me.imlukas.prisoncore.modules.api.event.PrisonBlockBreakEvent;
import me.imlukas.prisoncore.modules.newitems.ItemModule;
import me.imlukas.prisoncore.modules.newitems.tool.base.BaseTool;
import me.imlukas.prisoncore.modules.newitems.tool.stats.ToolStatisticType;
import me.imlukas.prisoncore.modules.newitems.tool.stats.ToolStatistics;
import me.imlukas.prisoncore.modules.newitems.tool.stats.filter.ToolStatisticFilter;
import me.imlukas.prisoncore.modules.newitems.tool.stats.impl.AbstractToolStatistic;
import org.bukkit.Material;
import org.bukkit.block.Block;

public class BlockBreakStatistic extends AbstractToolStatistic<Material> {

    public BlockBreakStatistic(ItemModule module) {
        super(module);
    }

    @Override
    public String getDisplayName() {
        return "Blocks Mined";
    }

    @Override
    public ToolStatisticType getType() {
        return ToolStatisticType.MINED_BLOCKS;
    }
}
