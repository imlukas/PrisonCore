package me.imlukas.prisoncore.utils.collection;

import java.util.HashMap;
import java.util.Map;

public class DefaultRegistry<T, V> {

    protected final Map<T, V> map = new HashMap<>();

    /**
     * Get a value from the registry
     * @param t The key
     * @return The value
     */
    public V get(T t) {
        return map.get(t);
    }

    /**
     * Get the map of the registry
     * @return The map
     */
    public Map<T, V> getMap() {
        return map;
    }

    /**
     * Register a value to the registry
     * @param t The key
     * @param v The value
     * @return The value
     */
    public V register(T t, V v) {
        map.put(t, v);
        return v;
    }

    /**
     * Unregister a value from the registry
     * @param t The key
     */
    public void unregister(T t) {
        map.remove(t);
    }

    /**
     * Clear the registry
     */
    public void clear() {
        map.clear();
    }
}
