package demo;

import org.reactivestreams.Publisher;
import org.reactivestreams.tck.PublisherVerification;
import org.reactivestreams.tck.TestEnvironment;
import org.testng.SkipException;

public class NaivePublisherVerification  extends PublisherVerification<Integer> {


    public NaivePublisherVerification() {
        super(new TestEnvironment(2000, true), 3500);
    }


    @Override
    public Publisher<Integer> createPublisher(long elements) {

        if(elements > 100L) {
            throw new SkipException("Large Publisher Not implemented");
        }

        return RsBackpressureDemo.createNaivePublisher(elements);
    }


    @Override
    public Publisher<Integer> createFailedPublisher() {
        throw new SkipException("Not implemented");
    }

}
