package me.imlukas.prisoncore.utils.PDCUtils;

import com.jeff_media.morepersistentdatatypes.DataType;
import com.jeff_media.morepersistentdatatypes.datatypes.collections.MapDataType;
import me.imlukas.prisoncore.PrisonCore;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.function.Consumer;

public class PDCWrapper {

    private final PrisonCore plugin;
    private final ItemStack itemStack;

    public PDCWrapper(PrisonCore plugin, ItemStack itemStack) {
        this(plugin, itemStack, true);
    }

    public PDCWrapper(PrisonCore plugin, ItemStack itemStack, boolean clone) {
        this.plugin = plugin;
        this.itemStack = itemStack;
    }

    public static void modifyItem(PrisonCore plugin, ItemStack item, Consumer<PDCWrapper> modifier) {
        PDCWrapper wrapper = new PDCWrapper(plugin, item, false);
        modifier.accept(wrapper);
    }

    public void setString(String key, String value) {
        set(key, PersistentDataType.STRING, value);
    }

    public void setInteger(String key, int value) {
        set(key, PersistentDataType.INTEGER, value);
    }

    public void setBoolean(String key, boolean value) {
        set(key, PersistentDataType.BYTE, (byte) (value ? 1 : 0));
    }

    public void setUUID(String key, UUID value) {
        set(key, PersistentDataType.STRING, value.toString());
    }

    public void setMap(String key, Map<String, Integer> value) {
        setMap(key, DataType.asMap(DataType.STRING, DataType.INTEGER), value);
    }

    public String getString(String key) {
        return get(key, PersistentDataType.STRING);
    }

    public int getInteger(String key) {
        return get(key, PersistentDataType.INTEGER);
    }

    public boolean getBoolean(String key) {
        return get(key, PersistentDataType.BYTE) == 1;
    }

    public UUID getUUID(String key) {
        return UUID.fromString(get(key, PersistentDataType.STRING));
    }

    public Map<String, Integer> getMap(String key) {
        return getMap(key, DataType.asMap(DataType.STRING, DataType.INTEGER));
    }

    public ItemStack getModifiedItem() {
        return itemStack;
    }

    private NamespacedKey createKey(String name) {
        return new NamespacedKey(plugin, name);
    }

    private void modifyMeta(Consumer<ItemMeta> consumer) {
        ItemMeta meta = itemStack.getItemMeta();
        consumer.accept(meta);
        itemStack.setItemMeta(meta);
    }

    private <T, V> void setMap(String key, MapDataType<Map<T, V>, T, V> type, Map<T, V> value) {
        modifyMeta(meta -> meta.getPersistentDataContainer().set(createKey(key), type, value));
    }

    private <T, V> Map<T, V> getMap(String key, MapDataType<Map<T, V>, T, V> type) {
        return itemStack.getItemMeta().getPersistentDataContainer().get(createKey(key), type);
    }

    private <T> void set(String key, PersistentDataType<T, T> type, T value) {
        modifyMeta(meta -> meta.getPersistentDataContainer().set(createKey(key), type, value));
    }

    private <T> T get(String key, PersistentDataType<T, T> type) {
        return itemStack.getItemMeta().getPersistentDataContainer().get(createKey(key), type);
    }
}
