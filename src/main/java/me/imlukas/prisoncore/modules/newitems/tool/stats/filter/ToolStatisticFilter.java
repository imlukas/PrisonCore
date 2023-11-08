package me.imlukas.prisoncore.modules.newitems.tool.stats.filter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ToolStatisticFilter<T> {

    private final List<T> filter = new ArrayList<>();

    @SafeVarargs
    public final void add(T... t) {
        Collections.addAll(filter, t);
    }

    public boolean contains(T t) {
        return filter.contains(t);
    }

    @SafeVarargs
    public final void remove(T... t) {
        for (T t1 : t) {
            filter.remove(t1);
        }
    }

    public void clear() {
        filter.clear();
    }

    public List<T> getFilter() {
        return filter;
    }

    public boolean isEmpty() {
        return filter.isEmpty();
    }
}
