package demo;


import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import reactor.rx.Stream;
import reactor.rx.Streams;

public class FirstReactorStreamDemo {


	public static void main(String[] args) {

		List<Integer> items = initIntSequence(10);

		// Stream, 10 items

		// Streams.from(items).consume(System.out::println);

		// log()

		// log() + capacity(4) + consume()

		// log() + consumeLater() .. control.requestMore()

		// Reactor Stream:

		Streams.from(Stream.class.getMethods())
		  .filter(m -> Stream.class.isAssignableFrom(m.getReturnType()))
		  .map(Method::getName)
		  .distinct()
		  .sort()
		  .consume(System.out::println);
	}


	private static List<Integer> initIntSequence(long itemCount) {
		List<Integer> items = new ArrayList<>();
		for(int i = 1; i <= itemCount ; i++){
			items.add(i);
		}
		return items;
	}

}
