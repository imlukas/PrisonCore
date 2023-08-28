package me.imlukas.prisoncore.modules.database.impl.file;

import me.imlukas.prisoncore.modules.database.DatabaseModule;
import me.imlukas.prisoncore.modules.database.impl.PrisonDatabase;
import me.imlukas.prisoncore.modules.database.impl.file.manager.FileManager;
import me.imlukas.prisoncore.modules.database.impl.file.player.PlayerFile;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public class FileDatabase extends PrisonDatabase {

    private final FileManager fileManager;

    public FileDatabase(DatabaseModule databaseModule) {
        super(databaseModule);
        this.fileManager = new FileManager(databaseModule);
    }

    public FileManager getFileManager() {
        return fileManager;
    }

    @Override
    public String getIdentifier() {
        return "file";
    }

    @Override
    public boolean connect() {
        for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
            fileManager.add(onlinePlayer.getUniqueId());
        }

        return true;
    }

    @Override
    public <T> CompletableFuture<T> fetch(UUID playerId, String key, Class<T> clazz) {
        return fileManager.getPlayerFile(playerId).fetch(key);
    }

    @Override
    public void store(UUID playerId, String key, Object value) {
        fileManager.getPlayerFile(playerId).store(key, value);
    }
}
