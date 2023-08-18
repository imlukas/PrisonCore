package me.imlukas.prisoncore.modules.items;

import lombok.Getter;
import me.imlukas.prisoncore.modules.AbstractModule;
import me.imlukas.prisoncore.modules.items.enchantments.commands.GiveEnchantCommand;
import me.imlukas.prisoncore.modules.items.enchantments.impl.AbstractEnchantment;
import me.imlukas.prisoncore.modules.items.enchantments.impl.pickaxe.BlastEnchantment;
import me.imlukas.prisoncore.modules.items.enchantments.impl.pickaxe.JackhammerEnchantment;
import me.imlukas.prisoncore.modules.items.enchantments.impl.pickaxe.MoneyGiverEnchantment;
import me.imlukas.prisoncore.modules.items.enchantments.listeners.EnchantmentTrigger;
import me.imlukas.prisoncore.modules.items.enchantments.handler.EnchantmentHandler;
import me.imlukas.prisoncore.modules.items.enchantments.registry.EnchantmentRegistry;
import me.imlukas.prisoncore.modules.items.items.cache.PrisonItemCache;
import me.imlukas.prisoncore.modules.items.items.fetching.PrisonItemFetcher;
import me.imlukas.prisoncore.modules.items.items.registry.PrisonItemRegistry;
import me.imlukas.prisoncore.modules.items.items.command.GiveItemCommand;
import me.imlukas.prisoncore.modules.items.items.listener.ItemConnectionListener;
import me.imlukas.prisoncore.modules.items.items.parser.ItemParser;

@Getter
public class ItemModule extends AbstractModule {

    private EnchantmentRegistry enchantmentRegistry;
    private EnchantmentHandler enchantmentHandler;

    private PrisonItemCache prisonItemCache;
    private PrisonItemFetcher prisonItemFetcher;

    private PrisonItemRegistry prisonItemRegistry;

    private ItemParser itemParser;


    @Override
    public void onEnable() {
        prisonItemRegistry = new PrisonItemRegistry(this);
        prisonItemFetcher = new PrisonItemFetcher(this);
        prisonItemCache = new PrisonItemCache(this);

        enchantmentRegistry = new EnchantmentRegistry();
        enchantmentHandler = new EnchantmentHandler(this);
        registerDefaultEnchantments();

        itemParser = new ItemParser(this);

        registerCommand(new GiveItemCommand(this));
        registerCommand(new GiveEnchantCommand(this));

        registerListener(new ItemConnectionListener());
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
        registerEnchantment("money-giver", MoneyGiverEnchantment.class);
    }
}
