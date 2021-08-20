package server.model;

public class FollowRequestNotification {
    private int id;
    private String followerUsername;
    private String followedUsername;

    public FollowRequestNotification(String followerUsername, String followedUsername) {
        this.id = -1;

        this.followerUsername = followerUsername;
        this.followedUsername = followedUsername;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFollowerUsername() {
        return followerUsername;
    }

    public void setFollowerUsername(String followerUsername) {
        this.followerUsername = followerUsername;
    }

    public String getFollowedUsername() {
        return followedUsername;
    }

    public void setFollowedUsername(String followedUsername) {
        this.followedUsername = followedUsername;
    }
}
