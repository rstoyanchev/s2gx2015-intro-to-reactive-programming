package demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.rx.Streams;


public class ErrorHandlingDemo {

	private static final Logger logger = LoggerFactory.getLogger(ErrorHandlingDemo.class);


	public static void main(String[] args) {

		try {
			Streams.just(1, 2, 3, 4, 5)
					.map(i -> {
						throw new NullPointerException();
					})
					.consume(
							element -> {},
							error -> logger.debug("Oooh error!")
					);
		}
		catch (Throwable ex) {
			logger.error("Crickets...");
		}
	}

}
