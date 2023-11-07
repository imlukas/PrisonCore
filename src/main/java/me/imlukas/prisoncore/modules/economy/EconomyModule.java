package me.imlukas.prisoncore.modules.economy;

import lombok.Getter;
import me.imlukas.prisoncore.modules.AbstractModule;
import me.imlukas.prisoncore.modules.economy.commands.BalanceCommand;
import me.imlukas.prisoncore.modules.economy.commands.GiveCurrencyCommand;
import me.imlukas.prisoncore.modules.economy.commands.TakeCurrencyCommand;
import me.imlukas.prisoncore.modules.economy.listener.PointsConnectionListener;
import me.imlukas.prisoncore.modules.economy.manager.registry.EconomyManagerRegistry;
import me.imlukas.prisoncore.modules.economy.manager.impl.TokensManager;

@Getter
public class EconomyModule extends AbstractModule {

    private EconomyManagerRegistry economyManagerRegistry;

    @Override
    public void onEnable() {
        economyManagerRegistry = new EconomyManagerRegistry();
        economyManagerRegistry.register(new TokensManager(getPlugin()));

        registerListener(new PointsConnectionListener(this));

        registerCommand(new BalanceCommand(this));
        registerCommand(new GiveCurrencyCommand(this));
        registerCommand(new TakeCurrencyCommand(this));
    }

    @Override
    public String getIdentifier() {
        return "economy";
    }
}
