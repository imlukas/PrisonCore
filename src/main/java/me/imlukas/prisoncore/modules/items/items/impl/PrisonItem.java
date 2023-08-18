package me.imlukas.prisoncore.modules.items.items.impl;

import me.imlukas.prisoncore.modules.items.constants.ToolType;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;
import java.util.function.Consumer;

public interface PrisonItem {

    UUID getUUID();

    String getIdentifier();

    /**
     * Returns a clone of the display item
     * @return the default display item
     */
    ItemStack getDisplayItem();

    ToolType getToolType();

    void setDisplayItem(ItemStack item);

    void onRightClick(Consumer<PlayerInteractEvent> onRightClick);

    public PrisonItem clone();
}
