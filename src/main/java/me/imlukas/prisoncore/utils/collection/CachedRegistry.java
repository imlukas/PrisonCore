package me.imlukas.prisoncore.utils.collection;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import me.imlukas.prisoncore.utils.time.Time;

import javax.annotation.Nullable;
import java.util.concurrent.TimeUnit;

public abstract class CachedRegistry<T, V> {

    private final Time flushTime;
    private final Cache<T, V> cacheMap;

    protected CachedRegistry(Time flushTime) {
        this.flushTime = flushTime;
        this.cacheMap = CacheBuilder.newBuilder().expireAfterWrite(this.flushTime.as(TimeUnit.SECONDS), TimeUnit.SECONDS)
                .removalListener(notification -> {
                    System.out.println("Removed " + notification.getKey() + " from cache");
                    afterFlush((V) notification.getValue());
                }).build();
    }

    public void register(T key, V value) {
        cacheMap.put(key, value);
    }

    @Nullable
    public V get(T key) {
        return cacheMap.getIfPresent(key);
    }

    public Time getFlushTime() {
        return flushTime;
    }

    public void unregister(T key) {
        afterFlush(get(key));
        cacheMap.invalidate(key);
    }

    public abstract void afterFlush(V value);
}
