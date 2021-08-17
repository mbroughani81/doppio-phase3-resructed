package shared.response;

public class GetTweetPicResponse implements Response {
    private int tweetId;
    private String imageString;

    @Override
    public void handle(ResponseHandler responseHandler) {
        responseHandler.updateTweetPic(this);
    }

    public GetTweetPicResponse(int tweetId, String imageString) {
        this.tweetId = tweetId;
        this.imageString = imageString;
    }

    public int getTweetId() {
        return tweetId;
    }

    public void setTweetId(int tweetId) {
        this.tweetId = tweetId;
    }

    public String getImageString() {
        return imageString;
    }

    public void setImageString(String imageString) {
        this.imageString = imageString;
    }
}
