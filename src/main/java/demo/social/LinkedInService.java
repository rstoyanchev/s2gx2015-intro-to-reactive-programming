package demo.social;

import rx.Observable;

public interface LinkedInService {

	Observable<String> findUsers(String skill);

	Observable<LinkedInProfile> getConnections(String id);

}


