package me.imlukas.prisoncore.modules.database;

import lombok.Getter;
import me.imlukas.prisoncore.modules.AbstractModule;
import me.imlukas.prisoncore.modules.database.impl.file.FileDatabase;
import me.imlukas.prisoncore.modules.database.impl.file.manager.FileManager;
import me.imlukas.prisoncore.modules.database.impl.file.manager.PlayerFileManager;
import me.imlukas.prisoncore.modules.database.impl.mysql.MySQLDatabase;
import me.imlukas.prisoncore.modules.database.listener.DatabaseConnectionListener;
import me.imlukas.prisoncore.modules.database.registry.DatabaseRegistry;
import me.imlukas.prisoncore.modules.database.user.UserManager;

@Getter
public class DatabaseModule extends AbstractModule {

    private static final String DEFAULT_DATABASE = "file";
    private DatabaseRegistry databaseRegistry;
    private PlayerFileManager fileManager;
    private UserManager userManager;


    @Override
    public void onEnable() {
        saveDefaultConfig(true);
        databaseRegistry = new DatabaseRegistry();
        fileManager = new PlayerFileManager(this);
        userManager = new UserManager(this.getPlugin());

        databaseRegistry.registerDatabase("file", () -> new FileDatabase(this));
        databaseRegistry.registerDatabase("sql", () -> new MySQLDatabase(this));
        databaseRegistry.setFetchingDatabase(getConfig().getString("database.fetching", DEFAULT_DATABASE));

        registerListener(new DatabaseConnectionListener(this));

        System.out.println("Fetching database: " + databaseRegistry.getFetchingDatabase().getIdentifier());
        System.out.println("[PrisonCore] DatabaseModule enabled");
    }

    @Override
    public String getIdentifier() {
        return "database";
    }
}
