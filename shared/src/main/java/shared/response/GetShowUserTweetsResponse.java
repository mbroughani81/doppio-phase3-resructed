package shared.response;

import shared.model.SingleTweet;

import java.util.LinkedList;

public class GetShowUserTweetsResponse implements Response {

    private LinkedList<SingleTweet> singleTweets;

    @Override
    public void handle(ResponseHandler responseHandler) {
        responseHandler.updateShowTweetsPage(this);
    }

    public GetShowUserTweetsResponse(LinkedList<SingleTweet> singleTweets) {
        this.singleTweets = singleTweets;
    }

    public LinkedList<SingleTweet> getSingleTweets() {
        return singleTweets;
    }

    public void setSingleTweets(LinkedList<SingleTweet> singleTweets) {
        this.singleTweets = singleTweets;
    }
}
