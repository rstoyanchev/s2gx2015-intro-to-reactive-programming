package demo;

import java.lang.reflect.Method;
import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.rx.Stream;
import reactor.rx.Streams;
import rx.Observable;

public class OperatorsDemo {

	private static final Logger logger= LoggerFactory.getLogger(OperatorsDemo.class);


	public static void main(String[] args) {

		logger.debug("\n\nRxJava operators:");

		Observable.from(Observable.class.getMethods())
				.filter(m -> Observable.class.isAssignableFrom(m.getReturnType()))
				.map(Method::getName)
				.distinct()
				.toSortedList()
				.forEach(System.out::println);

		logger.debug("\n\nRxJava operators (using Java 8 Stream):");

		java.util.stream.Stream.of(Observable.class.getMethods())
				.filter(m -> Observable.class.isAssignableFrom(m.getReturnType()))
				.map(Method::getName)
				.distinct()
				.sorted()
				.forEach(s -> System.out.print(s + "  "));

		logger.debug("\n\nReactor Stream operators:");

		Streams.from(Stream.class.getMethods())
				.filter(m -> Stream.class.isAssignableFrom(m.getReturnType()))
				.map(Method::getName)
				.distinct()
				.sort()
				.consume(s -> System.out.print(s + "  "));
	}

}
