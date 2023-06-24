package me.imlukas.prisoncore.modules;

import me.imlukas.prisoncore.PrisonCore;
import me.imlukas.prisoncore.utils.command.SimpleCommand;
import me.imlukas.prisoncore.utils.messages.MessagesFile;
import org.bukkit.event.Listener;

import java.io.File;

public abstract class AbstractModule {
    private PrisonCore plugin;
    private File dataFolder;

    public void init(PrisonCore plugin) {
        this.plugin = plugin;
        this.dataFolder = new File(plugin.getDataFolder(), getIdentifier());
        if (!dataFolder.exists()) {
            dataFolder.mkdirs();
        }

        onEnable();
    }

    public void onEnable() {}

    public void onDisable() {}

    public abstract String getIdentifier();

    public PrisonCore getPlugin() {
        return plugin;
    }

    public File getDataFolder() {
        return dataFolder;
    }

    public void registerListener(Listener listener) {
        plugin.registerListener(listener);
    }

    public void registerCommand(SimpleCommand command) {
        plugin.registerCommand(command);
    }

    public MessagesFile getMessages() {
        return plugin.getMessages();
    }

}
