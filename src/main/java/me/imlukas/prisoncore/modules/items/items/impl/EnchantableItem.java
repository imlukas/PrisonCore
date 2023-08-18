package me.imlukas.prisoncore.modules.items.items.impl;

import me.imlukas.prisoncore.modules.items.constants.ToolType;
import me.imlukas.prisoncore.modules.items.enchantments.Enchantment;
import me.imlukas.prisoncore.modules.items.enchantments.impl.AbstractEnchantment;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class EnchantableItem extends BaseItem{

    private final List<AbstractEnchantment> enchantments = new ArrayList<>();

    public EnchantableItem(UUID uuid, String identifier, ToolType toolType, ItemStack displayItem, List<AbstractEnchantment> enchantments) {
        super(uuid, identifier, displayItem, toolType);
        this.enchantments.addAll(enchantments);
    }

    public AbstractEnchantment getEnchantment(String identifier) {
        for (AbstractEnchantment enchantment : enchantments) {
            if (enchantment.getIdentifier().equals(identifier)) {
                return enchantment;
            }
        }
        return null;
    }

    public List<AbstractEnchantment> getEnchantments() {
        return enchantments;
    }

    public void addEnchantment(AbstractEnchantment enchantment) {
        enchantments.add(enchantment);
    }

    public void removeEnchantment(AbstractEnchantment enchantment) {
        enchantments.remove(enchantment);
    }

    public void addLevelToEnchantment(AbstractEnchantment enchantment, int level) {
        enchantment.addLevel(level);
    }

    public void addLevelToEnchantment(String identifier, int level) {
        AbstractEnchantment enchantment = getEnchantment(identifier);
        if (enchantment != null) {
            enchantment.addLevel(level);
        }
    }
}
