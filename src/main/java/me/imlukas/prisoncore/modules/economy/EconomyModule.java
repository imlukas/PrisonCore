package me.imlukas.prisoncore.modules.economy;

import lombok.Getter;
import me.imlukas.prisoncore.modules.AbstractModule;
import me.imlukas.prisoncore.modules.economy.listener.PointsConnectionListener;
import me.imlukas.prisoncore.modules.economy.manager.EconomyManagerRegistry;
import me.imlukas.prisoncore.modules.economy.manager.impl.TokensManager;

@Getter
public class EconomyModule extends AbstractModule {

    private EconomyManagerRegistry economyManagerRegistry;

    @Override
    public void onEnable() {
        economyManagerRegistry = new EconomyManagerRegistry();
        economyManagerRegistry.registerManager(new TokensManager(getPlugin()));

        registerListener(new PointsConnectionListener(this));
    }

    @Override
    public String getIdentifier() {
        return "economy";
    }
}
