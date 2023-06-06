package me.imlukas.prisoncore.items;

import lombok.SneakyThrows;
import me.imlukas.prisoncore.items.data.ParsedItem;
import me.imlukas.prisoncore.utils.NBTUtil;
import me.imlukas.prisoncore.utils.item.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static me.imlukas.prisoncore.utils.SerializedItemStackArray.itemStackArrayFromBase64;

public abstract class AbstractItem implements BaseItem {
    protected ItemStack cachedItem;
    protected String displayName;
    protected UUID itemId, ownerId;

    /**
     * Used for deserialization using Spigot's {@link org.bukkit.configuration.serialization.ConfigurationSerializable}
     *
     * @param serialized The serialized map
     */
    @SneakyThrows
    protected AbstractItem(Map<String, Object> serialized) {
        this.cachedItem = itemStackArrayFromBase64((String) serialized.get("item"))[0];
        this.displayName = (String) serialized.get("displayName");
        this.itemId = UUID.fromString((String) serialized.get("itemId"));
        this.ownerId = UUID.fromString((String) serialized.get("ownerId"));
    }

    protected AbstractItem() {
    }

    public void updateItem() {
        ItemBuilder.fromItem(this.cachedItem).name(displayName).lore(List.of("&7Default pickaxe buddy")).build();
        NBTUtil.setItemId(this.cachedItem, getItemId());
        cachedItem = updateLore();
    }

    public void updateValues(Player player, ParsedItem parsedItem) {
        this.cachedItem = parsedItem.getDisplayItem();
        this.displayName = parsedItem.getDisplayName();
        this.itemId = UUID.randomUUID();
        this.ownerId = player.getUniqueId();
    }

    public void updateLore(Player player) {
        Inventory playerInventory = player.getInventory();

        for (int i = 0; i < playerInventory.getSize(); i++) {
            ItemStack itemStack = playerInventory.getItem(i);

            if (itemStack == null || !itemStack.hasItemMeta() || itemStack.getType().isAir()) {
                continue;
            }

            UUID itemId = NBTUtil.getItemId(itemStack);

            if (itemId == null) {
                continue;
            }

            if (itemId.equals(getItemId())) {
                getOwner().getInventory().setItem(i, updateLore());
            }
        }
    }

    public ItemStack updateLore() {
        List<String> newLore = new ArrayList<>();

        ItemMeta meta = cachedItem.getItemMeta();
        meta.setLore(newLore);
        cachedItem.setItemMeta(meta);
        return cachedItem;
    }

    @Override
    public String getDisplayName() {
        return displayName;
    }

    @Override
    public ItemStack getDisplayItem() {
        return cachedItem;
    }

    @Override
    public UUID getItemId() {
        return itemId;
    }

    @Override
    public Player getOwner() {
        return Bukkit.getPlayer(ownerId);
    }

    @Override
    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    @Override
    public void setDisplayItem(ItemStack cachedItem) {
        this.cachedItem = cachedItem;
    }

    @Override
    public void setOwnerId(UUID ownerId) {
        this.ownerId = ownerId;
    }
}
