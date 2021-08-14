package shared.request;

import shared.response.Response;

public class NewTweetRequest extends Request {

    private int parentTweetId;
    private String text;
    private String imageString;
    private int creatorId;

    @Override
    public Response handle(RequestHandler requestHandler) {
        return requestHandler.newTweet(this);
    }

    public NewTweetRequest(int parentTweetId, String text, String imageString) {
        this.parentTweetId = parentTweetId;
        this.text = text;
        this.imageString = imageString;
        this.creatorId = -1;
    }

    public NewTweetRequest(int parentTweetId, String text, String imageString, int creatorId) {
        this.parentTweetId = parentTweetId;
        this.text = text;
        this.imageString = imageString;
        this.creatorId = creatorId;
    }

    public int getParentTweetId() {
        return parentTweetId;
    }

    public void setParentTweetId(int parentTweetId) {
        this.parentTweetId = parentTweetId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getImageString() {
        return imageString;
    }

    public void setImageString(String imageString) {
        this.imageString = imageString;
    }

    public int getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(int creatorId) {
        this.creatorId = creatorId;
    }
}
