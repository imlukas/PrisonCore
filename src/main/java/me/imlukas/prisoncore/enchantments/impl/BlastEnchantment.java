package me.imlukas.prisoncore.enchantments.impl;

import me.imlukas.prisoncore.enchantments.ChanceEnchantment;
import me.imlukas.prisoncore.enchantments.data.ParsedEnchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class BlastEnchantment extends ChanceEnchantment {

    public BlastEnchantment(ParsedEnchantment parsedEnchantment) {
        super(parsedEnchantment);
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public String getIdentifier() {
        return null;
    }

    @Override
    public List<String> getDescription() {
        return null;
    }

    @Override
    public void trigger(Player player, ItemStack itemStack) {

    }
}
