package me.imlukas.prisoncore.modules.newitems.event;

import org.bukkit.event.Event;
import org.bukkit.event.Listener;

import java.util.function.Consumer;

/**
 * Handles dynamic event registration, providing a nice way to make custom items and anything else that needs events
 * and avoids having big event classes with a lot of handlers or just a lot of event classes.
 */
public interface EventBus extends Listener {

    <T extends Event> void subscribe(Class<T> eventClass, Consumer<T> action);

    void dispose();


}
