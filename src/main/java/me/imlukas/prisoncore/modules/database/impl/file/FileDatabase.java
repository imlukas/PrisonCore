package me.imlukas.prisoncore.modules.database.impl.file;

import me.imlukas.prisoncore.modules.database.DatabaseModule;
import me.imlukas.prisoncore.modules.database.impl.PrisonDatabase;
import me.imlukas.prisoncore.modules.database.impl.file.manager.PlayerFileManager;
import me.imlukas.prisoncore.modules.database.impl.file.player.PlayerFile;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.*;
import java.util.concurrent.CompletableFuture;

public class FileDatabase extends PrisonDatabase {

    private final PlayerFileManager fileManager;

    public FileDatabase(DatabaseModule databaseModule) {
        super(databaseModule);
        this.fileManager = new PlayerFileManager(databaseModule);
    }

    @Override
    public <T> CompletableFuture<T> fetchOrDefault(UUID playerId, String key, Class<T> clazz, T defaultValue) {
        return fetch(playerId, key, clazz).thenApply(value -> value == null ? defaultValue : value);
    }

    public <T> CompletableFuture<Map<String, T>> fetchMultiple(UUID playerId, Class<T> clazz, T defaultValue, String... keys) {
        return fetchMultiple(playerId, clazz, keys).thenApply(values -> {
            Map<String, T> map = new HashMap<>();

            for (Map.Entry<String, T> valuesEntry : values.entrySet()) {
                if (valuesEntry.getValue() == null) {
                    map.put(valuesEntry.getKey(), defaultValue);
                }

                map.put(valuesEntry.getKey(), valuesEntry.getValue());
            }

            return map;
        });
    }

    public PlayerFileManager getPlayerFileManager() {
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

    public PlayerFile getPlayerFile(UUID playerId) {
        return fileManager.getPlayerFile(playerId);
    }

    @Override
    public <T> CompletableFuture<T> fetch(UUID playerId, String key, Class<T> clazz) {
        return getPlayerFile(playerId).fetch(key);
    }

    @Override
    public <T> CompletableFuture<Map<String, T>> fetchMultiple(UUID playerId, Class<T> clazz, String... keys) {
        return CompletableFuture.supplyAsync(() -> {
            Map<String, T> map = new HashMap<>();
            PlayerFile playerFile = getPlayerFile(playerId);

            for (String key : keys) {
                playerFile.fetch(key, clazz).thenAccept(value -> map.put(key, value));
            }

            return map;
        });
    }


    @Override
    public void store(UUID playerId, String key, Object value) {
        getPlayerFile(playerId).store(key, value);
    }

    @Override
    public void storeMutiple(UUID playerId, Map<String, Object> values) {
        getPlayerFile(playerId).storeMultiple(values);
    }
}
