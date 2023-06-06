package me.imlukas.prisoncore.enchantments;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public abstract class AbstractEnchantment implements Enchantment {

    protected int level, maxLevel;
    protected ItemStack displayItem;

    /**
     * Default constructor for Enchantment, used for registration
     */
    public AbstractEnchantment() {
        this.level = 0;
        this.maxLevel = 0;
        this.displayItem = new ItemStack(Material.AIR);
    }

    public AbstractEnchantment(int level, int maxLevel, ItemStack displayItem) {
        this.level = level;
        this.maxLevel = maxLevel;
        this.displayItem = displayItem;
    }

    @Override
    public int getLevel() {
        return level;
    }

    @Override
    public int getMaxLevel() {
        return maxLevel;
    }

    @Override
    public ItemStack getDisplayItem() {
        return displayItem;
    }

    @Override
    public void setLevel(int level) {
        this.level = level;
    }

    @Override
    public void setMaxLevel(int maxLevel) {
        this.maxLevel = maxLevel;
    }

    @Override
    public void addLevel(int level) {
        int newLevel = this.level + level;
        this.level = Math.min(newLevel, getMaxLevel());
    }
}
