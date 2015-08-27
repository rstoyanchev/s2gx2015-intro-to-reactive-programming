package demo;

import java.util.ArrayList;
import java.util.List;

import org.reactivestreams.Publisher;
import org.reactivestreams.tck.PublisherVerification;
import org.reactivestreams.tck.TestEnvironment;
import org.testng.SkipException;
import reactor.rx.Streams;

public class ReactorStreamVerification extends PublisherVerification<Integer> {


    public ReactorStreamVerification() {
        super(new TestEnvironment(500, true));
    }


    @Override
    public Publisher<Integer> createPublisher(long elementCount) {

        if(elementCount > 100L) {
            throw new SkipException("Large Publisher Not implemented");
        }

        List<Integer> items = new ArrayList<>();
        for(int i = 1; i <= elementCount ; i++){
            items.add(i);
        }

        return Streams.from(items);
    }

    @Override
    public Publisher<Integer> createFailedPublisher() {
        throw new SkipException("Not implemented");
    }

}
