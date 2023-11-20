package me.imlukas.prisoncore.modules.newitems.tool.base;

import lombok.Getter;
import me.imlukas.prisoncore.modules.newitems.ItemModule;
import me.imlukas.prisoncore.modules.newitems.tool.stats.ToolStatistics;
import me.imlukas.prisoncore.utils.PDCUtils.PDCWrapper;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;

@Getter
public abstract class BaseTool {

    private final ItemModule module;
    private final ItemStack originalItem;
    private final ToolStatistics statistics;
    private final UUID uuid = UUID.randomUUID();

    protected BaseTool(ItemModule module, ItemStack itemStack) {
        this.module = module;
        this.originalItem = itemStack;
        this.statistics = new ToolStatistics(module);


        PDCWrapper wrapper = new PDCWrapper(module.getPlugin(), itemStack);
        String baseItem = wrapper.getString("base-item");
        // TODO: Read item's NBT data and apply it to the statistics and other stuff

    }

    public ToolStatistics getStatistics() {
        return statistics;
    }

    public UUID getId() {
        return uuid;
    }
}
