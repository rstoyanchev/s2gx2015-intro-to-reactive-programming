package rs;

import java.util.function.Consumer;

import org.reactivestreams.Subscriber;


public class SubscriberFactory {

	public static final SimpleRequestStrategy SIMPLE_REQUEST_STRATEGY = new SimpleRequestStrategy();

	public static final UnboundedRequestStrategy UNBOUNDED_REQUEST_STRATEGY = new UnboundedRequestStrategy();


	/**
	 * Consume one at a time.
	 */
	public static <T> Subscriber<T> simple(Consumer<T> consumer) {
		return simple(consumer, null, null);
	}

	/**
	 * Consume one at a time, also receiving error and completion callbacks.
	 */
	public static <T> Subscriber<T> simple(Consumer<T> consumer, Consumer<Throwable> errorConsumer, Runnable completionTask) {
		return new FunctionalSubscriber<>(SIMPLE_REQUEST_STRATEGY, consumer, errorConsumer, completionTask);
	}

	/**
	 * Request {@code initialDemand} at first, and one item at a time thereafter.
	 */
	public static <T> Subscriber<T> simple(long initialDemand, Consumer<T> consumer) {
		return simple(initialDemand, consumer, null, null);
	}

	/**
	 * Request {@code initialDemand} at first, and one item at a time thereafter,
	 * also receiving error and completion callbacks.
	 */
	public static <T> Subscriber<T> simple(long initialDemand, Consumer<T> consumer,
			Consumer<Throwable> errorConsumer, Runnable completionTask) {

		return new FunctionalSubscriber<>(new SimpleRequestStrategy(initialDemand), consumer,
				errorConsumer, completionTask);
	}

	/**
	 * Request {@code initialDemand} initially, one at a time thereafter, up to
	 * a maximum of {@code maxDemand}.
	 */
	public static <T> Subscriber<T> bounded(long initialDemand, long maxDemand, Consumer<T> consumer) {
		return new FunctionalSubscriber<T>(new BoundedRequestStrategy(initialDemand, maxDemand), consumer, null, null);
	}

	/**
	 * Request all the Publisher has.
	 */
	public static <T> Subscriber<T> unbounded(Consumer<T> consumer) {
		return new FunctionalSubscriber<T>(UNBOUNDED_REQUEST_STRATEGY, consumer, null, null);
	}

}
