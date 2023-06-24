package me.imlukas.prisoncore.modules.items;

import lombok.Getter;
import me.imlukas.prisoncore.modules.AbstractModule;
import me.imlukas.prisoncore.modules.items.enchantments.commands.GiveEnchantCommand;
import me.imlukas.prisoncore.modules.items.enchantments.impl.AbstractEnchantment;
import me.imlukas.prisoncore.modules.items.enchantments.impl.pickaxe.BlastEnchantment;
import me.imlukas.prisoncore.modules.items.enchantments.impl.pickaxe.JackhammerEnchantment;
import me.imlukas.prisoncore.modules.items.enchantments.listeners.EnchantmentTrigger;
import me.imlukas.prisoncore.modules.items.enchantments.handler.EnchantmentHandler;
import me.imlukas.prisoncore.modules.items.enchantments.registry.EnchantmentRegistry;
import me.imlukas.prisoncore.modules.items.items.cache.DisplayItemCache;
import me.imlukas.prisoncore.modules.items.items.command.GiveItemCommand;
import me.imlukas.prisoncore.modules.items.items.handler.PlayerItemHandler;
import me.imlukas.prisoncore.modules.items.items.listener.ItemConnectionListener;
import me.imlukas.prisoncore.modules.items.items.parser.ItemParser;
import me.imlukas.prisoncore.modules.items.items.registry.PlayerItemDataRegistry;

@Getter
public class ItemModule extends AbstractModule {

    private EnchantmentRegistry enchantmentRegistry;
    private EnchantmentHandler enchantmentHandler;

    private PlayerItemDataRegistry playerItemDataRegistry;
    private DisplayItemCache displayItemCache;
    private PlayerItemHandler playerItemHandler;
    private ItemParser itemParser;


    @Override
    public void onEnable() {
        playerItemDataRegistry = new PlayerItemDataRegistry();

        enchantmentRegistry = new EnchantmentRegistry();
        enchantmentHandler = new EnchantmentHandler(this);
        registerDefaultEnchantments();

        displayItemCache = new DisplayItemCache();

        playerItemHandler = new PlayerItemHandler(this);
        itemParser = new ItemParser(this);

        registerCommand(new GiveItemCommand(this));
        registerCommand(new GiveEnchantCommand(this));

        registerListener(new ItemConnectionListener(this));
        registerListener(new EnchantmentTrigger(this));

        System.out.println("[PrisonCore] Item module enabled");
    }

    @Override
    public String getIdentifier() {
        return "items";
    }

    private void registerEnchantment(String identifier, Class<? extends AbstractEnchantment> enchantmentClass) {
        enchantmentHandler.registerEnchantment(identifier, enchantmentClass);
    }

    private void registerDefaultEnchantments() {
        registerEnchantment("jackhammer", JackhammerEnchantment.class);
        registerEnchantment("blast", BlastEnchantment.class);
    }
}
