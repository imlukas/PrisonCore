package me.imlukas.prisoncore.modules.database.registry;

import me.imlukas.prisoncore.modules.database.impl.FetchingDatabase;

import java.util.HashMap;
import java.util.Map;

public class DatabaseRegistry {

    public DatabaseRegistry(String database) {
    }

    private FetchingDatabase fetchingDatabase;

    public FetchingDatabase getFetchingDatabase() {
        return fetchingDatabase;
    }
}
