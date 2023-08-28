package me.imlukas.prisoncore;

import lombok.Getter;
import me.imlukas.prisoncore.modules.AbstractModule;
import me.imlukas.prisoncore.modules.database.DatabaseModule;
import me.imlukas.prisoncore.modules.items.ItemModule;
import me.imlukas.prisoncore.modules.ranking.RankingModule;
import me.imlukas.prisoncore.utils.command.SimpleCommand;
import me.imlukas.prisoncore.utils.command.impl.CommandManager;
import me.imlukas.prisoncore.utils.messages.MessagesFile;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;

@Getter
public final class PrisonCore extends JavaPlugin {

    private final Map<String, AbstractModule> registeredModules = new HashMap<>();
    private MessagesFile messages;
    private CommandManager commandManager;


    @Override
    public void onEnable() {
        messages = new MessagesFile(this);
        commandManager = new CommandManager(this);
        saveDefaultConfig();

        registerModule(new DatabaseModule());
        registerModule(new ItemModule());
        registerModule(new RankingModule());
    }

    @Override
    public void onDisable() {

    }

    public void registerModule(AbstractModule module) {
        registeredModules.put(module.getIdentifier(), module);
        module.init(this);
    }
    
    public void registerListener(Listener listener) {
        Bukkit.getPluginManager().registerEvents(listener, this);
    }

    public void registerCommand(SimpleCommand simpleCommand) {
        commandManager.register(simpleCommand);
    }

    public AbstractModule getModule(String moduleIdentifier) {
        return registeredModules.get(moduleIdentifier);
    }

    public <T extends AbstractModule> T getModule(Class<T> clazz) {
        for (AbstractModule module : registeredModules.values()) {
            if (module.getClass().equals(clazz)) {
                return (T) module;
            }
        }
        return null;
    }
}
