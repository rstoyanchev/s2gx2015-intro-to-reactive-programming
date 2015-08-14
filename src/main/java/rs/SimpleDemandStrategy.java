package rs;


public class SimpleDemandStrategy implements DemandStrategy {

	private final long initialDemand;


	public SimpleDemandStrategy() {
		this(1);
	}

	public SimpleDemandStrategy(long initialDemand) {
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
