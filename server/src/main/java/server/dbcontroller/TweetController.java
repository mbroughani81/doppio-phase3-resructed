package server.dbcontroller;

import org.apache.logging.log4j.LogManager;
import server.model.LikedTweetList;
import server.model.Tweet;
import server.model.User;
import shared.model.SingleTweet;
import shared.request.NewLikeTweetRequest;
import shared.request.NewRetweetRequest;
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

    public void newRetweet(NewRetweetRequest newRetweetRequest) {
        AuthController authController = new AuthController();
        User user = authController.getUserWithAuthToken(newRetweetRequest.getAuthToken());
        Tweet tweet = new Tweet(
                -1,
                "this is retweeted",
                user.getId(),
                newRetweetRequest.getTweetId());
        context.Tweets.add(tweet);
    }

    public void newLike(NewLikeTweetRequest newLikeTweetRequest) {
        AuthController authController = new AuthController();
        User u = authController.getUserWithAuthToken(newLikeTweetRequest.getAuthToken());
        LikedTweetList likedTweetList = context.LikedTweetLists.get(u.getLikedTweetListId());
        if (likedTweetList.getTweetIds().contains(newLikeTweetRequest.getTweetId()))
            return;
        likedTweetList.getTweetIds().add(newLikeTweetRequest.getTweetId());
        context.LikedTweetLists.update(likedTweetList);
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
