package server.model;

import java.util.LinkedList;

public class NotificationBox {
    private int id;
    private LinkedList<Integer> followRequestIds;

    public NotificationBox() {
        this.id = -1;
        this.followRequestIds = new LinkedList<>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LinkedList<Integer> getFollowRequestIds() {
        return followRequestIds;
    }

    public void setFollowRequestIds(LinkedList<Integer> followRequestIds) {
        this.followRequestIds = followRequestIds;
    }
}
