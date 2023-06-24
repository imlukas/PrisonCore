package me.imlukas.prisoncore.modules.items.enchantments.registry;

import me.imlukas.prisoncore.modules.items.enchantments.Enchantment;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class EnchantmentRegistry {

    private final Map<String, Supplier<Enchantment>> map = new HashMap<>();

    public Enchantment get(String key) {
        return map.get(key).get();
    }

    public Map<String, Supplier<Enchantment>> getData() {
        return map;
    }

    public void put(String key, Supplier<Enchantment> value) {
        map.put(key, value);
    }

    public void remove(String key) {
        map.remove(key);
    }

    public boolean contains(String key) {
        return map.containsKey(key);
    }
}
