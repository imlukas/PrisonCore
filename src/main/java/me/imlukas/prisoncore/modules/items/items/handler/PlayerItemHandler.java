package me.imlukas.prisoncore.modules.items.items.handler;

import me.imlukas.prisoncore.modules.items.ItemModule;
import me.imlukas.prisoncore.modules.items.enchantments.Enchantment;
import me.imlukas.prisoncore.modules.items.items.cache.DisplayItemCache;
import me.imlukas.prisoncore.modules.items.items.data.PlayerItemData;
import me.imlukas.prisoncore.modules.items.items.impl.EnchantableItem;
import me.imlukas.prisoncore.modules.items.items.impl.PrisonItem;
import me.imlukas.prisoncore.modules.items.items.impl.builder.PrisonItemBuilder;
import me.imlukas.prisoncore.modules.items.items.parser.ParsedItem;
import me.imlukas.prisoncore.modules.items.items.registry.PlayerItemDataRegistry;
import me.imlukas.prisoncore.utils.PDCUtils.PDCWrapper;
import me.imlukas.prisoncore.utils.item.ItemUtil;
import me.imlukas.prisoncore.utils.storage.YMLBase;
import me.imlukas.prisoncore.utils.text.TextUtils;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public class PlayerItemHandler {

    private final ItemModule module;
    private final PlayerItemDataRegistry playerItemDataRegistry;
    private final DisplayItemCache displayItemCache;

    public PlayerItemHandler(ItemModule module) {
        this.module = module;
        this.playerItemDataRegistry = module.getPlayerItemDataRegistry();
        this.displayItemCache = module.getDisplayItemCache();
    }

    public void unload(UUID playerId) {
        CompletableFuture.runAsync(() -> {
            PlayerItemData playerItemData = playerItemDataRegistry.get(playerId);
            String playerFileString = "playerdata" + File.separator + playerId.toString() + ".yml";
            YMLBase playerYML = new YMLBase(module, playerFileString);

            if (!playerYML.getFile().exists()) {
                playerYML.save();
            }

            FileConfiguration config = playerYML.getConfiguration();

            for (PrisonItem item : playerItemData.getItems()) {

                EnchantableItem enchantableItem;
                if (item instanceof EnchantableItem) {
                    enchantableItem = (EnchantableItem) item;
                } else {
                    enchantableItem = null;
                }

                ConfigurationSection itemSection = config.createSection(item.getUUID().toString());
                itemSection.set("type", item.getDisplayItemType());

                if (enchantableItem != null) {
                    itemSection.set("enchantable", true);
                    ConfigurationSection enchantmentSection = itemSection.createSection("enchantments");

                    for (Enchantment enchantment : enchantableItem.getEnchantments()) {
                        enchantmentSection.set(enchantment.getIdentifier(), enchantment.getLevel());
                    }
                } else {
                    itemSection.set("enchantable", false);
                }
            }

            playerYML.save();
        });
    }

    public void load(UUID playerId) {
        CompletableFuture.runAsync(() -> {

            PlayerItemData playerItemData = new PlayerItemData(playerId);
            String playerFileString = "playerdata" + File.separator + playerId.toString() + ".yml";

            File playerDataFolder = new File(module.getDataFolder(), "playerdata");
            File file = new File(playerDataFolder, playerId + ".yml");

            if (!file.exists()) {
                try {
                    playerDataFolder.mkdir();
                    file.createNewFile();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                playerItemDataRegistry.put(playerId, playerItemData);
                return;
            }

            YMLBase playerYML = new YMLBase(module, playerFileString);

            FileConfiguration config = playerYML.getConfiguration();

            for (String itemId : config.getKeys(false)) {
                ConfigurationSection itemSection = config.getConfigurationSection(itemId);
                String itemIdentifier = itemSection.getString("type");
                ParsedItem parsedItem = displayItemCache.get(itemIdentifier);

                if (parsedItem == null) {
                    continue;
                }
                UUID itemUUID = UUID.fromString(itemId);

                // Might want to remove this later or change how I approach this. Currently, this for loops and don't really change anything.
                ItemStack displayItem = parsedItem.getDisplayItem();

                ItemUtil.clearLore(displayItem);
                for (Enchantment enchantment : parsedItem.getEnchantmentList()) {
                    ItemUtil.addLore(displayItem, TextUtils.color("&e" + enchantment.getIdentifier() + " &7| " + "&e" + enchantment.getLevel()));
                }

                for (org.bukkit.enchantments.Enchantment enchantment : displayItem.getEnchantments().keySet()) {
                    ItemUtil.addLore(displayItem, TextUtils.color("&e" + enchantment.getKey().getKey() + " &7| " + "&e" + displayItem.getEnchantments().get(enchantment)));
                }

                if (parsedItem.isEnchantable()) {
                     ItemUtil.setGlowing(displayItem, true);
                }

                PDCWrapper.modifyItem(module.getPlugin(), displayItem, wrapper -> {
                    wrapper.setUUID("prison-item-id", itemUUID);
                });

                PrisonItem item = new PrisonItemBuilder(displayItem)
                        .uuid(itemUUID)
                        .itemType(itemIdentifier)
                        .toolType(parsedItem.getItemType())
                        .enchantable(parsedItem.isEnchantable())
                        .enchantments(parsedItem.getEnchantmentList())
                        .build();

                playerItemData.put(item);
            }

            playerItemDataRegistry.put(playerId, playerItemData);
        }).exceptionally(ex -> {
            ex.printStackTrace();
            return null;
        });
    }

}
