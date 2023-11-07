package me.imlukas.prisoncore.utils.storage;

import me.imlukas.prisoncore.modules.AbstractModule;

public abstract class FileHandler extends YMLBase{

    public FileHandler(AbstractModule module, String name) {
        super(module, name, true);
    }

    public abstract void load();
}
