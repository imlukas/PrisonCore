package me.imlukas.prisoncore.modules.database.impl.file.player;

import me.imlukas.prisoncore.modules.AbstractModule;
import me.imlukas.prisoncore.utils.storage.YMLBase;

import java.util.concurrent.CompletableFuture;

public class PlayerFile extends YMLBase {
    public PlayerFile(AbstractModule module, String playerId) {
        super(module, playerId + ".yml", false);
    }

    public <T> CompletableFuture<T> fetch(String key) {
        return CompletableFuture.supplyAsync(() -> (T) getConfiguration().get(key));
    }

    public void store(String key, Object value) {
        CompletableFuture.runAsync(() -> {
            getConfiguration().set(key, value);
            save();
        });
    }
}
