package shared.response;

import shared.model.AuthToken;

import java.util.LinkedList;

public class LoginResponse implements Response {

    private String username;
    private AuthToken authToken;
    private LinkedList<String> errors;

    @Override
    public void handle(ResponseHandler responseHandler) {
        responseHandler.checkLoginResponse(this);
    }

    public LoginResponse(String username, AuthToken authToken, LinkedList<String> errors) {
        this.username = username;
        this.authToken = authToken;
        this.errors = errors;
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

    public LinkedList<String> getErrors() {
        return errors;
    }

    public void setErrors(LinkedList<String> errors) {
        this.errors = errors;
    }
}
