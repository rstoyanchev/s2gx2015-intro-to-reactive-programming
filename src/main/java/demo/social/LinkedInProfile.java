package demo.social;

public class LinkedInProfile {

	private final String id;

	private final String twitterId;

	private final String facebookId;


	public LinkedInProfile(String id, String twitterId, String facebookId) {
		this.id = id;
		this.twitterId = twitterId;
		this.facebookId = facebookId;
	}


	public String getId() {
		return id;
	}

	public String getTwitterId() {
		return twitterId;
	}

	public String getFacebookId() {
		return facebookId;
	}


	@Override
	public String toString() {
		return "LinkedIn: " + getId();
	}

}


