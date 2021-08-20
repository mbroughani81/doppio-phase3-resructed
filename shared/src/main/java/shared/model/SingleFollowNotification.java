package shared.model;

public class SingleFollowNotification {
    private int followRequestId;
    private String followerUsername;
    private String followedUsername;
    private boolean canResponse;

    public SingleFollowNotification(int followRequestId, String followerUsername, String followedUsername, boolean canResponse) {
        this.followRequestId = followRequestId;
        this.followerUsername = followerUsername;
        this.followedUsername = followedUsername;
        this.canResponse = canResponse;
    }

    public int getFollowRequestId() {
        return followRequestId;
    }

    public void setFollowRequestId(int followRequestId) {
        this.followRequestId = followRequestId;
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

    public boolean isCanResponse() {
        return canResponse;
    }

    public void setCanResponse(boolean canResponse) {
        this.canResponse = canResponse;
    }
}
