package me.imlukas.prisoncore.items;

import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.UUID;
import java.util.function.Consumer;

public interface BaseItem extends ConfigurationSerializable {
    /**
     * This is the owner of the item, it is used to identify the item in the db/items.yml file
     *
     * @return The owner of the item
     */
    Player getOwner();

    /**
     * This is the identifier for the item, it is used to identify the item in the items.yml file
     *
     * @return The identifier for the item
     */
    String getIdentifier();

    /**
     * This is the UUID of the item, it is used to identify the item in the db/items.yml file
     *
     * @return The UUID of the item
     */
    UUID getItemId();

    /**
     * This is the display name of the item
     *
     * @return The display name of the item
     */
    String getDisplayName();

    /**
     * This is the item's display item
     *
     * @return The display item of the item
     */
    ItemStack getDisplayItem();

    void setDisplayName(String displayName);

    void setDisplayItem(ItemStack cachedItem);

    void setOwnerId(UUID ownerId);

    void updateItem();

    /**
     * Gives one of this item to the (current) owner
     */
    void giveItem();

    /**
     * What to do when player right-clicks the item.
     *
     * @return The action to perform when the player right-clicks the item
     */
    Consumer<Player> onRightClick();

    @NotNull Map<String, Object> serialize();
}
