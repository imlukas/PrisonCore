package me.imlukas.prisoncore.modules.items.items.command;

import me.imlukas.prisoncore.PrisonCore;
import me.imlukas.prisoncore.modules.items.ItemModule;
import me.imlukas.prisoncore.modules.items.enchantments.Enchantment;
import me.imlukas.prisoncore.modules.items.items.cache.DisplayItemCache;
import me.imlukas.prisoncore.modules.items.items.data.PlayerItemData;
import me.imlukas.prisoncore.modules.items.items.impl.PrisonItem;
import me.imlukas.prisoncore.modules.items.items.impl.builder.PrisonItemBuilder;
import me.imlukas.prisoncore.modules.items.items.parser.ParsedItem;
import me.imlukas.prisoncore.modules.items.items.registry.PlayerItemDataRegistry;
import me.imlukas.prisoncore.utils.PDCUtils.PDCWrapper;
import me.imlukas.prisoncore.utils.command.SimpleCommand;
import me.imlukas.prisoncore.utils.item.ItemUtil;
import me.imlukas.prisoncore.utils.text.TextUtils;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public class GiveItemCommand implements SimpleCommand {

    private final PrisonCore plugin;
    private final DisplayItemCache displayItemCache;
    private final PlayerItemDataRegistry playerItemDataRegistry;
    public GiveItemCommand(ItemModule itemModule) {
        this.plugin = itemModule.getPlugin();
        this.displayItemCache = itemModule.getDisplayItemCache();
        this.playerItemDataRegistry = itemModule.getPlayerItemDataRegistry();
    }

    @Override
    public String getIdentifier() {
        return "pcore.give.*";
    }

    @Override
    public Map<Integer, List<String>> tabCompleteWildcards() {
        return Map.of(1, displayItemCache.getItemIdentifiers());
    }

    @Override
    public void execute(CommandSender sender, String... args) {
        Player player = (Player) sender;
        PlayerItemData playerItemData = playerItemDataRegistry.get(player.getUniqueId());

        if (playerItemData == null) {
            System.out.println("Player item data is null");
            return;
        }

        if (args[0].isEmpty()) {
            return;
        }

        String displayItemType = args[0];
        ParsedItem parsedItem = displayItemCache.get(displayItemType);

        if (parsedItem == null) {
            player.sendMessage("This item does not exist!");
            return;
        }

        UUID itemId = UUID.randomUUID();
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

        PDCWrapper.modifyItem(plugin, displayItem, wrapper -> {
            wrapper.setUUID("prison-item-id", itemId);
        });


        PrisonItem item = new PrisonItemBuilder(displayItem)
                .uuid(itemId)
                .itemType(displayItemType)
                .toolType(parsedItem.getItemType())
                .enchantable(parsedItem.isEnchantable())
                .enchantments(parsedItem.getEnchantmentList())
                .build();

        playerItemData.put(item);
        player.getInventory().addItem(item.getDisplayItem());

        player.sendMessage("You have been given the item " + displayItemType);

    }
}
