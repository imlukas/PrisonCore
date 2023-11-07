package me.imlukas.prisoncore.modules.items.items.parser;


import me.imlukas.prisoncore.PrisonCore;
import me.imlukas.prisoncore.modules.newitems.ItemModule;
import me.imlukas.prisoncore.modules.items.constants.ToolType;
import me.imlukas.prisoncore.modules.items.enchantments.handler.EnchantmentHandler;
import me.imlukas.prisoncore.modules.items.enchantments.impl.AbstractEnchantment;
import me.imlukas.prisoncore.modules.items.items.registry.PrisonItemRegistry;
import me.imlukas.prisoncore.utils.item.ItemBuilder;
import me.imlukas.prisoncore.utils.storage.YMLBase;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

/**
 * Parses and caches display items from config.
 *
 */
public class ItemParser extends YMLBase {

    private final EnchantmentHandler enchantmentHandler;
    private final PrisonItemRegistry prisonItemRegistry;
    private final FileConfiguration config;

    public ItemParser(ItemModule module) {
        super(module, "items.yml");
        PrisonCore plugin = module.getPlugin();
        this.enchantmentHandler = module.getEnchantmentHandler();
        this.prisonItemRegistry = module.getPrisonItemRegistry();
        this.config = getConfiguration();

        parse();
    }


    public void parse() {
        for (String itemIdentifier : getConfiguration().getKeys(false)) {
            ConfigurationSection itemSection = config.getConfigurationSection(itemIdentifier);
            ItemStack displayItem = ItemBuilder.fromSection(itemSection);

            boolean isEnchantable = itemSection.getBoolean("enchantable");
            ToolType toolType = ToolType.valueOf(itemSection.getString("type").toUpperCase());


            ParsedItem parsedItem = new ParsedItem(itemIdentifier, displayItem, isEnchantable, toolType);

            if (isEnchantable) {

                List<AbstractEnchantment> enchantmentList = new ArrayList<>();

                ConfigurationSection enchantmentSection = itemSection.getConfigurationSection("enchantments");
                for (String enchantment : enchantmentSection.getKeys(false)) {
                    int level = enchantmentSection.getInt(enchantment);
                    enchantmentList.add(enchantmentHandler.getEnchantment(enchantment, level));
                }

                parsedItem.setEnchantments(enchantmentList);
            }

            prisonItemRegistry.put(itemIdentifier, parsedItem);
        }
    }


}
