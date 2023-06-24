package me.imlukas.prisoncore.modules.items.items.impl;

import me.imlukas.prisoncore.modules.items.constants.ToolType;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;

public interface PrisonItem {

    UUID getUUID();

    String getDisplayItemType();

    /**
     * Returns a clone of the display item
     * @return the default display item
     */
    ItemStack getDisplayItem();

    ToolType getToolType();

    Runnable onRightClick();
}
