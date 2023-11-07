package me.imlukas.prisoncore.modules.economy.commands;

import me.imlukas.prisoncore.modules.economy.EconomyModule;
import me.imlukas.prisoncore.modules.economy.constants.EconomyType;
import me.imlukas.prisoncore.modules.economy.manager.registry.EconomyManagerRegistry;
import me.imlukas.prisoncore.utils.command.SimpleCommand;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GiveCurrencyCommand implements SimpleCommand {

    private final EconomyManagerRegistry economyManagerRegistry;

    public GiveCurrencyCommand(EconomyModule module) {
        this.economyManagerRegistry = module.getEconomyManagerRegistry();
    }

    @Override
    public String getIdentifier() {
        return "pcore.economy.give.*";
    }

    @Override
    public void execute(CommandSender sender, String... args) {
        if (!(sender instanceof Player player)) {
            return;
        }

        int amount = Integer.parseInt(args[0]);

        economyManagerRegistry.get(EconomyType.TOKENS).getData(player.getUniqueId()).thenAccept(data -> {
            data.addBalance(amount);
            player.sendMessage("New Balance: " + data.getBalance() + " " + data.getSign());
        });
    }
}
