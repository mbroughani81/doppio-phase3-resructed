package shared.response;

import shared.model.SingleTweet;

import java.util.LinkedList;

public class GetTimelineResponse implements Response {

    private LinkedList<SingleTweet> tweets;

    @Override
    public void handle(ResponseHandler responseHandler) {
        responseHandler.updateTimeline(this);
    }

    public GetTimelineResponse(LinkedList<SingleTweet> tweets) {
        this.tweets = tweets;
    }

    public LinkedList<SingleTweet> getTweets() {
        return tweets;
    }

    public void setTweets(LinkedList<SingleTweet> tweets) {
        this.tweets = tweets;
    }
}
