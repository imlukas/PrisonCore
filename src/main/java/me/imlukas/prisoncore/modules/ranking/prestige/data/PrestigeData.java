package me.imlukas.prisoncore.modules.ranking.prestige.data;

import lombok.Data;

@Data
public class PrestigeData {

    private final int id;
    private final String name;
    private final int price;
    private final String prefix;
    private final String suffix;

    public PrestigeData(int id, String name, int price, String prefix, String suffix) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.prefix = prefix;
        this.suffix = suffix;
    }
}
