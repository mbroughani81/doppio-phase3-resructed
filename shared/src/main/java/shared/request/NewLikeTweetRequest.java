package shared.request;

import shared.response.Response;

public class NewLikeTweetRequest extends Request {

    private int tweetId;

    @Override
    public Response handle(RequestHandler requestHandler) {
        return requestHandler.newLikeTweet(this);
    }

    public NewLikeTweetRequest(int tweetId) {
        this.tweetId = tweetId;
    }

    public int getTweetId() {
        return tweetId;
    }

    public void setTweetId(int tweetId) {
        this.tweetId = tweetId;
    }
}
