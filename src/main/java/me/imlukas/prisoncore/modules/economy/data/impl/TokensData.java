package me.imlukas.prisoncore.modules.economy.data.impl;

import me.imlukas.prisoncore.modules.economy.data.EconomyData;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.UUID;

public class TokensData implements EconomyData {

    private final UUID playerId;
    private double points;

    public TokensData(UUID playerId, double points) {
        this.playerId = playerId;
        this.points = points;
    }

    @Override
    public String getSign() {
        return "points";
    }

    @Override
    public double getBalance() {
        return points;
    }

    @Override
    public void setBalance(double balance) {
        points = balance;
    }

    @Override
    public void addBalance(double balance) {
        points += balance;
    }

    @Override
    public void subtractBalance(double balance) {
        points -= balance;
    }

    @Override
    public UUID getPlayerId() {
        return playerId;
    }

    @Override
    public Player getPlayer() {
        return Bukkit.getPlayer(playerId);
    }


}