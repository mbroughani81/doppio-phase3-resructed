package shared.request;

import shared.response.Response;

public class ChangeEmailRequest extends Request {

    private String email;

    @Override
    public Response handle(RequestHandler requestHandler) {
        return requestHandler.changeEmail(this);
    }

    public ChangeEmailRequest(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
