package demo.social;

import rx.Observable;

public interface TwitterService {

	Observable<TwitterProfile> getUserProfile(String id);

}


