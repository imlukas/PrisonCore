package me.imlukas.prisoncore.items.registry;

import me.imlukas.prisoncore.items.BaseItem;
import org.bukkit.entity.Player;

import java.util.*;

public class PrisonItemRegistry {
    private final Map<UUID, List<BaseItem>> items = new HashMap<>();

    public void registerItem(UUID playerId, BaseItem item) {
        List<BaseItem> playerItemList = items.get(playerId);

        if (playerItemList == null) {
            playerItemList = new ArrayList<>();
        }

        playerItemList.add(item);
        items.put(playerId, playerItemList);
    }

    public void unregisterItem(UUID playerId, BaseItem item) {
        List<BaseItem> playerItemList = items.get(playerId);

        if (playerItemList == null) {
            return;
        }

        playerItemList.remove(item);
        items.put(playerId, playerItemList);
    }

    public void unregisterAll(UUID playerId) {
        items.remove(playerId);
    }

    public BaseItem getItem(UUID playerId, UUID itemId) {
        List<BaseItem> playerItemList = items.get(playerId);

        if (playerItemList == null) {
            return null;
        }

        for (BaseItem baseItem : playerItemList) {
            if (baseItem.getItemId().equals(itemId)) {
                return baseItem;
            }
        }

        return null;
    }

    public BaseItem getItem(UUID itemId) {
        for (List<BaseItem> baseItemList : items.values()) {
            for (BaseItem baseItem : baseItemList) {
                if (baseItem.getItemId().equals(itemId)) {
                    return baseItem;
                }
            }
        }

        String newLine = System.getProperty("line.separator");

        for (char c : newLine.toCharArray()) {
            System.out.println(c);
        }
        return null;
    }

    public List<BaseItem> getPlayerItems(Player player) {
        List<BaseItem> playerItemList = items.get(player.getUniqueId());

        if (playerItemList == null) {
            return new ArrayList<>();
        }

        return playerItemList;
    }

    /**
     * Gets the map with all the items from all players
     *
     * @return the map with all the items from all players
     */
    public Map<UUID, List<BaseItem>> getItems() {
        return items;
    }
}
