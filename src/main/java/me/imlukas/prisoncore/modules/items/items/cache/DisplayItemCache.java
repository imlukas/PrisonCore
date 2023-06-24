package me.imlukas.prisoncore.modules.items.items.cache;

import me.imlukas.prisoncore.modules.items.items.parser.ParsedItem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Stores all the display items for items.
 */
public class DisplayItemCache {

    private final Map<String, ParsedItem> map = new HashMap<>();

    public List<String> getItemIdentifiers() {
        return new ArrayList<>(getData().keySet());
    }

    public ParsedItem get(String key) {
        return map.get(key);
    }

    public Map<String, ParsedItem> getData() {
        return map;
    }

    public void put(String key, ParsedItem value) {
        map.put(key, value);
    }

    public void remove(String key) {
        map.remove(key);
    }

    public boolean contains(String key) {
        return map.containsKey(key);
    }
}
