package me.imlukas.prisoncore.items.registry;

import lombok.SneakyThrows;
import me.imlukas.prisoncore.PrisonCore;
import me.imlukas.prisoncore.enchantments.Enchantment;
import me.imlukas.prisoncore.enchantments.registry.EnchantmentRegistry;
import me.imlukas.prisoncore.items.AbstractItem;
import me.imlukas.prisoncore.items.BaseItem;
import me.imlukas.prisoncore.items.data.ParsedItem;
import me.imlukas.prisoncore.utils.item.ItemBuilder;
import me.imlukas.prisoncore.utils.storage.YMLBase;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class ItemParser extends YMLBase {
    private final EnchantmentRegistry enchantmentRegistry;
    private final Map<String, Function<ParsedItem, BaseItem>> itemInstances = new HashMap<>();
    private final Map<String, ParsedItem> parsedItems = new HashMap<>();

    public ItemParser(PrisonCore plugin) {
        super(plugin, "items.yml");
        this.enchantmentRegistry = plugin.getEnchantmentRegistry();
        parseItems();
    }

    public void parseItems() {
        FileConfiguration configuration = getConfiguration();

        for (String key : configuration.getKeys(false)) {
            ItemStack displayItem = ItemBuilder.fromSection(configuration.getConfigurationSection(key + ".item"));

            ConfigurationSection enchantmentsSection = configuration.getConfigurationSection(key + ".enchantments");
            if (enchantmentsSection == null) {
                parsedItems.put(key, new ParsedItem(displayItem, new ArrayList<>()));
                continue;
            }

            List<Enchantment> enchantmentList = new ArrayList<>();
            for (String enchantmentName : enchantmentsSection.getKeys(false)) {
                int level = enchantmentsSection.getInt(enchantmentName);
                Enchantment enchantment = enchantmentRegistry.getEnchantment(enchantmentName, level);

                if (enchantment == null) {
                    continue;
                }

                enchantmentList.add(enchantment);
            }

            parsedItems.put(key, new ParsedItem(displayItem, enchantmentList));
        }
    }

    @SneakyThrows
    public void registerItem(AbstractItem itemToRegister) {
        AbstractItem baseItem = (AbstractItem) getConfiguration().get(itemToRegister.getIdentifier());

        if (baseItem != null) {
            return;
        }

        getConfiguration().set(itemToRegister.getIdentifier(), itemToRegister);
        save();
    }


    public AbstractItem getItem(Player player, String identifier) {
        ParsedItem parsedItem = getParsedItem(identifier);
        AbstractItem item = getRegisteredItem(identifier);

        if (item == null) {
            return null;
        }

        item.updateValues(player, parsedItem);
        item.updateItem();
        return item;
    }

    public AbstractItem getRegisteredItem(String identifier) {
        return (AbstractItem) getConfiguration().get(identifier);
    }

    public ParsedItem getParsedItem(String identifier) {
        return parsedItems.get(identifier);
    }

    public List<String> getItemIdentifiers() {
        return new ArrayList<>(parsedItems.keySet());
    }

}
