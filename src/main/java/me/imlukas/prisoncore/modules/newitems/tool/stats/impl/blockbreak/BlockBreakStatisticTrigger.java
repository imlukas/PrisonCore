package me.imlukas.prisoncore.modules.newitems.tool.stats.impl.blockbreak;

import me.imlukas.prisoncore.modules.api.event.PrisonBlockBreakEvent;
import me.imlukas.prisoncore.modules.newitems.tool.base.BaseTool;
import me.imlukas.prisoncore.modules.newitems.tool.stats.ToolStatisticType;
import me.imlukas.prisoncore.modules.newitems.tool.stats.ToolStatistics;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class BlockBreakStatisticTrigger implements Listener {

    @EventHandler
    public void onBreak(PrisonBlockBreakEvent event) {
        BaseTool tool = event.getUsedTool();

        if (tool == null) {
            return;
        }

        ToolStatistics statistics = tool.getStatistics();

        if (statistics == null) {
            return;
        }

        BlockBreakStatistic blockBreak = statistics.get(ToolStatisticType.MINED_BLOCKS);

        if (blockBreak == null) {
            return;
        }

        blockBreak.increment();
    }
}
