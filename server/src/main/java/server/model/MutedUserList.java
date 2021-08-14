package server.model;

import java.util.LinkedList;

public class MutedUserList {
    private int id;
    private LinkedList<Integer> userIds;

    public MutedUserList() {
        this.id = -1;
        userIds = new LinkedList<>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LinkedList<Integer> getUserIds() {
        return userIds;
    }

    public void setUserIds(LinkedList<Integer> userIds) {
        this.userIds = userIds;
    }
}
