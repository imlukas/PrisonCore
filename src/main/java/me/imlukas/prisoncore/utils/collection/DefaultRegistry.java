package me.imlukas.prisoncore.utils.collection;

import me.imlukas.prisoncore.PrisonCore;
import me.imlukas.prisoncore.modules.database.user.PrisonUser;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class DefaultRegistry<T, V> {

    protected final Map<T, V> map = new HashMap<>();

    public V get(T t) {
        return map.get(t);
    }

    public V add(T t, V v) {
        map.put(t, v);
        return v;
    }

    public void remove(T t) {
        map.remove(t);
    }

    public void clear() {
        map.clear();
    }
}
