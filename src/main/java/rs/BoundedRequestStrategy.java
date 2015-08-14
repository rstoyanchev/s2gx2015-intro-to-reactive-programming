package rs;


public class BoundedRequestStrategy extends SimpleRequestStrategy {

	private final long maxItems;

	private volatile long itemCount;


	public BoundedRequestStrategy(long initialDemand, long maxItems) {
		super(initialDemand);
		this.maxItems = maxItems;
	}


	@Override
	public int getDemand(Object data) {
		this.itemCount++;
		return (this.itemCount < this.maxItems ? super.getDemand(data) : -1);
	}

}
