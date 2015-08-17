package demo.tck;

import demo.NaivePublisherDemo;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscription;
import org.reactivestreams.tck.PublisherVerification;
import org.reactivestreams.tck.TestEnvironment;
import org.testng.SkipException;

/**
 * @author Stephane Maldini
 */
public class NaivePublisherVerification  extends PublisherVerification<Integer> {

        public NaivePublisherVerification() {
            super(new TestEnvironment(2000, true), 3500);
        }

    @Override
    public Publisher<Integer> createPublisher(long elements) {

        if(elements > 100L)
            throw new SkipException("Large Publisher Not implemented");

        int size = (int)elements;
        Integer[] datasource = new Integer[size];

        for(int i = 0; i < size; i++){
            datasource[i] = i;
        }


        return new NaivePublisherDemo.NaiveMultiItemPublisher(datasource);
    }


    @Override
    public Publisher<Integer> createFailedPublisher() {
        return s -> {
            s.onSubscribe(new Subscription() {
                @Override
                public void request(long n) {
                }

                @Override
                public void cancel() {
                }
            });
            s.onError(new Exception("test"));

        };
    }
}
