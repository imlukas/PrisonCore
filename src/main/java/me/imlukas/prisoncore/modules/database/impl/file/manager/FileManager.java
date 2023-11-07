package me.imlukas.prisoncore.modules.database.impl.file.manager;

public interface FileManager<T> {

    void add(T t);

    void remove(T t);
}
