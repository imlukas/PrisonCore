package me.imlukas.prisoncore.modules.items.items.registry;

import me.imlukas.prisoncore.modules.items.items.data.PlayerItemData;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PlayerItemDataRegistry {

    private final Map<UUID, PlayerItemData> map = new HashMap<>();

    public PlayerItemData get(UUID key) {
        return map.get(key);
    }

    public Map<UUID, PlayerItemData> getData() {
        return map;
    }

    public void put(UUID type, PlayerItemData value) {
        map.put(type, value);
    }

    public void remove(UUID type) {
        map.remove(type);
    }

    public boolean contains(UUID type) {
        return map.containsKey(type);
    }
}
