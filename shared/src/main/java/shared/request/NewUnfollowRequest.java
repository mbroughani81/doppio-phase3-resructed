package shared.request;

import shared.response.Response;

public class NewUnfollowRequest extends Request {

    int userId;

    @Override
    public Response handle(RequestHandler requestHandler) {
        return requestHandler.newUnfollow(this);
    }

    public NewUnfollowRequest(int userId) {
        this.userId = userId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
