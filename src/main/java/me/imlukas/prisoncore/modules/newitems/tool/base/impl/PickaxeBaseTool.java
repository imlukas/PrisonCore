package me.imlukas.prisoncore.modules.newitems.tool.base.impl;

import me.imlukas.prisoncore.modules.newitems.ItemModule;
import me.imlukas.prisoncore.modules.newitems.tool.base.BaseTool;
import me.imlukas.prisoncore.modules.newitems.tool.stats.ToolStatisticType;
import me.imlukas.prisoncore.modules.newitems.tool.stats.ToolStatistics;
import me.imlukas.prisoncore.modules.newitems.tool.stats.impl.ToolStatistic;
import org.bukkit.Material;

public class PickaxeBaseTool extends BaseTool {

    public PickaxeBaseTool(ItemModule module) {
        super(module);

        statistics.track(ToolStatisticType.MINED_BLOCKS);

        ToolStatistic minedBlocksStatistic = new ToolStatistic(module.getStatisticRegistry().get(ToolStatisticType.MINED_BLOCKS));

        minedBlocksStatistic.getFilter().add(Material.STONE);

        statistics.track();
    }
}
