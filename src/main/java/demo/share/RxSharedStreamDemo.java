/*
 * Copyright 2002-2015 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package demo.share;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import rx.Observable;

/**
 * Shared stream with fan-out, i.e. each element to each Observer.
 */
public class RxSharedStreamDemo {

	private static Logger logger = LoggerFactory.getLogger(RxSharedStreamDemo.class);


	public static void main(String[] args) throws IOException {

		Observable<Long> observable = Observable.interval(1, TimeUnit.SECONDS).share();

		observable.subscribe(n -> logger.debug("\t A[{}]", n));

		// 2nd subscribe 5 seconds later (try with and without this line...)
		sleep(5);

		observable.subscribe(n -> logger.debug("\t\t\t B[{}]", n));

		System.in.read();

		// Slow consumer: add sleep(5) in 2nd subscribe action

		// Decouple slow consumer: add observeOn(Schedulers.computation() before 2nd subscribe

	}

	private static void sleep(long seconds) {
		try {
			Thread.sleep(seconds * 1000);
		}
		catch (InterruptedException e) {
			logger.debug("Interrupted...");
		}
	}

}
