package me.imlukas.prisoncore.modules.newitems;

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
import me.imlukas.prisoncore.modules.items.items.fetching.PrisonItemHandler;
import me.imlukas.prisoncore.modules.items.items.registry.PrisonItemRegistry;
import me.imlukas.prisoncore.modules.items.items.command.GiveItemCommand;
import me.imlukas.prisoncore.modules.items.items.listener.ItemConnectionListener;
import me.imlukas.prisoncore.modules.items.items.parser.ItemParser;
import me.imlukas.prisoncore.modules.newitems.event.SimpleEventBus;
import me.imlukas.prisoncore.modules.newitems.tool.base.cache.BaseToolCache;
import me.imlukas.prisoncore.modules.newitems.tool.stats.registry.ToolStatisticRegistry;

@Getter
public class ItemModule extends AbstractModule {

    // TODO: Re-Write this entire module for the third time.

    private EnchantmentRegistry enchantmentRegistry;
    private EnchantmentHandler enchantmentHandler;

    private BaseToolCache toolCache;
    private PrisonItemHandler prisonItemHandler;

    private PrisonItemRegistry prisonItemRegistry;

    private ItemParser itemParser;
    private SimpleEventBus eventBus;
    private ToolStatisticRegistry statisticRegistry;


    @Override
    public void onEnable() {

        eventBus = new SimpleEventBus(this);
        toolCache = new BaseToolCache();

        statisticRegistry = new ToolStatisticRegistry();

        prisonItemHandler = new PrisonItemHandler(this);
        prisonItemRegistry = new PrisonItemRegistry(this);

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
