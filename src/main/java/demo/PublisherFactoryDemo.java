package demo;

import java.util.concurrent.atomic.AtomicInteger;

import org.reactivestreams.Publisher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.reactivestreams.PublisherFactory;
import rs.Subscribers;

public class PublisherFactoryDemo {

	private final static Logger logger = LoggerFactory.getLogger(PublisherFactoryDemo.class);


	public static void main(String[] args) {

		Publisher<String> publisher = PublisherFactory.forEach((subscriber) -> {
			Integer count = subscriber.context().getAndIncrement();
			subscriber.onNext("data[" + count + "]");
		}, subscriber -> new AtomicInteger(0));

//		Publisher<String> publisher = PublisherFactory.create((demand, subscriber) -> {
//			Integer count = subscriber.context().getAndIncrement();
//			for (int i = 0; i < demand; i++) {
//				subscriber.onNext("data[" + count + "]");
//			}
//		}, subscriber -> new AtomicInteger(0));

		publisher.subscribe(Subscribers.simple(
				data -> {
					// data
				},
				throwable -> {
					//error
				},
				aVoid -> {
					// done
				}));
	}


}
