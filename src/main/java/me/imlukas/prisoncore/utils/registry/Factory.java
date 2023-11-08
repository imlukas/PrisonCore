package me.imlukas.prisoncore.utils.registry;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class Factory<T, V> {

    protected final Map<T, Supplier<V>> map = new HashMap<>();

    public V supply(T t) {
        Supplier<V> supplier = map.get(t);
        return supplier == null ? null : supplier.get();
    }

    public Map<T, Supplier<V>> getMap() {
        return map;
    }

    public void register(T t, Supplier<V> v) {
        map.put(t, v);
    }

    public void register(Supplier<V> v) {
        T t = (T) v.get();
        map.put(t, v);
    }

    public void unregister(T t) {
        map.remove(t);
    }

    public void clear() {
        map.clear();
    }

}