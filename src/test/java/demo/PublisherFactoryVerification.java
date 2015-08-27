package demo;

import org.reactivestreams.Publisher;
import org.reactivestreams.tck.PublisherVerification;
import org.reactivestreams.tck.TestEnvironment;
import org.testng.SkipException;

public class PublisherFactoryVerification extends PublisherVerification<Integer> {


    public PublisherFactoryVerification() {
        super(new TestEnvironment(2000, true), 3500);
    }


    @Override
    public Publisher<Integer> createPublisher(long elementCount) {

        if(elementCount > 100L) {
            throw new SkipException("Large Publisher Not implemented");
        }

        return FirstPublisherDemo.createWithPublisherFactory(elementCount);
    }

    @Override
    public Publisher<Integer> createFailedPublisher() {
        throw new SkipException("Not implemented");
    }

}
