package me.imlukas.prisoncore.enchantments;

import me.imlukas.prisoncore.enchantments.data.ParsedEnchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public abstract class ChanceEnchantment extends AbstractEnchantment {

    protected int maxLevel;
    protected double basePercentage, percentageIncrease;

    public ChanceEnchantment(ParsedEnchantment parsedEnchantment) {
        super(parsedEnchantment.getLevel(), parsedEnchantment.getMaxLevel(), parsedEnchantment.getDisplayitem());
        this.maxLevel = parsedEnchantment.getMaxLevel();
        this.basePercentage = parsedEnchantment.getBasePercentage();
        this.percentageIncrease = parsedEnchantment.getPercentageIncrease();
    }

    public double getChancePercentage() {
        return basePercentage;
    }

    public double getIncreasePerLevel() {
        return percentageIncrease;
    }

    public boolean tryTrigger(Player player, ItemStack itemStack) {
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

    public abstract void trigger(Player player, ItemStack itemStack);
}
