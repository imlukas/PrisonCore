package me.imlukas.prisoncore.modules.newitems.tool.base.impl;

import me.imlukas.prisoncore.modules.newitems.ItemModule;
import me.imlukas.prisoncore.modules.newitems.tool.base.BaseTool;
import me.imlukas.prisoncore.modules.newitems.tool.stats.ToolStatisticType;
import me.imlukas.prisoncore.modules.newitems.tool.stats.impl.blockbreak.BlockBreakStatistic;
import org.bukkit.Material;

public class PickaxeBaseTool extends BaseTool {

    public PickaxeBaseTool(ItemModule module) {
        super(module);

        BlockBreakStatistic blockBreak = statistics.track(ToolStatisticType.MINED_BLOCKS);
        blockBreak.getFilter().add(Material.STONE, Material.COBBLESTONE,
                Material.ANDESITE, Material.GRANITE, Material.DIORITE,
                Material.COAL_ORE, Material.IRON_ORE, Material.COPPER_ORE);
    }
}
