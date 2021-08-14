package server.model;

import shared.model.AuthToken;

public class Session {
    private int id;

    private int userId;
    private AuthToken authToken;

    public Session(int userId, AuthToken authToken) {
        this.id = -1;
        this.userId = userId;
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

    public AuthToken getAuthToken() {
        return authToken;
    }

    public void setAuthToken(AuthToken authToken) {
        this.authToken = authToken;
    }
}
