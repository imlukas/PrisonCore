package me.imlukas.prisoncore.modules.newitems.tool.base;

import me.imlukas.prisoncore.modules.newitems.ItemModule;
import me.imlukas.prisoncore.modules.newitems.tool.stats.ToolStatistics;

import java.util.UUID;

public abstract class BaseTool {

    protected final ItemModule module;
    protected final ToolStatistics statistics;
    private final UUID uuid = UUID.randomUUID();

    protected BaseTool(ItemModule module) {
        this.module = module;
        this.statistics = new ToolStatistics(module);
    }

    public ToolStatistics getStatistics() {
        return statistics;
    }

    public UUID getId() {
        return uuid;
    }
}
