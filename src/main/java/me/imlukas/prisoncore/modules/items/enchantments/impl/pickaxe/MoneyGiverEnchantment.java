package me.imlukas.prisoncore.modules.items.enchantments.impl.pickaxe;

import me.imlukas.prisoncore.modules.items.constants.ToolType;
import me.imlukas.prisoncore.modules.items.enchantments.data.ParsedEnchantment;
import me.imlukas.prisoncore.modules.items.enchantments.impl.AbstractEnchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class MoneyGiverEnchantment extends AbstractEnchantment {

    private int baseValue = 10;
    private int baseMoney = baseValue * getLevel();

    public MoneyGiverEnchantment(ParsedEnchantment parsedEnchantment) {
        super(parsedEnchantment);
    }

    @Override
    public String getName() {
        return "Money Giver";
    }

    @Override
    public String getIdentifier() {
        return "money-giver";
    }

    @Override
    public List<String> getDescription() {
        return List.of("Gives you money when you mine a block");
    }

    @Override
    public ToolType getToolType() {
        return ToolType.PICKAXE;
    }

    @Override
    public void addLevel(int level) {
        super.addLevel(level);
        baseValue = (baseValue + 5);
        baseMoney = baseValue * getLevel();
    }

    @Override
    public void trigger(Player player, ItemStack itemStack) {

    }
}
