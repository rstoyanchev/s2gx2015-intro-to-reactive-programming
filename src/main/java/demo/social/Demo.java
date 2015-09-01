package demo.social;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import rx.Observable;


public class Demo {

	private static final TestTwitterService twitter = new TestTwitterService();

	private static final TestFacebookService facebook = new TestFacebookService();

	private static final TestLinkedInService linkedIn = new TestLinkedInService();


	static {
		twitter.add("jdenham", "Mongtomery AL");
		twitter.add("josorio", "Huntington NY");
		twitter.add("cfeliciano", "San Diago CA");
		twitter.add("moden", "Detroit MI");
		twitter.add("lconrad", "Portland OR");

		facebook.add("jdenham", "Janelle Denham", "Pop culture lover. Wannabe coffee expert. Beer ninja. Passionate internet aficionado. Introvert.");
		facebook.add("josorio", "Justina Osorio", "Freelance communicator. Web maven. Internet specialist. Bacon expert. Falls down a lot. Student. Twitter ninja. Avid food advocate.");
		facebook.add("cfeliciano", "Clemmie Feliciano", "Music geek. Friendly writer. Prone to fits of apathy. Extreme travel enthusiast. Organizer.");
		facebook.add("moden", "Major Oden", "Passionate web scholar. Prone to fits of apathy. Twitter fanatic. Proud troublemaker. General bacon aficionado.");
		facebook.add("lconrad", "Lamonica Conrad", "Incurable webaholic. Hardcore organizer. Wannabe music maven. Friendly tv expert. Gamer. Unapologetic beer evangelist. Internet fan.");

		linkedIn.add("tina_nelson", new LinkedInProfile("jannel_denham", "jdenham", "jdenham"));
		linkedIn.add("tina_nelson", new LinkedInProfile("justina_osorio", "josorio", "josorio"));
		linkedIn.add("tina_nelson", new LinkedInProfile("clemmie_feliciano", "cfeliciano", "cfeliciano"));
		linkedIn.add("tina_nelson", new LinkedInProfile("major_oden", "moden", "moden"));
		linkedIn.add("tina_nelson", new LinkedInProfile("lamonica_conrad", "lconrad", "lconrad"));
	}


	public static void main(String[] args) {

		linkedIn.findUsers("reactive")
				.take(5)
				.flatMap(userName ->
						linkedIn.getConnections(userName)
								.take(3)
								.flatMap(linkedInConnection -> {

									String twitterId = linkedInConnection.getTwitterId();
									Observable<TwitterProfile> connTwitter = twitter.getUserProfile(twitterId);

									String facebookId = linkedInConnection.getFacebookId();
									Observable<FacebookProfile> connFacebook = facebook.getUserProfile(facebookId);

									return Observable.zip(connTwitter, connFacebook, (t, f) ->
											new UserConnectionInfo(userName, linkedInConnection, t, f));

								}))
				.subscribe(System.out::println);
	}


	/**
	 * Test LinkedIn service.
	 */
	private static class TestLinkedInService implements LinkedInService {

		private final Map<String, Set<LinkedInProfile>> map = new HashMap<>();


		public TestLinkedInService add(String id, LinkedInProfile connection) {
			Set<LinkedInProfile> set = map.get(id);
			if (set == null) {
				set = new LinkedHashSet<>();
				map.put(id, new LinkedHashSet<>());
			}
			set.add(connection);
			return this;
		}

		@Override
		public Observable<String> findUsers(String skill) {
			return Observable.from(map.keySet());
		}

		@Override
		public Observable<LinkedInProfile> getConnections(String id) {
			return (map.containsKey(id) ? Observable.from(map.get(id)) : Observable.empty());
		}
	}

	/**
	 * Test Twitter service.
	 */
	private static class TestTwitterService implements TwitterService {

		private final Map<String, TwitterProfile> map = new HashMap<>();


		public TestTwitterService add(String id, String location) {
			map.put(id, new TwitterProfile(id, location));
			return this;
		}

		@Override
		public Observable<TwitterProfile> getUserProfile(String id) {
			return (map.containsKey(id) ? Observable.just(map.get(id)) : Observable.empty());
		}
	}

	/**
	 * Test Facebook service.
	 */
	private static class TestFacebookService implements FacebookService {

		private final Map<String, FacebookProfile> map = new HashMap<>();


		public TestFacebookService add(String id, String name, String bio) {
			map.put(id, new FacebookProfile(id, name, bio));
			return this;
		}

		@Override
		public Observable<FacebookProfile> getUserProfile(String id) {
			return (map.containsKey(id) ? Observable.just(map.get(id)) : Observable.empty());
		}
	}

}
