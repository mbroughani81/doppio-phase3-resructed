package server.dbcontroller;

import org.apache.logging.log4j.LogManager;
import server.model.Tweet;
import shared.request.NewTweetRequest;

import java.util.LinkedList;

public class TweetController extends AbstractController {
    public int newTweet(NewTweetRequest newTweetRequest) {
        Tweet tweet = new Tweet(
                newTweetRequest.getParentTweetId(),
                newTweetRequest.getText(),
                newTweetRequest.getCreatorId()
        );

        int id = context.Tweets.add(tweet);
        LogManager.getLogger(TweetController.class).info("new tweet is created with id " + id);
        return id;
    }

    public LinkedList<Tweet> getUserTweets(int userId) {
        LinkedList<Tweet> tweets = new LinkedList<>();
        for (Tweet tweet : context.Tweets.all()) {
            if (userId == tweet.getCreatorId())
                tweets.add(tweet);
        }
        return tweets;
    }

    public Tweet getTweet(int tweetId) {
        return context.Tweets.get(tweetId);
    }

    public LinkedList<Tweet> getTimeline(String username) {
        return context.Tweets.all();
    }
}
