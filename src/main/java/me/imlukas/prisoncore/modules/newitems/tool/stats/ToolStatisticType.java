package me.imlukas.prisoncore.modules.newitems.tool.stats;

import me.imlukas.prisoncore.modules.newitems.tool.stats.impl.ToolStatistic;
import org.bukkit.Material;

/**
 * Represents the statistic types that can be associated with a tool
 */
public enum ToolStatisticType {

    MINED_BLOCKS("Blocks Mined"),
    DAMAGE_DONE("Damage Done"),
    BLOCKS_WALKED("Blocks Walked"),
    KILLED_MOBS("Mobs Killed"),
    FISH_CAUGHT("Fish Caught"),
    ;

    private final String displayName;

    ToolStatisticType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
