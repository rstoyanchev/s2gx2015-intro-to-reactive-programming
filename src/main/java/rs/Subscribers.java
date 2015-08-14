package rs;

import java.util.function.Consumer;

import org.reactivestreams.Subscriber;


public class Subscribers {

	private static final DemandStrategy SIMPLE_DEMAND_STRATEGY = new SimpleDemandStrategy();

	private static final DemandStrategy UNBOUNDED_DEMAND_STRATEGY = new UnboundedDemandStrategy();


	/**
	 * Consume one at a time.
	 */
	public static <T> Subscriber<T> simple(Consumer<T> dataCallback) {
		return simple(dataCallback, null, null);
	}

	/**
	 * Consume one at a time, also receiving error and completion callbacks.
	 */
	public static <T> Subscriber<T> simple(Consumer<T> dataCallback, Consumer<Throwable> errorCallback,
			Consumer<Void> completionCallback) {

		return new FunctionalSubscriber<>(SIMPLE_DEMAND_STRATEGY, dataCallback,
				errorCallback, completionCallback);
	}

	/**
	 * Request {@code initialDemand} at first, and one item at a time thereafter.
	 */
	public static <T> Subscriber<T> simple(long initialDemand, Consumer<T> dataCallback) {
		return simple(initialDemand, dataCallback, null, null);
	}

	/**
	 * Request {@code initialDemand} at first, and one item at a time thereafter,
	 * also receiving error and completion callbacks.
	 */
	public static <T> Subscriber<T> simple(long initialDemand, Consumer<T> dataCallback,
			Consumer<Throwable> errorCallback, Consumer<Void> completionCallback) {

		DemandStrategy demandStrategy = new SimpleDemandStrategy(initialDemand);
		return new FunctionalSubscriber<>(demandStrategy, dataCallback, errorCallback, completionCallback);
	}

	/**
	 * Request {@code initialDemand} initially, one at a time thereafter, up to
	 * a maximum of {@code maxDemand}.
	 */
	public static <T> Subscriber<T> bounded(long initialDemand, long maxDemand, Consumer<T> dataCallback) {
		DemandStrategy demandStrategy = new BoundedDemandStrategy(initialDemand, maxDemand);
		return new FunctionalSubscriber<T>(demandStrategy, dataCallback, null, null);
	}

	/**
	 * Request all THAT the Publisher has.
	 */
	public static <T> Subscriber<T> unbounded(Consumer<T> consumer) {
		return new FunctionalSubscriber<T>(UNBOUNDED_DEMAND_STRATEGY, consumer, null, null);
	}

}
