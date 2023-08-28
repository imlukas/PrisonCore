package me.imlukas.prisoncore.modules.database;

import java.util.UUID;

public interface Database {

    String getIdentifier();

    default boolean connect() { return true; }

    default void disconnect() {}

    default boolean isConnected() {
        return false;
    }
}
