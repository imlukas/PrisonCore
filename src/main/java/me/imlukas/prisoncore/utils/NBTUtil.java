package me.imlukas.prisoncore.utils;

import de.tr7zw.nbtapi.NBTItem;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;

public class NBTUtil {

    public static String getString(ItemStack item, String key) {
        NBTItem nbtItem = new NBTItem(item);
        return nbtItem.getString(key);
    }

    public static UUID getUUID(ItemStack item, String identifier) {
        NBTItem nbtItem = new NBTItem(item);
        return nbtItem.getUUID(identifier);
    }

    public static UUID getItemId(ItemStack item) {
        NBTItem nbtItem = new NBTItem(item);
        return nbtItem.getUUID("itemId");
    }

    public static void setItemId(ItemStack item, UUID itemId) {
        NBTItem nbtItem = new NBTItem(item);
        nbtItem.setUUID("itemId", itemId);
        applyNBT(nbtItem, item);
    }

    public static void applyNBT(NBTItem nbtItem, ItemStack item) {
        nbtItem.applyNBT(item);
    }
}
