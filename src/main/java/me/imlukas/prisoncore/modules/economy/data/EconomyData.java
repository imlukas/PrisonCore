package me.imlukas.prisoncore.modules.economy.data;

import java.io.Serializable;
import java.util.UUID;

public interface EconomyData extends Serializable {

    /**
     * The sign to show after the balance (e.g. $, points, etc.)
     *
     * @return the sign
     */
    public String getSign();

    /**
     * The balance of the player
     *
     * @return the balance
     */
    public double getBalance();

    /**
     * Set the balance of the player
     *
     * @param balance the new balance
     */
    public void setBalance(double balance);

    /**
     * Add to the balance of the player
     *
     * @param balance the amount to add
     */
    public void addBalance(double balance);

    /**
     * Subtract from the balance of the player
     *
     * @param balance the amount to subtract
     */
    public void subtractBalance(double balance);

    /**
     * The UUID of the player
     *
     * @return the UUID
     */
    public UUID getPlayerId();
}
