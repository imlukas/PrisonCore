package me.imlukas.prisoncore.items.data;

import lombok.Data;
import me.imlukas.prisoncore.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

import java.util.List;

@Data
public class ParsedItem {

    private final ItemStack displayItem;
    private final String displayName;
    private final List<Enchantment> enchantmentList;

    public ParsedItem(ItemStack displayItem, List<Enchantment> enchantmentList) {
        this.displayItem = displayItem;
        this.displayName = displayItem.getItemMeta().getDisplayName();
        this.enchantmentList = enchantmentList;
    }
}
