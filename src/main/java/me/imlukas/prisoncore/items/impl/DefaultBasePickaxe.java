package me.imlukas.prisoncore.items.impl;

import me.imlukas.prisoncore.items.EnchantableItem;
import org.bukkit.entity.Player;

import java.util.Map;
import java.util.function.Consumer;

public class DefaultBasePickaxe extends EnchantableItem {

    public DefaultBasePickaxe() {
        super();
    }

    public DefaultBasePickaxe(Map<String, Object> serialized) {
        super(serialized);
    }

    @Override
    public Consumer<Player> onRightClick() {
        return (player) -> player.sendMessage("You right clicked the pickaxe!");
    }

    @Override
    public String getIdentifier() {
        return "default-pickaxe";
    }

    @Override
    public void giveItem() {
        getOwner().getInventory().addItem(cachedItem);
    }


}
