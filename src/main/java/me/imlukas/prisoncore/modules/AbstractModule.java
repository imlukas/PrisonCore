package me.imlukas.prisoncore.modules;

import lombok.SneakyThrows;
import me.imlukas.prisoncore.PrisonCore;
import me.imlukas.prisoncore.utils.command.SimpleCommand;
import me.imlukas.prisoncore.utils.messages.MessagesFile;
import me.imlukas.prisoncore.utils.storage.YMLBase;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.Listener;

import java.io.File;

public abstract class AbstractModule {
    private PrisonCore plugin;
    private File dataFolder;
    private YMLBase config;

    public void init(PrisonCore plugin) {
        this.plugin = plugin;
        this.dataFolder = new File(plugin.getDataFolder(), getIdentifier());
        if (!dataFolder.exists()) {
            dataFolder.mkdirs();
        }

        onEnable();
    }

    @SneakyThrows
    public void saveDefaultConfig(boolean existsOnSource) {
        File configFile = new File(dataFolder, "config.yml");
        config = new YMLBase(plugin, configFile, existsOnSource);
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

    public FileConfiguration getConfig() {
        return config.getConfiguration();
    }

    public YMLBase getConfigBase() {
        return config;
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
