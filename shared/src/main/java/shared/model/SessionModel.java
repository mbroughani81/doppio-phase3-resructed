package shared.model;

public class SessionModel {
    private int id;

    private String username;
    private AuthToken authToken;

    public SessionModel(String username, AuthToken authToken) {
        this.id = -1;
        this.username = username;
        this.authToken = authToken;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
