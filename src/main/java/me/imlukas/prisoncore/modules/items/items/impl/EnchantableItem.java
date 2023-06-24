package me.imlukas.prisoncore.modules.items.items.impl;

import me.imlukas.prisoncore.modules.items.constants.ToolType;
import me.imlukas.prisoncore.modules.items.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class EnchantableItem extends BaseItem{

    private final List<Enchantment> enchantments = new ArrayList<>();

    public EnchantableItem(UUID uuid, String identifier, ToolType toolType, ItemStack displayItem, List<Enchantment> enchantments) {
        super(uuid, identifier, displayItem, toolType);
        this.enchantments.addAll(enchantments);
    }

    public Enchantment getEnchantment(String identifier) {
        for (Enchantment enchantment : enchantments) {
            if (enchantment.getIdentifier().equals(identifier)) {
                return enchantment;
            }
        }
        return null;
    }

    public List<Enchantment> getEnchantments() {
        return enchantments;
    }

    public void addEnchantment(Enchantment enchantment) {
        enchantments.add(enchantment);
    }

    public void removeEnchantment(Enchantment enchantment) {
        enchantments.remove(enchantment);
    }

    public void addLevelToEnchantment(Enchantment enchantment, int level) {
        enchantment.addLevel(level);
    }

    public void addLevelToEnchantment(String identifier, int level) {
        Enchantment enchantment = getEnchantment(identifier);
        if (enchantment != null) {
            enchantment.addLevel(level);
        }
    }
}
