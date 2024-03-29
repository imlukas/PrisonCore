package me.imlukas.prisoncore.modules.items.enchantments.impl;

import me.imlukas.prisoncore.modules.items.enchantments.Enchantment;
import me.imlukas.prisoncore.modules.items.enchantments.data.ParsedEnchantment;
import org.bukkit.Material;
import org.bukkit.entity.Player;
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

    protected AbstractEnchantment(ParsedEnchantment parsedEnchantment) {
        this.level = parsedEnchantment.getLevel();
        this.maxLevel = parsedEnchantment.getMaxLevel();
        this.displayItem = parsedEnchantment.getDisplayitem();
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

    /**
     * Called when the enchantment is going to be triggered.
     * See also {@link ChanceEnchantment#tryTrigger(Player, ItemStack)}.
     * @param player The player who triggered the enchantment
     * @param itemStack The item that triggered the enchantment
     */
    public abstract void trigger(Player player, ItemStack itemStack);
}
