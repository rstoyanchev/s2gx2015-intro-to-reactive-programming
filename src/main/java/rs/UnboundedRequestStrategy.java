package rs;


public final class UnboundedRequestStrategy implements RequestStrategy {


	@Override
	public long getInitialDemand() {
		return Long.MAX_VALUE;
	}

	@Override
	public int getDemand(Object data) {
		return 0;
	}

}
