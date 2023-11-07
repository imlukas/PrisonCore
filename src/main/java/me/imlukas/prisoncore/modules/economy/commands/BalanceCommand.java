package me.imlukas.prisoncore.modules.economy.commands;

import me.imlukas.prisoncore.modules.economy.EconomyModule;
import me.imlukas.prisoncore.modules.economy.constants.EconomyType;
import me.imlukas.prisoncore.modules.economy.manager.registry.EconomyManagerRegistry;
import me.imlukas.prisoncore.utils.command.SimpleCommand;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class BalanceCommand implements SimpleCommand {

    private final EconomyManagerRegistry economyManagerRegistry;

    public BalanceCommand(EconomyModule economyModule) {
        this.economyManagerRegistry = economyModule.getEconomyManagerRegistry();
    }

    @Override
    public String getIdentifier() {
        return "pcore.balance";
    }

    @Override
    public void execute(CommandSender sender, String... args) {
        if (!(sender instanceof Player player)) {
            return;
        }

        economyManagerRegistry.get(EconomyType.TOKENS).getData(player.getUniqueId()).thenAccept(data -> {
            player.sendMessage("Balance: " + data.getBalance() + " " + data.getSign());
        }).exceptionally(throwable -> {
            throwable.printStackTrace();
            return null;
        });

    }
}
