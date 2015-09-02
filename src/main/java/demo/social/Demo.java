package demo.social;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import rx.Observable;


public class Demo {

	private static final TestTwitterService twitterService = new TestTwitterService();

	private static final TestFacebookService facebookService = new TestFacebookService();

	private static final TestLinkedInService linkedInService = new TestLinkedInService();


	static {
		twitterService.add("jdenham", "Mongtomery AL");
		twitterService.add("josorio", "Huntington NY");
		twitterService.add("cfeliciano", "San Diago CA");
		twitterService.add("moden", "Detroit MI");
		twitterService.add("lconrad", "Portland OR");

		facebookService.add("jdenham", "Janelle Denham", "Pop culture lover. Wannabe coffee expert. Beer ninja. Passionate internet aficionado. Introvert.");
		facebookService.add("josorio", "Justina Osorio", "Freelance communicator. Web maven. Internet specialist. Bacon expert. Falls down a lot. Student. Twitter ninja. Avid food advocate.");
		facebookService.add("cfeliciano", "Clemmie Feliciano", "Music geek. Friendly writer. Prone to fits of apathy. Extreme travel enthusiast. Organizer.");
		facebookService.add("moden", "Major Oden", "Passionate web scholar. Prone to fits of apathy. Twitter fanatic. Proud troublemaker. General bacon aficionado.");
		facebookService.add("lconrad", "Lamonica Conrad", "Incurable webaholic. Hardcore organizer. Wannabe music maven. Friendly tv expert. Gamer. Unapologetic beer evangelist. Internet fan.");

		linkedInService.add("tina_nelson", new LinkedInProfile("jannel_denham", "jdenham", "jdenham"));
		linkedInService.add("tina_nelson", new LinkedInProfile("justina_osorio", "josorio", "josorio"));
		linkedInService.add("tina_nelson", new LinkedInProfile("clemmie_feliciano", "cfeliciano", "cfeliciano"));
		linkedInService.add("tina_nelson", new LinkedInProfile("major_oden", "moden", "moden"));
		linkedInService.add("tina_nelson", new LinkedInProfile("lamonica_conrad", "lconrad", "lconrad"));
	}


	public static void main(String[] args) {

		linkedInService.findUsers("reactive").take(5).flatMap(userName ->

				linkedInService.getConnections(userName).take(3).flatMap(connection -> {

					String id = connection.getTwitterId();
					Observable<TwitterProfile> twitterProfile = twitterService.getUserProfile(id);

					id = connection.getFacebookId();
					Observable<FacebookProfile> facebookProfile = facebookService.getUserProfile(id);

					return Observable.zip(twitterProfile, facebookProfile, (tp, fp) ->
							new UserConnectionInfo(userName, connection, tp, fp));

				})
		)
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
