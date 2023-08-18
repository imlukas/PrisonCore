package me.imlukas.prisoncore.modules.items.enchantments.handler;

import lombok.SneakyThrows;
import me.imlukas.prisoncore.modules.items.ItemModule;
import me.imlukas.prisoncore.modules.items.enchantments.Enchantment;
import me.imlukas.prisoncore.modules.items.enchantments.data.ParsedEnchantment;
import me.imlukas.prisoncore.modules.items.enchantments.impl.AbstractEnchantment;
import me.imlukas.prisoncore.modules.items.enchantments.registry.EnchantmentRegistry;
import me.imlukas.prisoncore.utils.item.ItemBuilder;
import me.imlukas.prisoncore.utils.storage.YMLBase;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

public class EnchantmentHandler extends YMLBase {

    private final EnchantmentRegistry enchantmentRegistry;
    private final Map<String, ParsedEnchantment> parsedEnchantmentMap = new HashMap<>();

    public EnchantmentHandler(ItemModule itemModule) {
        super(itemModule, "enchantments.yml");
        enchantmentRegistry = itemModule.getEnchantmentRegistry();
        parse();
    }

    public void parse() {
        FileConfiguration configuration = getConfiguration();
        for (String key : configuration.getKeys(false)) {
            int maxLevel = configuration.getInt(key + ".max-level");
            double basePercentage = configuration.getDouble(key + ".base-percentage");
            double percentageIncrease = configuration.getDouble(key + ".percentage-increase");
            ItemStack displayItem = ItemBuilder.fromSection(configuration.getConfigurationSection(key + ".display-item"));

            ParsedEnchantment parsedEnchantment = new ParsedEnchantment(maxLevel, basePercentage, percentageIncrease, displayItem);
            parsedEnchantmentMap.put(key, parsedEnchantment);
        }
    }

    /**
     * Registers an enchantment with the given identifier and class.
     * @param identifier The identifier of the enchantment
     * @param enchantmentClass The class of the enchantment
     */
    @SneakyThrows
    public void registerEnchantment(String identifier, Class<? extends AbstractEnchantment> enchantmentClass) {
        Constructor<? extends AbstractEnchantment> constructor = enchantmentClass.getConstructor(ParsedEnchantment.class);

        ParsedEnchantment parsedEnchantment = parsedEnchantmentMap.get(identifier);

        if (parsedEnchantment == null) {
            throw new IllegalArgumentException("Enchantment with identifier " + identifier + " is not implemented or not registered in the enchantments.yml");
        }

        enchantmentRegistry.put(identifier, () -> {
            try {
                return constructor.newInstance(parsedEnchantment);
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
                throw new RuntimeException(e);
            }
        });
    }

    /**
     * Tries to get the enchantment identifier based on the default (empty) constructor of the enchanment.
     * Be aware that this will only work if you have an empty constructor in your enchantment class.
     * Refer to {@link #registerEnchantment(String, Class)} if you don't have an empty constructor.
     * @param enchantmentClass The enchantment class
     */
    @SneakyThrows
    public void registerEnchantment(Class<? extends AbstractEnchantment> enchantmentClass) {
        String identifier = enchantmentClass.getConstructor().newInstance().getIdentifier();
        registerEnchantment(identifier, enchantmentClass);
    }

    public AbstractEnchantment getEnchantment(String identifier, int level) {
        AbstractEnchantment enchantment = enchantmentRegistry.get(identifier);
        enchantment.setLevel(level);
        return enchantment;
    }

    public List<String> getEnchantmentIdentifiers() {
        return new ArrayList<>(enchantmentRegistry.getData().keySet());
    }
}
