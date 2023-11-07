package me.imlukas.prisoncore.modules.economy.manager.impl;

import me.imlukas.prisoncore.PrisonCore;
import me.imlukas.prisoncore.modules.economy.constants.EconomyType;

public class TokensManager extends BaseManager {

    public TokensManager(PrisonCore plugin) {
        super(plugin);
    }

    @Override
    public String getIdentifier() {
        return "tokens";
    }

    @Override
    public EconomyType getType() {
        return EconomyType.TOKENS;
    }

    @Override
    public String getSign() {
        return "tokens";
    }
}
