package me.imlukas.prisoncore.modules.newitems.tool.base.cache;

import me.imlukas.prisoncore.modules.newitems.tool.base.BaseTool;
import me.imlukas.prisoncore.utils.collection.CachedRegistry;
import me.imlukas.prisoncore.utils.time.Time;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class BaseToolCache extends CachedRegistry<UUID, BaseTool> {

    public BaseToolCache() {
        super(new Time(30, TimeUnit.MINUTES));
    }

    @Override
    public void afterFlush(BaseTool value) {
        register(value.getId(), value);
    }
}
