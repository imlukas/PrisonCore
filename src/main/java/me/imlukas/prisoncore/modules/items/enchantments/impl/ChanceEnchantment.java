package me.imlukas.prisoncore.modules.items.enchantments.impl;

import me.imlukas.prisoncore.modules.items.enchantments.data.ParsedEnchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public abstract class ChanceEnchantment extends AbstractEnchantment {

    protected double basePercentage;
    protected double percentageIncrease;

    protected ChanceEnchantment(ParsedEnchantment parsedEnchantment) {
        super(parsedEnchantment);
    }

    /**
     * Gets the chance percentage of the enchantment
     *
     * @return The chance percentage
     */
    public double getChancePercentage() {
        return basePercentage;
    }

    /**
     * Gets the increase in chance percentage per level
     *
     * @return The increase in chance percentage per level
     */
    public double getIncreasePerLevel() {
        return percentageIncrease;
    }

    /**
     * Tries to trigger the enchantment based on the chance percentage
     *
     * @param player    The player who triggered the enchantment
     * @param itemStack The item that triggered the enchantment
     * @return Whether the enchantment triggered
     */
    public boolean tryTrigger(Player player, ItemStack itemStack) {
        System.out.println("Triggering chance enchantment");
        if (getChancePercentage() == 1.0d) {
            trigger(player, itemStack);
            return true;
        }

        double percentage = getChancePercentage() + (getIncreasePerLevel() * (getLevel() - 1));
        double random = Math.random();

        if (random < percentage) {
            trigger(player, itemStack);
            return true;
        }
        return false;
    }


}
