package me.imlukas.prisoncore.enchantments;

import org.bukkit.inventory.ItemStack;

import java.util.List;

public interface Enchantment {

    public int getLevel();

    public int getMaxLevel();

    public String getName();

    public String getIdentifier();

    public List<String> getDescription();

    public ItemStack getDisplayItem();

    public void addLevel(int level);

    public void setLevel(int level);

    public void setMaxLevel(int maxLevel);


}
