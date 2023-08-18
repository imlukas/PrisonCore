package me.imlukas.prisoncore.modules.database;

public interface Database {

    String getIdentifier();

    boolean connect();

    void disconnect();

    boolean isConnected();
}
