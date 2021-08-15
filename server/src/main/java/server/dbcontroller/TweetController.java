package server.dbcontroller;

import org.apache.logging.log4j.LogManager;
import server.model.Tweet;
import shared.model.SingleTweet;
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

    public LinkedList<Tweet> getTweetComments(int tweetId) {
        LinkedList<Tweet> tweets = new LinkedList<>();
        for (Tweet tweet : context.Tweets.all()) {
            if (tweet.getParentTweetId() == tweetId)
                tweets.add(tweet);
        }
        return tweets;
    }

    public static LinkedList<SingleTweet> convertToSingleTweet(LinkedList<Tweet> tweets) {
        LinkedList<SingleTweet> singleTweets = new LinkedList<>();
        for (Tweet tweet : tweets) {
            singleTweets.add(convertToSingleTweet(tweet));
        }
        return singleTweets;
    }

    public static SingleTweet convertToSingleTweet(Tweet tweet) {
        return new SingleTweet(
                tweet.getId(),
                tweet.getCreatorId(),
                tweet.getText()
        );
    }

    public Tweet getTweet(int tweetId) {
        return context.Tweets.get(tweetId);
    }

    public LinkedList<Tweet> getTimeline(String username) {
        return context.Tweets.all();
    }
}
