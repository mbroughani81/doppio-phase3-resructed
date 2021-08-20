package server.model;

public class User {
    private int id;

    private String username;
    private String password;
    private boolean isActive;
    private boolean isAlive;
    private int profileId;
    private int blockListId;
    private int followersListId;
    private int followingListId;
    private int messageDataId;
    private int notificationBoxId;
    private int likedTweetListId;
    private int retweetedTweetListId;
    private int reportedTweetListId;
    private int mutedUserListId;

    public User(String username, String password, int profileId, int blockListId, int followerListId,
                int followingListId, int messageDataId, int notificationBoxId, int likedTweetListId,
                int retweetedTweetListId, int reportedTweetListId, int mutedUserListId) {
        this.id = -1;
        this.username = username;
        this.password = password;
        this.profileId = profileId;
        this.isActive = true;
        this.isAlive = true;
        this.blockListId = blockListId;
        this.followersListId = followerListId;
        this.followingListId = followingListId;
        this.messageDataId = messageDataId;
        this.notificationBoxId = notificationBoxId;
        this.likedTweetListId = likedTweetListId;
        this.retweetedTweetListId = retweetedTweetListId;
        this.reportedTweetListId = reportedTweetListId;
        this.mutedUserListId = mutedUserListId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getProfileId() {
        return profileId;
    }

    public void setProfileId(int profileId) {
        this.profileId = profileId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public boolean isAlive() {
        return isAlive;
    }

    public void setAlive(boolean alive) {
        isAlive = alive;
    }

    public int getBlockListId() {
        return blockListId;
    }

    public void setBlockListId(int blockListId) {
        this.blockListId = blockListId;
    }

    public int getFollowersListId() {
        return followersListId;
    }

    public void setFollowersListId(int followersListId) {
        this.followersListId = followersListId;
    }

    public int getFollowingListId() {
        return followingListId;
    }

    public void setFollowingListId(int followingListId) {
        this.followingListId = followingListId;
    }

    public int getMessageDataId() {
        return messageDataId;
    }

    public void setMessageDataId(int messageDataId) {
        this.messageDataId = messageDataId;
    }

    public int getNotificationBoxId() {
        return notificationBoxId;
    }

    public void setNotificationBoxId(int notificationBoxId) {
        this.notificationBoxId = notificationBoxId;
    }

    public int getLikedTweetListId() {
        return likedTweetListId;
    }

    public void setLikedTweetListId(int likedTweetListId) {
        this.likedTweetListId = likedTweetListId;
    }

    public int getRetweetedTweetListId() {
        return retweetedTweetListId;
    }

    public void setRetweetedTweetListId(int retweetedTweetListId) {
        this.retweetedTweetListId = retweetedTweetListId;
    }

    public int getReportedTweetListId() {
        return reportedTweetListId;
    }

    public void setReportedTweetListId(int reportedTweetListId) {
        this.reportedTweetListId = reportedTweetListId;
    }

    public int getMutedUserListId() {
        return mutedUserListId;
    }

    public void setMutedUserListId(int mutedUserListId) {
        this.mutedUserListId = mutedUserListId;
    }

    @Override
    public String toString() {
        return "id = " + id + ", username = " + username;
    }
}
