package me.imlukas.prisoncore.modules.items.items.impl;

import me.imlukas.prisoncore.modules.items.constants.ToolType;
import me.imlukas.prisoncore.utils.PDCUtils.PDCWrapper;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;

public class BaseItem implements PrisonItem {

    private final UUID uuid;
    private final String displayItemType;
    private final ItemStack displayItem;
    private final ToolType toolType;

    public BaseItem(UUID uuid, String displayItemType, ItemStack displayItem, ToolType toolType) {
        this.uuid = uuid;
        this.displayItemType = displayItemType;
        this.displayItem = displayItem;
        this.toolType = toolType;
    }

    @Override
    public UUID getUUID() {
        return uuid;
    }

    @Override
    public String getDisplayItemType() {
        return displayItemType;
    }

    @Override
    public ItemStack getDisplayItem() {
        return displayItem.clone();
    }

    @Override
    public ToolType getToolType() {
        return toolType;
    }

    @Override
    public Runnable onRightClick() {
        return null;
    }
}
