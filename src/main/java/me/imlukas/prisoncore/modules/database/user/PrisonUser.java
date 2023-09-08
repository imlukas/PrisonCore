package me.imlukas.prisoncore.modules.database.user;

import me.imlukas.prisoncore.PrisonCore;
import me.imlukas.prisoncore.modules.database.DatabaseModule;
import me.imlukas.prisoncore.modules.database.impl.PrisonDatabase;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public class PrisonUser {

    private final UUID playerId;
    private final PrisonDatabase fetchingDatabase;
    private final Map<String, Object> cachedData = new HashMap<>();

    public PrisonUser(PrisonCore plugin, UUID playerId) {
        this.playerId = playerId;
        this.fetchingDatabase = plugin.getModule(DatabaseModule.class).getDatabaseRegistry().getFetchingDatabase();
    }

    /**
     * Fetches a value from the database, assumes the value is a String
     * @param key The key to fetch the value from
     * @return A future with the value or null if the value is not present
     */
    public CompletableFuture<String> fetch(String key) {
        return fetchingDatabase.fetch(playerId, key, String.class);
    }

    // TODO: Make default value work
    public <T> CompletableFuture<T> fetchOrDefault(String key, Class<T> clazz, T defaultValue) {
        return fetchingDatabase.fetch(playerId, key, clazz);
    }

    /**
     * Fetches a value from the selected database
     * @param key The key to fetch the value from
     * @param clazz The class of the value
     * @return A future with the value or null if the value is not present
     * @param <T> The type of the value
     */
    public <T> CompletableFuture<T> fetch(String key, Class<T> clazz) {
        return fetchingDatabase.fetch(playerId, key, clazz);
    }

    /**
     * Fetches multiple values from the database
     * @param clazz The class of the values
     * @param keys The keys to fetch the values from
     * @return A future with a map of the values in which the key is the identifier of the value, based on the original key
     * @param <T> The type of the values
     */
    public <T> CompletableFuture<Map<String, T>> fetchMultiple(Class<T> clazz, String... keys) {
        return fetchingDatabase.fetchMultiple(playerId, clazz, keys);
    }

    /**
     * Stores a value in the database
     * @param key The key to store the value under
     * @param value The value to store
     */
    public void store(String key, Object value) {
        fetchingDatabase.store(playerId, key, value);
    }

    // Cache

    /**
     * Caches a value for the user, as obvious these values are not saved to the database
     * Should be used for temporary data that needs to be associated with a user.
     * @param key The key to cache the value under
     * @param value The value to cache
     */
    public void cache(String key, Object value) {
        cachedData.put(key, value);
    }

    /**
     * Gets a cached value from the user
     * @param key The key to get the value from
     * @return The value stored under the key
     */
    public Object getCached(String key) {
        return cachedData.get(key);
    }

    /**
     * Removes a cached value from the user
     * @param key The key to remove the value from
     */
    public void removeCached(String key) {
        cachedData.remove(key);
    }

    /**
     * Checks if the user has a cached value under the key
     * @param key The key to check
     * @return Whether the user has a cached value under the key
     */
    public boolean hasCached(String key) {
        return cachedData.containsKey(key);
    }

}
