package demo.social;

public class TwitterProfile {

	private final String id;

	private final String location;


	public TwitterProfile(String id, String location) {
		this.id = id;
		this.location = location;
	}


	public String getId() {
		return id;
	}

	public String getLocation() {
		return location;
	}


	@Override
	public String toString() {
		return "Twitter: @" + getId() + ", " + getLocation();
	}

}


