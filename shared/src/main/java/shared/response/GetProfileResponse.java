package shared.response;

import shared.datatype.LastSeenPrivacy;

public class GetProfileResponse implements Response {

    private int userId;
    private String username;
    private String name;
    private LastSeenPrivacy lastSeenPrivacy;
    private String bio;

    @Override
    public void handle(ResponseHandler responseHandler) {
        responseHandler.updateProfile(this);
    }

    public GetProfileResponse(int userId, String username, String name, LastSeenPrivacy lastSeenPrivacy, String bio) {
        this.userId = userId;
        this.username = username;
        this.name = name;
        this.lastSeenPrivacy = lastSeenPrivacy;
        this.bio = bio;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LastSeenPrivacy getLastSeenPrivacy() {
        return lastSeenPrivacy;
    }

    public void setLastSeenPrivacy(LastSeenPrivacy lastSeenPrivacy) {
        this.lastSeenPrivacy = lastSeenPrivacy;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }
}
