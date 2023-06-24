package me.imlukas.prisoncore.modules.items.enchantments.data;

import lombok.Data;
import org.bukkit.inventory.ItemStack;

@Data
public class ParsedEnchantment {

    public int level, maxLevel;
    public double basePercentage, percentageIncrease;
    public ItemStack displayitem;

    public ParsedEnchantment(int maxLevel, double basePercentage, double percentageIncrease, ItemStack displayitem) {
        this.level = 1;
        this.maxLevel = maxLevel;
        this.basePercentage = basePercentage;
        this.percentageIncrease = percentageIncrease;
        this.displayitem = displayitem;
    }
}
