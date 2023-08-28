package me.imlukas.prisoncore.modules.database.user;

import me.imlukas.prisoncore.PrisonCore;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class UserManager {

    private final PrisonCore plugin;
    private final Map<UUID, PrisonUser> users = new HashMap<>();

    public UserManager(PrisonCore plugin) {
        this.plugin = plugin;
    }

    public PrisonUser get(UUID uuid) {
        return users.get(uuid);
    }

    public PrisonUser add(UUID uuid) {
        PrisonUser user = new PrisonUser(plugin, uuid);
        users.put(uuid, user);
        return user;
    }

    public void remove(UUID uuid) {
        users.remove(uuid);
    }

    public void clear() {
        users.clear();
    }
}
