package me.imlukas.prisoncore.modules.database.user;

import me.imlukas.prisoncore.PrisonCore;
import me.imlukas.prisoncore.utils.collection.DefaultRegistry;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class UserManager extends DefaultRegistry<UUID, PrisonUser> {

    private final PrisonCore plugin;

    public UserManager(PrisonCore plugin) {
        this.plugin = plugin;
    }

    public PrisonUser add(UUID uuid) {
        PrisonUser user = new PrisonUser(plugin, uuid);
        map.put(uuid, user);
        return user;
    }
}
