package rs;


public class SimpleRequestStrategy implements RequestStrategy {

	private final long initialDemand;


	public SimpleRequestStrategy() {
		this(1);
	}

	public SimpleRequestStrategy(long initialDemand) {
		this.initialDemand = initialDemand;
	}


	@Override
	public long getInitialDemand() {
		return this.initialDemand;
	}

	@Override
	public int getDemand(Object data) {
		return 1;
	}

}
