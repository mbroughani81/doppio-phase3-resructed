package shared.request;

import shared.response.Response;

public class GetTweetPicRequest extends Request {

    private int tweetId;

    @Override
    public Response handle(RequestHandler requestHandler) {
        return requestHandler.getTweetPic(this);
    }

    public GetTweetPicRequest(int tweetId) {
        this.tweetId = tweetId;
    }

    public int getTweetId() {
        return tweetId;
    }

    public void setTweetId(int tweetId) {
        this.tweetId = tweetId;
    }
}
