package me.imlukas.prisoncore.modules.economy.data;

import org.bukkit.entity.Player;

import java.io.Serializable;
import java.util.UUID;

public interface EconomyData extends Serializable {

    /**
     * The sign to show after the balance (e.g. $, points, etc.)
     *
     * @return the sign
     */
    String getSign();

    /**
     * The balance of the player
     *
     * @return the balance
     */
    double getBalance();

    /**
     * Set the balance of the player
     *
     * @param balance the new balance
     */
    void setBalance(double balance);

    /**
     * Add to the balance of the player
     *
     * @param balance the amount to add
     */
    void addBalance(double balance);

    /**
     * Subtract from the balance of the player
     *
     * @param balance the amount to subtract
     */
    void subtractBalance(double balance);

    /**
     * The UUID of the player
     *
     * @return the UUID
     */
    UUID getPlayerId();

    /**
     * The player
     *
     * @return the player or null if the player is offline
     */
    Player getPlayer();
}
