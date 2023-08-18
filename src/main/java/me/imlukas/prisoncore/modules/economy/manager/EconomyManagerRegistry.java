package me.imlukas.prisoncore.modules.economy.manager;

import me.imlukas.prisoncore.modules.economy.constants.EconomyType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EconomyManagerRegistry {

    private final Map<EconomyType, EconomyManager<?>> economyManagers = new HashMap<>();

    public void registerManager(EconomyManager<?> economyManager) {
        economyManagers.put(economyManager.getType(), economyManager);
        System.out.println("[PrisonCore] Registered economy manager: " + economyManager.getType().name());
    }

    /**
     * Get manager method using the {@link EconomyManager#getCommonIdentifier()} method
     * @param commonIdentifier The common identifier of the economy
     * @return The economy manager or null if not found
     */
    public EconomyManager<?> getManager(String commonIdentifier) {
        for (EconomyManager<?> economyManager : getEconomyManagers()) {
            if (economyManager.getCommonIdentifier().equalsIgnoreCase(commonIdentifier)) {
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
    public EconomyManager<?> getManager(EconomyType economyType) {
        return economyManagers.get(economyType);
    }

    /**
     * Unregisters a manager
     * @param economyType the type of economy
     */
    public void unregisterManager(EconomyType economyType) {
        economyManagers.remove(economyType);
    }

    /**
     * Makes a list with all the managers registered.
     * @return A list with all the managers registered
     */
    public List<EconomyManager<?>> getEconomyManagers() {
        return new ArrayList<>(economyManagers.values());
    }

    /**
     * Makes a list of all the common identifiers of the managers registered.
     * @return A list with all the common identifiers of the managers registered
     */
    public List<String> getAllIdentifiers() {
        List<String> identifiers = new ArrayList<>();
        for (EconomyManager<?> economyManager : getEconomyManagers()) {
            identifiers.add(economyManager.getCommonIdentifier());
        }
        return identifiers;
    }

}
