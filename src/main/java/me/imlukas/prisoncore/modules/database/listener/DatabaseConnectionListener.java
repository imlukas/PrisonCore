package me.imlukas.prisoncore.modules.database.listener;

import me.imlukas.prisoncore.modules.database.DatabaseModule;
import me.imlukas.prisoncore.modules.database.impl.PrisonDatabase;
import me.imlukas.prisoncore.modules.database.impl.file.FileDatabase;
import me.imlukas.prisoncore.modules.database.registry.DatabaseRegistry;
import me.imlukas.prisoncore.modules.database.user.UserManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.UUID;

public class DatabaseConnectionListener implements Listener {

    private final UserManager userRegistry;
    private final DatabaseRegistry databaseRegistry;
    
    public DatabaseConnectionListener(DatabaseModule databaseModule) {
        this.userRegistry = databaseModule.getUserManager();
        this.databaseRegistry = databaseModule.getDatabaseRegistry();
    }
    
    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        PrisonDatabase prisonDatabase = databaseRegistry.getFetchingDatabase();
        UUID playerId = event.getPlayer().getUniqueId();
        
        if (prisonDatabase instanceof FileDatabase fileDatabase) {
            fileDatabase.getPlayerFileManager().add(playerId);
        }
        
        userRegistry.add(playerId);
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        PrisonDatabase prisonDatabase = databaseRegistry.getFetchingDatabase();
        UUID playerId = event.getPlayer().getUniqueId();
        
        if (prisonDatabase instanceof FileDatabase fileDatabase) {
            fileDatabase.getPlayerFileManager().remove(playerId);
        }

        userRegistry.unregister(playerId);
    }
}
