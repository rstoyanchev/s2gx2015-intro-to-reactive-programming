package demo;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NaivePublisherDemo {

	private final static Logger logger = LoggerFactory.getLogger(NaivePublisherDemo.class);


	public static void main(String[] args) {

	}


	private static class NaiveSingleItemPublisher<T> implements Publisher<T> {

		private final T item;

		public NaiveSingleItemPublisher(T item) {
			this.item = item;
		}

		@Override
		public void subscribe(final Subscriber<? super T> subscriber) {

			subscriber.onSubscribe(new Subscription() {

				@Override
				public void request(long n) {
					if (n > 0) {
						subscriber.onNext(item);
						subscriber.onComplete();
					}
				}

				@Override
				public void cancel() {
				}
			});
		}
	}

	// What if Subscriber calls request more than once? Or calls cancel? Need per-Subscriber "terminated" state..
	// What if onNext throws an Exception?
	// Ensure single call to either onError or onComplete

	private static class NaiveMultiItemPublisher<T> implements Publisher<T> {

		private final List<T> items;

		public NaiveMultiItemPublisher(T... items) {
			this.items = Arrays.asList(items);
		}

		@Override
		public void subscribe(final Subscriber<? super T> subscriber) {

			subscriber.onSubscribe(new Subscription() {

				private Iterator<T> iterator = items.iterator();

				@Override
				public void request(long n) {
					for (int i = 0; i < n; i++) {
						if (this.iterator.hasNext()) {
							subscriber.onNext(this.iterator.next());
						}
						else {
							subscriber.onComplete();
						}
					}
				}

				@Override
				public void cancel() {
				}
			});
		}
	}

	// Same questions plus...
	// 3.3 Subscription.request MUST place an upper bound on possible synchronous recursion between Publisher and Subscriber

}
