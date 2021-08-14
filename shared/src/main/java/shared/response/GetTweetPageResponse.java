package shared.response;

import shared.model.SingleTweet;

import java.util.LinkedList;

public class GetTweetPageResponse implements Response {

    private int tweetId;
    private SingleTweet mainTweet;
    private LinkedList<SingleTweet> tweets;

    @Override
    public void handle(ResponseHandler responseHandler) {
        responseHandler.updateTweetPage(this);
    }

    public GetTweetPageResponse(int tweetId, SingleTweet mainTweet, LinkedList<SingleTweet> tweets) {
        this.tweetId = tweetId;
        this.mainTweet = mainTweet;
        this.tweets = tweets;
    }

    public int getTweetId() {
        return tweetId;
    }

    public void setTweetId(int tweetId) {
        this.tweetId = tweetId;
    }

    public SingleTweet getMainTweet() {
        return mainTweet;
    }

    public void setMainTweet(SingleTweet mainTweet) {
        this.mainTweet = mainTweet;
    }

    public LinkedList<SingleTweet> getTweets() {
        return tweets;
    }

    public void setTweets(LinkedList<SingleTweet> tweets) {
        this.tweets = tweets;
    }
}
