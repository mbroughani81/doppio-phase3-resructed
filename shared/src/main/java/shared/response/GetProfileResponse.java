package shared.response;

public class GetProfileResponse implements Response {

    private int userId;
    private String username;
    private String name;
    private String lastseen;
    private String bio;

    @Override
    public void handle(ResponseHandler responseHandler) {
        responseHandler.updateProfile(this);
    }

    public GetProfileResponse(int userId, String username, String name, String lastseen, String bio) {
        this.userId = userId;
        this.username = username;
        this.name = name;
        this.lastseen = lastseen;
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

    public String getLastseen() {
        return lastseen;
    }

    public void setLastseen(String lastseen) {
        this.lastseen = lastseen;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }
}
