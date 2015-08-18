package demo;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import reactor.core.reactivestreams.PublisherFactory;


public class RsBackpressureDemo {


	public static void main(String[] args) {

		createNaivePublisher(10).subscribe(createSubscriber());

		// ...


	}


	public static Publisher<Integer> createNaivePublisher(long itemCount) {

		List<Integer> items = new ArrayList<>();
		for(int i = 0; i < itemCount ; i++){
			items.add(i);
		}

		return new NaivePublisher<>(items);
	}

	private static Subscriber<Integer> createSubscriber() {

		return new Subscriber<Integer>() {

			private Subscription subscription;

			@Override
			public void onSubscribe(Subscription subscription) {
				System.out.println("onSubscribe");
				this.subscription = subscription;
				this.subscription.request(1);
			}

			@Override
			public void onNext(Integer data) {
				System.out.println(data);
				this.subscription.request(1);
			}

			@Override
			public void onError(Throwable error) {
				System.out.println("onError: " + error);
			}

			@Override
			public void onComplete() {
				System.out.println("onComplete");
			}
		};
	}

	public static Publisher<Integer> createPublisher(long itemCount) {

		List<Integer> items = new ArrayList<>();
		for(int i = 0; i < itemCount ; i++){
			items.add(i);
		}

		return PublisherFactory.forEach((subscriber) -> {
			Iterator<Integer> iterator = subscriber.context();
			if (iterator.hasNext()) {
				subscriber.onNext(iterator.next());
			}
			else {
				subscriber.onComplete();
			}
		}, subscriber -> items.iterator());
	}


	static class NaivePublisher<T> implements Publisher<T> {

		private final List<T> items;

		public NaivePublisher(List<T> items) {
			this.items = items;
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

}
