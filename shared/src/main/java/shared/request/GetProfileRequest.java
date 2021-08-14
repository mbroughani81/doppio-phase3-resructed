package shared.request;

import shared.response.Response;

public class GetProfileRequest extends Request {

    private int userId;

    @Override
    public Response handle(RequestHandler requestHandler) {
        return requestHandler.getProfile(this);
    }

    public GetProfileRequest(int userId) {
        this.userId = userId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
