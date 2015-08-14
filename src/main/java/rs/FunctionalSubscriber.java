package rs;

import java.util.function.Consumer;


public class FunctionalSubscriber<T> extends AbstractSubscriber<T> {

	private final Consumer<T> dataConsumer;

	private final Consumer<Throwable> errorConsumer;

	private final Runnable completionTask;


	/**
	 * Protected constructor. Use {@link SubscriberFactory}.
	 */
	protected FunctionalSubscriber(RequestStrategy requestStrategy, Consumer<T> consumer,
			Consumer<Throwable> errorConsumer, Runnable completionTask) {

		super(requestStrategy);
		this.dataConsumer = consumer;
		this.errorConsumer = errorConsumer;
		this.completionTask = completionTask;
	}


	@Override
	protected void handleData(T data) {
		if (this.dataConsumer != null) {
			this.dataConsumer.accept(data);
		}
	}

	@Override
	protected void handleError(Throwable error) {
		if (this.errorConsumer != null) {
			this.errorConsumer.accept(error);
		}
	}

	@Override
	protected void handleComplete() {
		if (this.completionTask != null) {
			this.completionTask.run();
		}
	}

}
