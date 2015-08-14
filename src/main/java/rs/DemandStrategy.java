package rs;

import org.reactivestreams.Subscription;


public interface DemandStrategy {

	/**
	 * How much demand should be requested initially.
	 * Greater than 0 will result in a call to {@link Subscription#request(long)}
	 * while less than 0 will result in {@link Subscription#cancel()}.
	 */
	long getInitialDemand();

	/**
	 * How much demand should be requested after the given item was received.
	 * Greater than 0 will result in a call to {@link Subscription#request(long)}
	 * while less than 0 will result in {@link Subscription#cancel()}.
	 */
	int getDemand(Object data);

}
