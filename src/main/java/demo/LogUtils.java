package demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.fn.Supplier;
import reactor.rx.action.Action;
import rx.Notification;
import rx.Observable;

public class LogUtils {

	private static Logger logger = LoggerFactory.getLogger(LogUtils.class);


	/**
	 * Helps append signal logging to a Reactor processing chain, e.g.:
	 * <pre>
	 * Streams.just(1)
	 * 	.lift(reactorLog("just", "consume")
	 * 	.consume(...);
	 * </pre>
	 *
	 * @param up label for the upstream Publisher
	 * @param down label of the downstream Subscriber
	 */
	public static <T> Supplier<Action<T, T>> reactorLog(String up, String down) {
		return () -> new LoggerAction<>(up, down);
	}

	/**
	 * Helps to append signal logging to an Rx Observable chain, e.g.:
	 * <pre>
	 * Observable.just(1)
	 *	.compose(rxLog("just", "subscribe"))
	 *	.subscribe(...);
	 * </pre>
	 *
	 * @param up label for the upstream Observable
	 * @param down label of the downstream Observable
	 */
	public static <T> LogTransformer<T> rxLog(String up, String down) {
		return new LogTransformer<>(up, down);
	}

	public static String demandToString(long demand) {
		return (demand == Long.MAX_VALUE ? "request(unbounded)" : "request(" + String.valueOf(demand) + ")");
	}


	private static class LogTransformer<T> implements Observable.Transformer<T, T> {

		private final String up;

		private final String down;


		public LogTransformer(String up, String down) {
			this.up = "  [" + up + " <-- " + down + "] {}";
			this.down = "  [" + up + " --> " + down + "] {}";
		}

		@Override
		public Observable<T> call(Observable<T> observable) {
			return observable
					.doOnRequest(n -> logger.debug(this.up, demandToString(n)))
					.doOnUnsubscribe(() -> logger.debug(this.up, "cancel"))
					.doOnEach((n) -> logger.debug(this.down, signalToString(n)));
		}

		protected static String signalToString(Notification<?> n) {
			return n.getKind() + (n.hasValue() ? ": " + n.getValue() : "");
		}
	}

	private static class LoggerAction<T> extends Action<T, T> {

		private final String up;

		private final String down;


		public LoggerAction(String up, String down) {
			this.up = "  [" + up + " <-- " + down + "] {}";
			this.down = "  [" + up + " --> " + down + "] {}";
		}

		@Override
		public void requestMore(long n) {
			logger.debug(this.up, demandToString(n));
			super.requestMore(n);
		}

		@Override
		public void cancel() {
			logger.debug(this.up, "cancel");
			super.cancel();
		}

		@Override
		protected void doNext(T data) {
			logger.debug(this.down, "onNext: " + data);
			broadcastNext(data);
		}

		@Override
		protected void doError(Throwable error) {
			logger.debug(this.down, "onError: " + error);
			super.doError(error);
		}

		@Override
		protected void doComplete() {
			logger.debug(this.down, "onComplete");
			super.doComplete();
		}
	}

}
