package me.imlukas.prisoncore.modules.database.registry;

import me.imlukas.prisoncore.modules.database.impl.PrisonDatabase;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class DatabaseRegistry {

    private static final Map<String, Supplier<PrisonDatabase>> DATABASES = new HashMap<>();
    private PrisonDatabase fetchingDatabase;

    public void registerDatabase(String identifier, Supplier<PrisonDatabase> supplier) {
        DATABASES.put(identifier, supplier);
    }

    public PrisonDatabase getFetchingDatabase() {
        return fetchingDatabase;
    }

    public void setFetchingDatabase(String identifier) {
        fetchingDatabase = DATABASES.get(identifier).get();
    }
}
