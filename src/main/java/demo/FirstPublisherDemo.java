package demo;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import reactor.core.reactivestreams.PublisherFactory;


public class FirstPublisherDemo {


	public static void main(String[] args) {

		// createNaivePublisher, 10 items (ok!)

		createNaivePublisher(10).subscribe(createSubscriber());

		// src/test/java -> NaivePublisherVerification (fail!)

		// src/test/java -> PublisherFactoryVerification (ok!)

		// see createWithPublisherFactory

		// back-pressure: experiment w/ request(n) in createSubscriber...

		// createNaivePublisher, 10000 items (StackOverflow!)

		// createWithPublisherFactory, 10000 items (ok!)

	}


	static Publisher<Integer> createNaivePublisher(long itemCount) {

		final List<Integer> items = initIntSequence(itemCount);

		return new Publisher<Integer>() {

			@Override
			public void subscribe(final Subscriber<? super Integer> subscriber) {

				subscriber.onSubscribe(new Subscription() {

					private Iterator<Integer> iterator = items.iterator();

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
		};
	}

	static Publisher<Integer> createWithPublisherFactory(long itemCount) {

		final List<Integer> items = initIntSequence(itemCount);

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

	private static List<Integer> initIntSequence(long itemCount) {
		List<Integer> items = new ArrayList<>();
		for(int i = 1; i <= itemCount ; i++){
			items.add(i);
		}
		return items;
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

}
