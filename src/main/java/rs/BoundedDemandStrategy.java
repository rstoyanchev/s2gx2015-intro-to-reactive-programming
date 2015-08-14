package rs;


public class BoundedDemandStrategy extends SimpleDemandStrategy {

	private final long maxItems;

	private volatile long itemCount;


	public BoundedDemandStrategy(long initialDemand, long maxItems) {
		super(initialDemand);
		this.maxItems = maxItems;
	}


	@Override
	public int getDemand(Object data) {
		this.itemCount++;
		return (this.itemCount < this.maxItems ? super.getDemand(data) : -1);
	}

}
