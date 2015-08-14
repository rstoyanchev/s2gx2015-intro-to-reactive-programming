package rs;


public final class UnboundedDemandStrategy implements DemandStrategy {


	@Override
	public long getInitialDemand() {
		return Long.MAX_VALUE;
	}

	@Override
	public int getDemand(Object data) {
		return 0;
	}

}
