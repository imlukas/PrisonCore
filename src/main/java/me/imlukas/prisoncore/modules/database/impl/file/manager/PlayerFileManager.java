package me.imlukas.prisoncore.modules.database.impl.file.manager;

import me.imlukas.prisoncore.modules.AbstractModule;
import me.imlukas.prisoncore.modules.database.DatabaseModule;
import me.imlukas.prisoncore.modules.database.impl.file.player.PlayerFile;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PlayerFileManager implements FileManager<UUID> {

    private final AbstractModule module;
    private final Map<UUID, PlayerFile> playerFiles = new HashMap<>();

    public PlayerFileManager(DatabaseModule databaseModule) {
        this.module = databaseModule;
    }

    public PlayerFile getTemporaryPlayerFile(UUID playerId) {
        return new PlayerFile(module, playerId.toString());
    }

    public PlayerFile getPlayerFile(UUID playerId) {
        return playerFiles.computeIfAbsent(playerId, uuid -> new PlayerFile(module, uuid.toString()));
    }

    public void remove(UUID playerId) {
        playerFiles.remove(playerId);
    }

    public void add(UUID playerId) {
        playerFiles.put(playerId, new PlayerFile(module, playerId.toString()));
    }
}
