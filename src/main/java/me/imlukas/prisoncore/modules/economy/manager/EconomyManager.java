package me.imlukas.prisoncore.modules.economy.manager;

import me.imlukas.prisoncore.modules.economy.constants.EconomyType;
import me.imlukas.prisoncore.modules.economy.data.PlayerEconomyData;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public interface EconomyManager {

    /**
     * @return The identifier of the economy manager, used for config and storage.
     */
    String getIdentifier();
    /**
     * @return The identifier of the economy manager
     */
    EconomyType getType();

    /**
     * The sign to show after the balance (e.g. $, points, etc.)
     * @return the sign
     */
    String getSign();
    /**
     * Gets the data of the player for the specified economy manager
     *
     * @param playerId The UUID of the player
     * @return The data of the player for the specified economy manager
     */
    CompletableFuture<PlayerEconomyData> getData(UUID playerId);

    /**
     * Used to get a new instance of the data, according to the economy manager
     *
     * @param playerId The UUID of the player
     * @param balance balance
     */
    PlayerEconomyData getDataInstance(UUID playerId, Integer balance);

    /**
     * Adds the data to the economy manager
     *
     * @param data The data to add
     */
    void addData(PlayerEconomyData data);

    /**
     * Removes the data from the economy manager
     *
     * @param playerId The UUID of the player
     */
    void removeData(UUID playerId);

    /**
     * Saves the data to the user of the player
     *
     * @param playerId The UUID of the player
     */
    void saveData(UUID playerId);

    /**
     * Loads the data from the user of the player
     *
     * @param playerId The UUID of the player
     * @return A CompletedFuture that contains the data of the player or null if the player has no data
     */
    CompletableFuture<PlayerEconomyData> loadData(UUID playerId);

    /**
     * Checks if the player's data is loaded
     *
     * @param playerId The UUID of the player
     * @return true if the player's data is loaded, false otherwise
     */
    boolean isLoaded(UUID playerId);
}
