package me.imlukas.prisoncore.enchantments.registry;

import lombok.SneakyThrows;
import me.imlukas.prisoncore.enchantments.AbstractEnchantment;
import me.imlukas.prisoncore.enchantments.Enchantment;
import me.imlukas.prisoncore.enchantments.data.ParsedEnchantment;
import me.imlukas.prisoncore.utils.item.ItemBuilder;
import me.imlukas.prisoncore.utils.storage.YMLBase;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

public class EnchantmentRegistry extends YMLBase {

    private final Map<String, Supplier<Enchantment>> enchantments = new HashMap<>();
    private final Map<String, ParsedEnchantment> parsedEnchantmentMap = new HashMap<>();

    public EnchantmentRegistry(JavaPlugin plugin) {
        super(plugin, "enchantments.yml");
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

    @SneakyThrows
    public void registerEnchantment(Class<? extends AbstractEnchantment> enchantmentClass) {
        String identifier = enchantmentClass.getConstructor().newInstance().getIdentifier();
        Constructor<? extends AbstractEnchantment> constructor = enchantmentClass.getConstructor(ParsedEnchantment.class);

        ParsedEnchantment parsedEnchantment = parsedEnchantmentMap.get(identifier);

        enchantments.put(identifier, () -> {
            try {
                return constructor.newInstance(parsedEnchantment);
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public Enchantment getEnchantment(String identifier, int level) {
        Enchantment enchantment = enchantments.get(identifier).get();
        enchantment.setLevel(level);
        return enchantment;
    }

    public List<String> getEnchantmentIdentifiers() {
        return enchantments.keySet().stream().toList();
    }
}
