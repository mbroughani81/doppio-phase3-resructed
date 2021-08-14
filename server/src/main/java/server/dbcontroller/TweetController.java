package server.dbcontroller;

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
        return context.Tweets.add(tweet);
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
