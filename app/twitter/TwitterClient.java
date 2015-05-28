package twitter;

import twitter4j.*;
import twitter4j.conf.ConfigurationBuilder;
import play.Logger;

public class TwitterClient implements ITwitterClient{

	@Override
	public void publishUuid(TwitterStatusMessage message) throws Exception {
		// TODO Auto-generated method stub
		ConfigurationBuilder cb = new ConfigurationBuilder();
		cb.setDebugEnabled(true)
		  .setOAuthConsumerKey("GZ6tiy1XyB9W0P4xEJudQ")
		  .setOAuthConsumerSecret("gaJDlW0vf7en46JwHAOkZsTHvtAiZ3QUd2mD1x26J9w")
		  .setOAuthAccessToken("1366513208-MutXEbBMAVOwrbFmZtj1r4Ih2vcoHGHE2207002")
		  .setOAuthAccessTokenSecret("RMPWOePlus3xtURWRVnv1TgrjTyK7Zk33evp4KKyA");
		TwitterFactory tf = new TwitterFactory(cb.build());
		Twitter twitter = tf.getInstance();
		
		
		try{
	    Status status = twitter.updateStatus(message.getTwitterPublicationString());
	    Logger.info("Twitter: Successfully updated the status to [" + status.getText() + "].");
		} catch (TwitterException e) {
			Logger.error("Twitter: Status update was unsuccessfull.");
		}
		
	}

}
