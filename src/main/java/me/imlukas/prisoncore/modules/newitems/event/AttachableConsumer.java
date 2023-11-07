package me.imlukas.prisoncore.modules.newitems.event;

import java.util.function.Consumer;

public class AttachableConsumer<T> {

    private Consumer<T> consumer = (irrelevant) -> {};

    public AttachableConsumer() {
    }

    public AttachableConsumer(Consumer<T> consumer) {
        this.consumer = consumer;
    }

    public void attach(Consumer<T> consumer) {
        Consumer<T> old = this.consumer;

        this.consumer = (t) -> {
            old.accept(t);
            consumer.accept(t);
        };
    }

    public void accept(T t) {
        consumer.accept(t);
    }

}
