package demo.social;

public class FacebookProfile {

	private final String id;

	private final String name;

	private final String bio;


	public FacebookProfile(String id, String name, String bio) {
		this.id = id;
		this.name = name;
		this.bio = bio;
	}


	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getBio() {
		return bio;
	}


	@Override
	public String toString() {
		return "Facebook: " + getName() + ", \"" + getBio() + "\"";
	}

}


