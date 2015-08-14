package rs;

import java.util.function.Consumer;


public class FunctionalSubscriber<T> extends AbstractSubscriber<T> {

	private final Consumer<T> dataCallback;

	private final Consumer<Throwable> errorCallback;

	private final Consumer<Void> completionCallback;


	/**
	 * Protected constructor. Use {@link Subscribers}.
	 */
	protected FunctionalSubscriber(DemandStrategy demandStrategy, Consumer<T> dataCallback,
			Consumer<Throwable> errorCallback, Consumer<Void> completionCallback) {

		super(demandStrategy);
		this.dataCallback = dataCallback;
		this.errorCallback = errorCallback;
		this.completionCallback = completionCallback;
	}


	@Override
	protected void handleData(T data) {
		if (this.dataCallback != null) {
			this.dataCallback.accept(data);
		}
	}

	@Override
	protected void handleError(Throwable error) {
		if (this.errorCallback != null) {
			this.errorCallback.accept(error);
		}
	}

	@Override
	protected void handleCompletion() {
		if (this.completionCallback != null) {
			this.completionCallback.accept(null);
		}
	}

}
