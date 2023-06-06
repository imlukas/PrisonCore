package me.imlukas.prisoncore.items.handler;

import lombok.SneakyThrows;
import me.imlukas.prisoncore.PrisonCore;
import me.imlukas.prisoncore.items.BaseItem;
import me.imlukas.prisoncore.items.registry.PrisonItemRegistry;
import me.imlukas.prisoncore.utils.storage.YMLBase;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public class ItemFileHandler extends YMLBase {

    private final PrisonItemRegistry prisonItemRegistry;

    public ItemFileHandler(PrisonCore plugin) {
        super(plugin, "db/items.yml");
        prisonItemRegistry = plugin.getPrisonItemRegistry();
    }

    @SneakyThrows
    public void loadFor(Player player) {
        UUID playerId = player.getUniqueId();
        CompletableFuture.runAsync(() -> {
            ConfigurationSection playerSection = getConfiguration().getConfigurationSection(playerId.toString());

            if (playerSection == null) {
                getConfiguration().createSection(playerId.toString());
                return;
            }

            for (String itemId : playerSection.getKeys(false)) {
                BaseItem baseItem = (BaseItem) playerSection.get(itemId);

                if (baseItem == null) {
                    System.err.println("Failed to load item " + itemId + " for player " + playerId + " (baseItem is null)");
                    return;
                }

                System.out.println("Loaded item " + itemId + " for player " + playerId);
                prisonItemRegistry.registerItem(playerId, baseItem);
            }
        });
    }

    public CompletableFuture<Void> save(Player player, List<BaseItem> itemsToSave) {
        return CompletableFuture.runAsync(() -> {
            for (BaseItem baseItem : itemsToSave) {
                System.out.println("Saving item " + baseItem.getItemId() + " for player " + player.getUniqueId());
                save(player, baseItem);
            }
        });
    }

    public void save(Player player, BaseItem itemToSave) {
        UUID playerId = player.getUniqueId();

        CompletableFuture.runAsync(() -> {
            ConfigurationSection playerSection = getConfiguration().getConfigurationSection(playerId.toString());

            if (playerSection == null) {
                playerSection = getConfiguration().createSection(playerId.toString());
            }

            playerSection.set(itemToSave.getItemId().toString(), itemToSave);
            save();
            System.out.println("Saved item " + itemToSave.getItemId() + " for player " + playerId);
        });

    }
}