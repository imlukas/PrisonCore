package me.imlukas.prisoncore.enchantments.impl;

import me.imlukas.prisoncore.enchantments.ChanceEnchantment;
import me.imlukas.prisoncore.enchantments.data.ParsedEnchantment;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.BlockIterator;
import org.bukkit.util.Vector;

import java.util.List;

public class JackhammerEnchantment extends ChanceEnchantment {

    public JackhammerEnchantment(ParsedEnchantment parsedEnchantment) {
        super(parsedEnchantment);
    }

    @Override
    public void trigger(Player player, ItemStack itemStack) {
        World world = player.getWorld();
        Vector playerLocation = player.getLocation().toVector();
        Location eyeLocation = player.getEyeLocation();
        Vector front = new Vector(0, 0, 1);

        front.rotateAroundY(Math.toRadians(Math.round(eyeLocation.getYaw() / 90d) * 90));

        Vector down = new Vector(0, -1, 0);

        BlockIterator frontIterator = new BlockIterator(world, playerLocation, front, 0, 50);

        while (frontIterator.hasNext()) {
            Block baseBlock = frontIterator.next();
            Vector subStart = baseBlock.getLocation().toVector();

            BlockIterator downRelativeIterator = new BlockIterator(world, subStart, down, 0, 50); // 50 blocks down

            while (downRelativeIterator.hasNext()) {
                Block targetBlock = downRelativeIterator.next();
                targetBlock.breakNaturally(itemStack); // workload distribution oportunity
            }
        }
    }

    @Override
    public String getName() {
        return "Jackhammer";
    }

    @Override
    public List<String> getDescription() {
        return List.of("Breaks a Straight Line of Blocks");
    }

    @Override
    public String getIdentifier() {
        return "jackhammer";
    }

}
