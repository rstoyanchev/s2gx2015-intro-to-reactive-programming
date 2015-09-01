package demo.social;

import rx.Observable;

public interface FacebookService {

	Observable<FacebookProfile> getUserProfile(String id);

}


