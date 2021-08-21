package server.dbcontroller;

import org.apache.logging.log4j.LogManager;
import server.config.dbcontrollerConfig.TweetControllerConfig;
import server.model.*;
import shared.datatype.Privacy;
import shared.model.SingleTweet;
import shared.request.NewLikeTweetRequest;
import shared.request.NewRetweetRequest;
import shared.request.NewTweetRequest;

import java.util.HashSet;
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
        User u = authController.getUserWithAuthToken(newRetweetRequest.getAuthToken());
        RetweetedTweetList retweetedTweetList = context.RetweetedTweetLists.get(u.getLikedTweetListId());
        retweetedTweetList.getTweetIds().add(newRetweetRequest.getTweetId());
        context.RetweetedTweetLists.update(retweetedTweetList);
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

    public LinkedList<Tweet> getUserPostedTweets(int userId) {
        LinkedList<Tweet> tweets = new LinkedList<>();
        User u = context.Users.get(userId);
        for (Tweet tweet : context.Tweets.all()) {
            if (userId == tweet.getCreatorId())
                tweets.add(tweet);
        }
        return tweets;
    }

    public LinkedList<Tweet> getUserRetweetedTweets(int userId) {
        LinkedList<Tweet> tweets = new LinkedList<>();
        User u = context.Users.get(userId);
        for (int tweetId : context.RetweetedTweetLists.get(u.getRetweetedTweetListId()).getTweetIds()) {
            tweets.add(context.Tweets.get(tweetId));
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

    public static LinkedList<SingleTweet> convertToSingleTweet(LinkedList<Tweet> tweets, String retweeterUsername) {
        LinkedList<SingleTweet> singleTweets = new LinkedList<>();
        for (Tweet tweet : tweets) {
            singleTweets.add(convertToSingleTweet(tweet, retweeterUsername));
        }
        return singleTweets;
    }

    public static SingleTweet convertToSingleTweet(Tweet tweet) {
        return new SingleTweet(
                tweet.getId(),
                tweet.getCreatorId(),
                tweet.getText(),
                null
        );
    }

    public static SingleTweet convertToSingleTweet(Tweet tweet, String retweeterUsername) {
        return new SingleTweet(
                tweet.getId(),
                tweet.getCreatorId(),
                tweet.getText(),
                retweeterUsername
        );
    }

    public Tweet getTweet(int tweetId) {
        return context.Tweets.get(tweetId);
    }

    public LinkedList<SingleTweet> getTimeline(int userrId) {
        AuthController authController = new AuthController();
        User userr = authController.getUser(userrId);
        MutedUserList mutedUserList = context.MutedUserLists.get(userr.getMutedUserListId());
        BlockList blockList = context.BlockLists.get(userr.getBlockListId());
        TweetControllerConfig config = new TweetControllerConfig();

        LinkedList<Integer> goodTweets = new LinkedList<>();
        for (int userId : context.FollowingLists.get(userr.getFollowingListId()).getList()) {
            User user = context.Users.get(userId);
            goodTweets.addAll(context.LikedTweetLists.get(user.getLikedTweetListId()).getTweetIds());
            for (Tweet t : getAllTweet(user.getId()))
                goodTweets.add(t.getId());
        }

        LinkedList<SingleTweet> tweets = new LinkedList<>();
        for (Tweet tweet : context.Tweets.all()) {
            User user = context.Users.get(tweet.getCreatorId());
            if (!user.isAlive())
                continue;
            if (!mutedUserList.getUserIds().contains(user.getId()) &&
                    !blockList.getList().contains(user.getMutedUserListId()) &&
                    tweet.getSpamCounter() < config.getMaxspamlimit() &&
                    goodTweets.contains(tweet.getId())
            ) {
                tweets.add(convertToSingleTweet(tweet));
            }
        }

        // add retweeted
        for (int userId : context.FollowingLists.get(userr.getFollowingListId()).getList()) {
            User u = context.Users.get(userId);
//            if (!u.isAlive())
//                continue;
            for (int tweetId : context.RetweetedTweetLists.get(u.getRetweetedTweetListId()).getTweetIds()) {
                Tweet tweet = context.Tweets.get(tweetId);
                User user = context.Users.get(tweet.getCreatorId());
                if (!mutedUserList.getUserIds().contains(user.getId()) &&
                        !blockList.getList().contains(user.getMutedUserListId()) &&
                        tweet.getSpamCounter() < config.getMaxspamlimit()
                ) {
                    tweets.add(convertToSingleTweet(tweet, u.getUsername()));
                }
            }
        }

        return tweets;
    }

    public LinkedList<Tweet> getExplorer(int userId) {
        AuthController authController = new AuthController();
        User userr = authController.getUser(userId);
        MutedUserList mutedUserList = context.MutedUserLists.get(userr.getMutedUserListId());
        BlockList blockList = context.BlockLists.get(userr.getBlockListId());
        TweetControllerConfig config = new TweetControllerConfig();

        LinkedList<Tweet> tweets = new LinkedList<>();
        for (Tweet tweet : context.Tweets.all()) {
            User user = context.Users.get(tweet.getCreatorId());
            if (!user.isActive() || !user.isAlive())
                continue;
            Profile profile = context.Profiles.get(user.getProfileId());
            if (profile.getPrivacy() == Privacy.PUBLIC &&
                    !mutedUserList.getUserIds().contains(user.getId()) &&
                    !blockList.getList().contains(user.getMutedUserListId()) &&
                    tweet.getSpamCounter() < config.getMaxspamlimit()
            ) {
                tweets.add(tweet);
            }
        }

        return tweets;
    }

    public LinkedList<Tweet> getAllTweet(int userId) {
        LinkedList<Tweet> tweets = new LinkedList<>();
        for (Tweet tweet : context.Tweets.all()) {
            if (tweet.getCreatorId() == userId)
                tweets.add(tweet);
        }
        return tweets;
    }
}
