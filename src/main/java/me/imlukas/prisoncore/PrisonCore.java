package me.imlukas.prisoncore;

import lombok.Getter;
import lombok.SneakyThrows;
import me.imlukas.prisoncore.commands.GiveEnchantCommand;
import me.imlukas.prisoncore.commands.GivePickaxeCommand;
import me.imlukas.prisoncore.enchantments.AbstractEnchantment;
import me.imlukas.prisoncore.enchantments.impl.JackhammerEnchantment;
import me.imlukas.prisoncore.enchantments.registry.EnchantmentRegistry;
import me.imlukas.prisoncore.items.AbstractItem;
import me.imlukas.prisoncore.items.BaseItem;
import me.imlukas.prisoncore.items.handler.ItemFileHandler;
import me.imlukas.prisoncore.items.impl.DefaultBasePickaxe;
import me.imlukas.prisoncore.items.registry.ItemParser;
import me.imlukas.prisoncore.items.registry.PrisonItemRegistry;
import me.imlukas.prisoncore.listeners.ConnectionListener;
import me.imlukas.prisoncore.listeners.EnchantmentTrigger;
import me.imlukas.prisoncore.listeners.ItemRightClickInteraction;
import me.imlukas.prisoncore.utils.command.SimpleCommand;
import me.imlukas.prisoncore.utils.command.impl.CommandManager;
import me.imlukas.prisoncore.utils.messages.MessagesFile;
import org.bukkit.Bukkit;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

@Getter
public final class PrisonCore extends JavaPlugin {

    private MessagesFile messages;
    private CommandManager commandManager;
    private EnchantmentRegistry enchantmentRegistry;
    private PrisonItemRegistry prisonItemRegistry;
    private ItemFileHandler itemFileHandler;
    private ItemParser itemParser;

    @Override
    public void onEnable() {
        messages = new MessagesFile(this);
        commandManager = new CommandManager(this);
        saveDefaultConfig();

        enchantmentRegistry = new EnchantmentRegistry(this);
        registerDefaultEnchantments();

        itemParser = new ItemParser(this);
        registerDefaultItems();

        prisonItemRegistry = new PrisonItemRegistry();
        itemFileHandler = new ItemFileHandler(this);

        registerListener(new ItemRightClickInteraction(this));
        registerListener(new EnchantmentTrigger(this));
        registerListener(new ConnectionListener(this));

        registerCommand(new GivePickaxeCommand(this));
        registerCommand(new GiveEnchantCommand(this));
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    private void registerEnchantment(Class<? extends AbstractEnchantment> enchantmentClass) {
        enchantmentRegistry.registerEnchantment(enchantmentClass);
    }

    @SneakyThrows
    private void registerItem(AbstractItem item) {
        registerSerializable(item.getClass());
        itemParser.registerItem(item);
    }

    private static void registerSerializable(Class<? extends BaseItem> itemClass) { // Must be static to guarantee that everything works.
        ConfigurationSerialization.registerClass(itemClass);
    }

    private void registerDefaultItems() {
        registerItem(new DefaultBasePickaxe());
    }

    private void registerDefaultEnchantments() {
        registerEnchantment(JackhammerEnchantment.class);
    }

    private void registerListener(Listener listener) {
        Bukkit.getPluginManager().registerEvents(listener, this);
    }

    private void registerCommand(SimpleCommand simpleCommand) {
        commandManager.register(simpleCommand);
    }
}
