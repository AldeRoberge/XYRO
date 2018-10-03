package alde.flash.utils.consumer;

import java.util.function.Consumer;

import flash.events.Event;

/**
 * A method that accepts an Event
 */
public class EventConsumer<T extends Event> {

	private Consumer<T> consumer;
	private Runnable runnable;

	public EventConsumer(Consumer<T> consumer) {
		this.consumer = consumer;
	}

	public EventConsumer(Runnable runnable) {
		this.runnable = runnable;
	}

	public void accept(T event) {
		if (runnable != null) {
			runnable.run();
		} else {
			consumer.accept(event);
		}
	}

}