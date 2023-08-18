package me.imlukas.prisoncore.modules.items.items.impl;

import lombok.SneakyThrows;
import me.imlukas.prisoncore.modules.items.constants.ToolType;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;
import java.util.function.Consumer;

public class BaseItem implements PrisonItem {

    private final UUID uuid;
    private final String identifier;
    private final ToolType toolType;

    private ItemStack displayItem;
    private Consumer<PlayerInteractEvent> onRightClick;

    public BaseItem(UUID uuid, String identifier, ItemStack displayItem, ToolType toolType) {
        this.uuid = uuid;
        this.identifier = identifier;
        this.displayItem = displayItem;
        this.toolType = toolType;
    }

    public Consumer<PlayerInteractEvent> getRightClickAction() {
        return onRightClick;
    }

    @Override
    public UUID getUUID() {
        return uuid;
    }

    @Override
    public String getIdentifier() {
        return identifier;
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
    public void setDisplayItem(ItemStack item) {
        this.displayItem = item;
    }

    @Override
    public void onRightClick(Consumer<PlayerInteractEvent> onRightClick) {
        this.onRightClick = onRightClick;
    }

    @SneakyThrows
    @Override
    public PrisonItem clone() {
        return (PrisonItem) super.clone();
    }
}
