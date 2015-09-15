package demo;

import java.lang.reflect.Method;

import reactor.rx.Stream;
import reactor.rx.Streams;

public class ReactorStreamOperatorsDemo {


	public static void main(String[] args) {

		System.out.println("Reactor Stream operators:");

		Streams.from(Stream.class.getMethods())
				.filter(m -> Stream.class.isAssignableFrom(m.getReturnType()))
				.map(Method::getName)
				.distinct()
				.sort()
				.consume(s -> System.out.print(s + "  "));

		System.out.println("\n\nReactor operators via Java 8 Stream:");

		java.util.stream.Stream.of(Stream.class.getMethods())
				.filter(m -> Stream.class.isAssignableFrom(m.getReturnType()))
				.map(Method::getName)
				.distinct()
				.sorted()
				.forEach(s -> System.out.print(s + "  "));
	}

}
