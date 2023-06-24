package me.imlukas.prisoncore.modules.items.items.parser;

import me.imlukas.prisoncore.modules.items.constants.ToolType;
import me.imlukas.prisoncore.modules.items.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ParsedItem {

    private final String identifier;
    private final ItemStack displayItem;
    private final ToolType toolType;
    private final boolean isEnchantable;

    private List<Enchantment> enchantmentList = new ArrayList<>();

    public ParsedItem(String identifier, ItemStack displayItem, boolean isEnchantable, ToolType toolType) {
        this.identifier = identifier;
        this.displayItem = displayItem;
        this.isEnchantable = isEnchantable;
        this.toolType = toolType;
    }

    public String getIdentifier() {
        return identifier;
    }

    /**
     * Returns a copy of the original display item
     */
    public ItemStack getDisplayItem() {
        return displayItem.clone();
    }

    public ToolType getItemType() {
        return toolType;
    }

    public List<Enchantment> getEnchantmentList() {
        return enchantmentList;
    }

    public boolean isEnchantable() {
        return isEnchantable;
    }


    public void addEnchantment(Enchantment... enchantments) {
        enchantmentList.addAll(List.of(enchantments));
    }

    public void setEnchantments(List<Enchantment> enchantments) {
        enchantmentList = enchantments;
    }
}
