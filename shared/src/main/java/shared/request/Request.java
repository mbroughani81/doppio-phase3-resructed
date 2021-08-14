package shared.request;

import shared.model.AuthToken;
import shared.response.Response;

abstract public class Request {
    private AuthToken authToken;

    abstract public Response handle(RequestHandler requestHandler);

    public AuthToken getAuthToken() {
        return authToken;
    }

    public void setAuthToken(AuthToken authToken) {
        this.authToken = authToken;
    }
}
