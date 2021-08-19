package shared.model;

public class SessionModel {
    private int id;

    private int userId;
    private String username;
    private AuthToken authToken;

    public SessionModel(int userId, String username, AuthToken authToken) {
        this.id = -1;
        this.userId = userId;
        this.username = username;
        this.authToken = authToken;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public AuthToken getAuthToken() {
        return authToken;
    }

    public void setAuthToken(AuthToken authToken) {
        this.authToken = authToken;
    }
}
