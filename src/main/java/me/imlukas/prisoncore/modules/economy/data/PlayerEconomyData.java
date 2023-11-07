package me.imlukas.prisoncore.modules.economy.data;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.io.Serializable;
import java.util.UUID;

public class PlayerEconomyData implements Serializable {

    private final UUID playerId;
    private double balance;
    private final String sign;

    public PlayerEconomyData(UUID playerId, double balance, String sign) {
        this.playerId = playerId;
        this.balance = balance;
        this.sign = sign;
    }

    /**
     * The sign to show after the balance (e.g. $, points, etc.)
     *
     * @return the sign
     */
    public String getSign() {
        return sign;
    }

    /**
     * The balance of the player
     *
     * @return the balance
     */
    public double getBalance() {
        return balance;
    }

    /**
     * Set the balance of the player
     *
     * @param balance the new balance
     */
    public void setBalance(double balance) {
        this.balance = balance;
    }

    /**
     * Add to the balance of the player
     *
     * @param balance the amount to add
     */
    public void addBalance(double balance) {
        this.balance += balance;
    }

    /**
     * Subtract from the balance of the player
     *
     * @param balance the amount to subtract
     */
    public void subtractBalance(double balance) {
        this.balance -= balance;
    }

    /**
     * The UUID of the player
     *
     * @return the UUID
     */
    public UUID getPlayerId() {
        return playerId;
    }

    /**
     * The player
     *
     * @return the player or null if the player is offline
     */
    public Player getPlayer() {
        return Bukkit.getPlayer(playerId);
    }
}
