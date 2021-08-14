package shared.request;

import shared.response.Response;

public class LoginRequest extends Request {

    private String username;
    private String password;

    @Override
    public Response handle(RequestHandler requestHandler) {
        return requestHandler.loginUser(this);
    }

    public LoginRequest(String username, String password) {
        this.username = username;
        this.password = password;
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
}
