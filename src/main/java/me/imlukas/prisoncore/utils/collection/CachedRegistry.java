package me.imlukas.prisoncore.utils.collection;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import me.imlukas.prisoncore.utils.time.Time;

import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

public class CachedRegistry<T, V> {

    private final Time flushTime;
    private final Cache<T, V> cacheMap;
    private Consumer<V> afterFlush;

    public CachedRegistry(Time flushTime) {
        this.flushTime = flushTime;
        this.cacheMap = CacheBuilder.newBuilder().expireAfterWrite(flushTime.as(TimeUnit.SECONDS), TimeUnit.SECONDS)
                .removalListener(notification -> {
                    if (afterFlush != null) {
                        afterFlush.accept((V) notification.getValue());
                    }
                }).build();


    }

    public void register(T key, V value) {
        cacheMap.put(key, value);
    }

    public V get(T key) {
        return cacheMap.getIfPresent(key);
    }

    public void unregister(T key) {
        cacheMap.invalidate(key);
    }

    public void afterFlush(Consumer<V> afterFlush) {
            this.afterFlush = afterFlush;
    }

    public Consumer<V> getAfterFlush() {
        return afterFlush;
    }
}
