package me.imlukas.prisoncore.modules.items.items.impl.builder;

import me.imlukas.prisoncore.modules.items.constants.ToolType;
import me.imlukas.prisoncore.modules.items.enchantments.Enchantment;
import me.imlukas.prisoncore.modules.items.items.impl.BaseItem;
import me.imlukas.prisoncore.modules.items.items.impl.EnchantableItem;
import me.imlukas.prisoncore.modules.items.items.impl.PrisonItem;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class PrisonItemBuilder {


    private final List<Enchantment> enchantmentList = new ArrayList<>();
    private final ItemStack displayItem;

    private UUID itemId = UUID.randomUUID();
    private String identifier;
    private ToolType toolType;
    private boolean isEnchantable = false;

    public PrisonItemBuilder(ItemStack item) {
        this.displayItem = item;
    }

    public PrisonItemBuilder uuid(UUID itemId) {
        this.itemId = itemId;
        return this;
    }

    public PrisonItemBuilder itemType(String displayItemType) {
        this.identifier = displayItemType;
        return this;
    }

    public PrisonItemBuilder toolType(ToolType toolType) {
        this.toolType = toolType;
        return this;
    }

    public PrisonItemBuilder enchantable(boolean isEnchantable) {
        this.isEnchantable = isEnchantable;
        return this;
    }

    public PrisonItemBuilder enchantments(List<Enchantment> enchantments) {
        this.enchantmentList.addAll(enchantments);
        return this;
    }

    public PrisonItemBuilder enchantments(Enchantment... enchantments) {
        this.enchantmentList.addAll(List.of(enchantments));
        return this;
    }

    public PrisonItem build() {
        if (isEnchantable) {
            return new EnchantableItem(itemId, identifier, toolType, displayItem, enchantmentList);
        } else {
            return new BaseItem(itemId, identifier, displayItem, toolType);
        }
    }
}
