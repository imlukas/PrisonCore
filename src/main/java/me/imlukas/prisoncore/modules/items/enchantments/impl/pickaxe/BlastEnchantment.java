package me.imlukas.prisoncore.modules.items.enchantments.impl.pickaxe;

import me.imlukas.prisoncore.modules.items.constants.ToolType;
import me.imlukas.prisoncore.modules.items.enchantments.data.ParsedEnchantment;
import me.imlukas.prisoncore.modules.items.enchantments.impl.ChanceEnchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class BlastEnchantment extends ChanceEnchantment {

    public BlastEnchantment(ParsedEnchantment parsedEnchantment) {
        super(parsedEnchantment);
    }

    @Override
    public String getName() {
        return "Blast Enchantment";
    }

    @Override
    public String getIdentifier() {
        return "blast";
    }

    @Override
    public List<String> getDescription() {
        return List.of("Creates an explosion when mining");
    }

    @Override
    public ToolType getToolType() {
        return ToolType.PICKAXE;
    }

    @Override
    public void trigger(Player player, ItemStack itemStack) {
        float explosionForce = 4.0f * getLevel() / 3.0f;
        player.getWorld().createExplosion(player.getLocation(), explosionForce, false, true, player);
    }
}
