package me.imlukas.prisoncore.modules.database;

import lombok.Getter;
import me.imlukas.prisoncore.modules.AbstractModule;
import me.imlukas.prisoncore.modules.database.registry.DatabaseRegistry;

@Getter
public class DatabaseModule extends AbstractModule {

    private static final String DEFAULT_DATABASE = "SQLITE";
    private DatabaseRegistry databaseRegistry;

    @Override
    public void onEnable() {
        saveDefaultConfig(true);
        databaseRegistry = new DatabaseRegistry(getConfig().getString("database", DEFAULT_DATABASE));
    }

    @Override
    public String getIdentifier() {
        return "database";
    }
}
