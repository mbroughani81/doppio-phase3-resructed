package server.model;

import java.util.LinkedList;

public class UserType {
    int id;

    String userTypeName;
    int ownerId;
    LinkedList<Integer> userIds;

    public UserType(String userTypeName, int ownerId) {
        this.id = -1;
        this.userTypeName = userTypeName;
        this.ownerId = ownerId;
        this.userIds = new LinkedList<>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserTypeName() {
        return userTypeName;
    }

    public void setUserTypeName(String userTypeName) {
        this.userTypeName = userTypeName;
    }

    public int getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(int ownerId) {
        this.ownerId = ownerId;
    }

    public LinkedList<Integer> getUserIds() {
        return userIds;
    }

    public void setUserIds(LinkedList<Integer> userIds) {
        this.userIds = userIds;
    }
}
