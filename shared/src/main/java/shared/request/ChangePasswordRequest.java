package shared.request;

import shared.response.Response;

public class ChangePasswordRequest extends Request {

    private String password;

    @Override
    public Response handle(RequestHandler requestHandler) {
        return null;
    }

    public ChangePasswordRequest(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
