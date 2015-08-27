package demo;

import java.util.ArrayList;
import java.util.List;

import org.reactivestreams.Publisher;
import org.reactivestreams.tck.PublisherVerification;
import org.reactivestreams.tck.TestEnvironment;
import org.testng.SkipException;
import rx.Observable;
import rx.RxReactiveStreams;

public class RxObservableVerification extends PublisherVerification<Integer> {


    public RxObservableVerification() {
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

        return RxReactiveStreams.toPublisher(Observable.from(items));
    }

    @Override
    public Publisher<Integer> createFailedPublisher() {

        // Null because we always successfully subscribe.
        // If the observable is in error state, it will subscribe and then emit the error as the first item
        // This is not an “error state” publisher as defined by RS

        // Source for above comment:
        // https://github.com/ReactiveX/RxJavaReactiveStreams/blob/d581cdf1768db20c8f81a6661c71bc68b860f51b/rxjava-reactive-streams/src/test/java/rx/reactivestreams/TckSynchronousPublisherTest.java#L41-L44

        return null;
    }

}
