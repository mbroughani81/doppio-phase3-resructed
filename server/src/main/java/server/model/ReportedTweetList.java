package server.model;

import java.util.LinkedList;

public class ReportedTweetList {
    private int id;
    private LinkedList<Integer> tweetIds;

    public ReportedTweetList() {
        this.id = -1;

        this.tweetIds = new LinkedList<>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LinkedList<Integer> getTweetIds() {
        return tweetIds;
    }

    public void setTweetIds(LinkedList<Integer> tweetIds) {
        this.tweetIds = tweetIds;
    }
}
