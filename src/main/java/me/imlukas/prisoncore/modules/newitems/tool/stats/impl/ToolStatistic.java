package me.imlukas.prisoncore.modules.newitems.tool.stats.impl;

import lombok.Data;
import me.imlukas.prisoncore.modules.newitems.tool.stats.ToolStatisticType;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Data
public class ToolStatistic<F> {

    private final List<F> filter = new ArrayList<>();
    private final ToolStatisticType type;
    private final String displayName;

    private int trackedValue;

    public ToolStatistic(ToolStatisticType type) {
        this.type = type;
        this.displayName = type.getDisplayName();
    }

    @SafeVarargs
    public final void addToFilter(F... toAdd) {
        Collections.addAll(filter, toAdd);
    }

    public void incrementBy(int value) {
        trackedValue += value;
    }

    public void increment() {
        incrementBy(1);
    }
}
