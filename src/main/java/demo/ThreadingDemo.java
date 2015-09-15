package demo;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import rx.Observable;
import rx.Subscriber;
import rx.schedulers.Schedulers;

import static demo.LogUtils.rxLog;

public class ThreadingDemo {

	private static Logger logger = LoggerFactory.getLogger(ThreadingDemo.class);


	public static void main(String[] args) throws IOException {

		Observable.just(1, 2, 3)
				.compose(rxLog("just", "subscribeOn"))
				.observeOn(Schedulers.computation())
				.compose(rxLog("subscribeOn", "subscribe"))
				.subscribe(createSubscriber());

		System.in.read();

	}


	@SuppressWarnings("unused")
	private static Subscriber<Integer> createSubscriber() {

		return new Subscriber<Integer>() {


			@Override
			public void onStart() {
				logger.debug("onStart");
				request(1);
			}

			@Override
			public void onNext(Integer data) {
				logger.debug("onNext: " + String.valueOf(data));
				request(1);
			}

			@Override
			public void onError(Throwable error) {
				logger.debug("onError: " + error);
			}

			@Override
			public void onCompleted() {
				logger.debug("onComplete");
			}
		};
	}

}
