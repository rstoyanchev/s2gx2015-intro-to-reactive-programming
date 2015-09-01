package demo;

import java.lang.reflect.Method;

import rx.Observable;

public class ObservableMethodListDemo {

	public static void main(String[] args) {

		System.out.println("\nrx.Observable operators:");

		Observable.from(Observable.class.getMethods())
				.filter(m -> Observable.class.isAssignableFrom(m.getReturnType()))
				.map(Method::getName)
				.distinct()
				.toSortedList()
				.subscribe(System.out::println);

		System.out.println("\nOthers:");

		Observable.from(Observable.class.getMethods())
				.filter(m -> Observable.class.equals(m.getDeclaringClass()))
				.filter(m -> !Observable.class.isAssignableFrom(m.getReturnType()))
				.map(Method::getName)
				.distinct()
				.toSortedList()
				.subscribe(System.out::println);
	}

}
