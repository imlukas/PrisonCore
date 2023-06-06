package me.imlukas.prisoncore.items;

import me.imlukas.prisoncore.enchantments.Enchantment;
import me.imlukas.prisoncore.utils.text.TextUtils;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

import java.util.*;

import static me.imlukas.prisoncore.utils.SerializedItemStackArray.itemStackArrayToBase64;

public abstract class EnchantableItem extends AbstractItem {

    protected final Map<String, Enchantment> enchantments = new HashMap<>();

    protected EnchantableItem(Map<String, Object> serialized) {
        super(serialized);
    }

    protected EnchantableItem() {
        super();
    }

    public void addEnchantment(Enchantment enchantment) {
        enchantments.put(enchantment.getName(), enchantment);
    }

    public void removeEnchantment(Enchantment enchantment) {
        enchantments.remove(enchantment.getName());
    }

    public void hasEnchantment(Enchantment enchantment) {
        enchantments.containsKey(enchantment.getName());
    }

    public Enchantment getEnchantment(String name) {
        return enchantments.get(name);
    }

    public Collection<Enchantment> getEnchantments() {
        return enchantments.values();
    }

    @Override
    public ItemStack updateLore() {
        List<String> newLore = new ArrayList<>();

        for (Enchantment enchantment : enchantments.values()) {
            newLore.add(TextUtils.color("&7" + enchantment.getName() + " &e-&7 " + enchantment.getLevel()));
        }

        ItemMeta meta = cachedItem.getItemMeta();
        meta.setLore(newLore);
        cachedItem.setItemMeta(meta);
        return cachedItem;
    }

    @NotNull
    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> map = new HashMap<>();

        map.put("type", getIdentifier());
        map.put("displayName", getDisplayName());
        map.put("itemId", getItemId().toString());
        map.put("ownerId", getOwner().getUniqueId().toString());
        map.put("item", itemStackArrayToBase64(getDisplayItem()));

        Map<String, Integer> enchantments = new HashMap<>();

        for (Enchantment enchantment : getEnchantments()) {
            enchantments.put(enchantment.getIdentifier(), enchantment.getLevel());
        }

        map.put("enchantments", enchantments);

        return map;
    }
}
