package server.model;

public class Tweet {
    private int id;

    private int parentTweetId;
    private String text;
    private int creatorId;
    private int sourceId;
    private int spamCounter;

    public Tweet(int parentTweetId, String text, int creatorId) {
        this.id = -1;
        this.parentTweetId = parentTweetId;
        this.text = text;
        this.creatorId = creatorId;
        this.sourceId = -1;
    }

    public Tweet(int parentTweetId, String text, int creatorId, int sourceId) {
        this.id = -1;
        this.parentTweetId = parentTweetId;
        this.text = text;
        this.creatorId = creatorId;
        this.sourceId = sourceId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public int getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(int creatorId) {
        this.creatorId = creatorId;
    }

    public int getSourceId() {
        return sourceId;
    }

    public void setSourceId(int sourceId) {
        this.sourceId = sourceId;
    }

    public int getSpamCounter() {
        return spamCounter;
    }

    public void setSpamCounter(int spamCounter) {
        this.spamCounter = spamCounter;
    }
}
