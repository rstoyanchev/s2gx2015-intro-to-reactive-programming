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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.Environment;
import reactor.core.processor.RingBufferWorkProcessor;
import reactor.rx.Stream;
import reactor.rx.Streams;

/**
 * Shared stream with point-to-point, i.e. each element to one subscriber only.
 */
public class ReactorDistributingSharedStreamDemo {

	private static Logger logger = LoggerFactory.getLogger(ReactorDistributingSharedStreamDemo.class);


	public static void main(String[] args) throws IOException {

		Environment.initialize();

		Stream<Long> stream = Streams.period(1).process(RingBufferWorkProcessor.create());;

		stream.consume(n -> logger.debug("\t A[{}]", n));
		stream.consume(n -> logger.debug("\t\t\t B[{}]", n));

		System.in.read();

	}

}
