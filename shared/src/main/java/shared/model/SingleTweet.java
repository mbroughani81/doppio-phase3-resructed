package shared.model;

public class SingleTweet {

    private int tweetId;
    private int userId;
    private String text;
    private String retweeterUsername;

    public SingleTweet(int tweetId, int userId, String text, String retweeterUsername) {
        this.tweetId = tweetId;
        this.userId = userId;
        this.text = text;
        this.retweeterUsername = retweeterUsername;
    }

    public int getTweetId() {
        return tweetId;
    }

    public void setTweetId(int tweetId) {
        this.tweetId = tweetId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getRetweeterUsername() {
        return retweeterUsername;
    }

    public void setRetweeterUsername(String retweeterUsername) {
        this.retweeterUsername = retweeterUsername;
    }
}
