package me.imlukas.prisoncore.modules.items.items.data;

import me.imlukas.prisoncore.modules.items.items.impl.PrisonItem;

import java.util.*;

public class PlayerItemData {

    private final Map<UUID, PrisonItem> map = new HashMap<>();
    private final UUID playerId;

    public PlayerItemData(UUID playerId) {
        this.playerId = playerId;
    }

    public void put(PrisonItem item) {
        put(item.getUUID(), item);
    }

    public void put(UUID uuid, PrisonItem item) {
        map.put(uuid, item);
    }

    public PrisonItem get(UUID uuid) {
        return map.get(uuid);
    }

    public void remove(UUID uuid) {
        map.remove(uuid);
    }

    public boolean contains(UUID uuid) {
        return map.containsKey(uuid);
    }

    public Map<UUID, PrisonItem> getData() {
        return map;
    }

    public UUID getPlayerId() {
        return playerId;
    }

    public List<PrisonItem> getItems() {
        return new ArrayList<>(getData().values());
    }
}
