package me.imlukas.prisoncore.modules.items.enchantments;

import me.imlukas.prisoncore.modules.items.constants.ToolType;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public interface Enchantment {

    public int getLevel();

    public int getMaxLevel();

    public String getName();

    public String getIdentifier();

    public List<String> getDescription();

    public ToolType getToolType();

    public ItemStack getDisplayItem();

    public void addLevel(int level);

    public void setLevel(int level);

    public void setMaxLevel(int maxLevel);


}
