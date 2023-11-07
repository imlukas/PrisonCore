package me.imlukas.prisoncore.modules.newitems.tool.stats.filter;

import java.util.ArrayList;
import java.util.List;

public class ToolStatisticFilter<T> {

    private final List<T> filter = new ArrayList<>();

    public void add(T t) {
        filter.add(t);
    }

    public boolean contains(T t) {
        return filter.contains(t);
    }

    public void remove(T t) {
        filter.remove(t);
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
