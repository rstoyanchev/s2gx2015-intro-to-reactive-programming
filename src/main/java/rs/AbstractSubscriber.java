package rs;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public abstract class AbstractSubscriber<T> implements Subscriber<T> {

	private Logger logger = LoggerFactory.getLogger(getClass());


	private final RequestStrategy requestStrategy;

	private Subscription subscription;


	protected AbstractSubscriber() {
		this(null);
	}

	protected AbstractSubscriber(RequestStrategy requestStrategy) {
		this.requestStrategy = requestStrategy;
	}


	protected RequestStrategy getRequestStrategy() {
		return this.requestStrategy;
	}

	protected Subscription getSubscription() {
		return this.subscription;
	}


	@Override
	public void onSubscribe(Subscription subscription) {
		if (subscription == null) {
			throw new NullPointerException("Spec 2.13: Signal cannot be null");
		}
		this.subscription = subscription;

		logger.debug("onSubscribe: " + this.subscription);
		onSubscribeInternal();

		long demand = onSubscribeInternal();
		if (demand > 0) {
			getSubscription().request(demand);
		}
		else if (demand < 0) {
			getSubscription().cancel();
		}
	}

	protected long onSubscribeInternal() {
		handleSubscribe();
		return getDemandOnSubscribe();
	}

	protected void handleSubscribe() {
	}

	protected long getDemandOnSubscribe() {
		return (getRequestStrategy() != null ? getRequestStrategy().getInitialDemand() : 0);
	}

	@Override
	public void onNext(T data) {
		logger.debug("onNext: " + data);
		long demand = onNextInternal(data);
		if (demand > 0) {
			getSubscription().request(demand);
		}
		else if (demand < 0) {
			getSubscription().cancel();
		}
	}

	protected long onNextInternal(T data) {
		handleData(data);
		return getDemandOnNext(data);
	}

	protected void handleData(T data) {
	}

	protected long getDemandOnNext(T data) {
		return (getRequestStrategy() != null ? getRequestStrategy().getDemand(data) : 0);
	}

	@Override
	public void onError(Throwable error) {
		logger.error("onError: " + error);
		handleError(error);
	}

	protected void handleError(Throwable error) {
	}

	@Override
	public void onComplete() {
		logger.debug("onComplete");
		handleComplete();
	}

	protected void handleComplete() {
	}

}
