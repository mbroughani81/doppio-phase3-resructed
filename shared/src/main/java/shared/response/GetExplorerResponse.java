package shared.response;

import shared.model.SingleTweet;

import java.util.LinkedList;

public class GetExplorerResponse implements Response {

    private LinkedList<SingleTweet> tweets;

    @Override
    public void handle(ResponseHandler responseHandler) {
        responseHandler.updateExplorer(this);
    }

    public GetExplorerResponse(LinkedList<SingleTweet> tweets) {
        this.tweets = tweets;
    }

    public LinkedList<SingleTweet> getTweets() {
        return tweets;
    }

    public void setTweets(LinkedList<SingleTweet> tweets) {
        this.tweets = tweets;
    }
}
