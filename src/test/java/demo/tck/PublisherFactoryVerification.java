package demo.tck;

import org.reactivestreams.Publisher;
import org.reactivestreams.tck.PublisherVerification;
import org.reactivestreams.tck.TestEnvironment;
import org.testng.SkipException;
import reactor.core.reactivestreams.PublisherFactory;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Stephane Maldini
 */
public class PublisherFactoryVerification extends PublisherVerification<Integer> {

        public PublisherFactoryVerification() {
            super(new TestEnvironment(2000, true), 3500);
        }

    @Override
    public Publisher<Integer> createPublisher(long elements) {

        return PublisherFactory.forEach((subscriber) -> {
            Integer count = subscriber.context().getAndIncrement();
            if(count < elements) {
                subscriber.onNext(count);
            }else{
                subscriber.onComplete();
            }
        }, subscriber -> new AtomicInteger(0));
    }


    @Override
    public Publisher<Integer> createFailedPublisher() {
        throw new SkipException("Not implemented");
    }
}
