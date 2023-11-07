package me.imlukas.prisoncore.modules.newitems.event;

import me.imlukas.prisoncore.modules.AbstractModule;
import org.bukkit.Bukkit;
import org.bukkit.event.Event;
import org.bukkit.event.EventPriority;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;

public class SimpleEventBus implements EventBus {

    private final JavaPlugin plugin;
    private final Map<Class<? extends Event>, AttachableConsumer<? extends Event>> subscriptions = new ConcurrentHashMap<>();

    public SimpleEventBus(AbstractModule module) {
        this.plugin = module.getPlugin();
    }

    @Override
    public <T extends Event> void subscribe(Class<T> eventClass, Consumer<T> action) {
        AttachableConsumer<T> existing = (AttachableConsumer<T>) subscriptions.get(eventClass);

        if(existing == null) {
            existing = new AttachableConsumer<>();
            subscriptions.put(eventClass, existing);

            AttachableConsumer<T> finalExisting = existing;

            Bukkit.getPluginManager().registerEvent(eventClass, this, EventPriority.NORMAL, (listener, event) -> {
                if (eventClass.isInstance(event)) {
                    finalExisting.accept(eventClass.cast(event));
                }
            }, plugin);
        }

        existing.attach(action);
    }

    @Override
    public void dispose() {
        HandlerList.unregisterAll(this);
    }
}
