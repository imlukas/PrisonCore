package me.imlukas.prisoncore.modules.economy.manager.registry;

import me.imlukas.prisoncore.modules.economy.constants.EconomyType;
import me.imlukas.prisoncore.modules.economy.manager.EconomyManager;
import me.imlukas.prisoncore.modules.economy.manager.impl.BaseManager;
import me.imlukas.prisoncore.utils.collection.DefaultRegistry;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EconomyManagerRegistry extends DefaultRegistry<EconomyType, BaseManager> {

    /**
     * Registers a manager
     * @param manager the manager
     * @return The manager
     */
    public BaseManager register(BaseManager manager) {
        System.out.println("Registering economy manager " + manager.getIdentifier());

        return super.register(manager.getType(), manager);
    }

    /**
     * Get manager method using the {@link EconomyManager#getIdentifier()} method
     * @param commonIdentifier The common identifier of the economy
     * @return The economy manager or null if not found
     */
    public BaseManager getManager(String commonIdentifier) {
        for (BaseManager economyManager : getEconomyManagers()) {
            if (economyManager.getIdentifier().equalsIgnoreCase(commonIdentifier)) {
                return economyManager;
            }
        }
        return null;
    }

    /**
     * Get manager method using the {@link EconomyManager#getType()} method
     * @param economyType The type of economy
     * @return The economy manager or null if not found
     */
    public BaseManager getManager(EconomyType economyType) {
        return super.get(economyType);
    }

    /**
     * Unregisters a manager
     * @param economyType the type of economy
     */
    public void unregisterManager(EconomyType economyType) {
        super.unregister(economyType);
    }

    /**
     * Makes a list with all the managers registered.
     * @return A list with all the managers registered
     */
    public List<BaseManager> getEconomyManagers() {
        return new ArrayList<>(getMap().values());
    }

    /**
     * Makes a list of all the common identifiers of the managers registered.
     * @return A list with all the common identifiers of the managers registered
     */
    public List<String> getAllIdentifiers() {
        List<String> identifiers = new ArrayList<>();
        for (BaseManager economyManager : getEconomyManagers()) {
            identifiers.add(economyManager.getIdentifier());
        }
        return identifiers;
    }

}
