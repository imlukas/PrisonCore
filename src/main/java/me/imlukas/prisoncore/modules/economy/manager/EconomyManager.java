package me.imlukas.prisoncore.modules.economy.manager;

import me.imlukas.prisoncore.modules.economy.constants.EconomyType;
import me.imlukas.prisoncore.modules.economy.data.EconomyData;

import java.util.UUID;

public interface EconomyManager<T extends EconomyData> {

    /**
     * @return The common identifier of the economy manager, used for config and storage.
     */
    String getCommonIdentifier();
    /**
     * @return The identifier of the economy manager
     */
    EconomyType getType();

    /**
     * Gets the data of the player for the specified economy manager
     *
     * @param playerId The UUID of the player
     * @return The data of the player for the specified economy manager
     */
    T getData(UUID playerId);

    /**
     * Adds the data to the economy manager
     *
     * @param data The data to add
     */
    void addData(T data);

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
    void loadData(UUID playerId);

    /**
     * Checks if the player's data is loaded
     *
     * @param playerId The UUID of the player
     * @return true if the player's data is loaded, false otherwise
     */
    boolean isLoaded(UUID playerId);
}
