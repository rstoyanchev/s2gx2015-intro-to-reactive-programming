package demo.social;

public class UserConnectionInfo {

	private final String name;

	private final LinkedInProfile linkedInProfile;

	private final TwitterProfile twitterProfile;

	private final FacebookProfile facebookProfile;


	public UserConnectionInfo(String n, LinkedInProfile lp, TwitterProfile tp, FacebookProfile fp) {
		name = n;
		linkedInProfile = lp;
		twitterProfile = tp;
		facebookProfile = fp;
	}

	public String getName() {
		return name;
	}

	public LinkedInProfile getLinkedInProfile() {
		return linkedInProfile;
	}

	public TwitterProfile getTwitterProfile() {
		return twitterProfile;
	}

	public FacebookProfile getFacebookProfile() {
		return facebookProfile;
	}


	public String toString() {
		return "UserConnectionInfo {\n" +
				"\t" + name + "\n" +
				"\t\t" + linkedInProfile + "\n" +
				"\t\t" + twitterProfile + "\n" +
				"\t\t" + facebookProfile + "\n" +
				"}\n";
	}

}
