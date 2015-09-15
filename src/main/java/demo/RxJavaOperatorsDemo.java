package demo;

import java.lang.reflect.Method;

import rx.Observable;

public class RxJavaOperatorsDemo {

	public static void main(String[] args) {

		System.out.println("\n\nRxJava operators:");

		Observable.from(Observable.class.getMethods())
				.filter(m -> Observable.class.isAssignableFrom(m.getReturnType()))
				.map(Method::getName)
				.distinct()
				.toSortedList()
				.forEach(System.out::println);

		System.out.println("\n\nRxJava operators via Java 8 Stream:");

		java.util.stream.Stream.of(Observable.class.getMethods())
				.filter(m -> Observable.class.isAssignableFrom(m.getReturnType()))
				.map(Method::getName)
				.distinct()
				.sorted()
				.forEach(s -> System.out.print(s + "  "));
	}

}
