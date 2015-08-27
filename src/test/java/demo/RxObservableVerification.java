package demo;

import java.util.List;

import org.reactivestreams.Publisher;
import org.reactivestreams.tck.PublisherVerification;
import org.reactivestreams.tck.TestEnvironment;
import org.testng.SkipException;
import rx.Observable;
import rx.RxReactiveStreams;

import static demo.FirstObservableDemo.initIntSequence;

public class RxObservableVerification extends PublisherVerification<Integer> {


    public RxObservableVerification() {
        super(new TestEnvironment(500, true));
    }


    @Override
    public Publisher<Integer> createPublisher(long elementCount) {

        if(elementCount > 100L) {
            throw new SkipException("Large Publisher Not implemented");
        }

        Observable<Integer> observable = Observable.range(1, (int) elementCount);

        return RxReactiveStreams.toPublisher(observable);
    }

    @Override
    public Publisher<Integer> createFailedPublisher() {
        return RxReactiveStreams.toPublisher(Observable.<Integer>error(new Exception()));
    }

}
