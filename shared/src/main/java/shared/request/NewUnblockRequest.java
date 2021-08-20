package shared.request;

import shared.response.Response;

public class NewUnblockRequest extends Request {

    private int userId;

    @Override
    public Response handle(RequestHandler requestHandler) {
        return requestHandler.newUnblock(this);
    }

    public NewUnblockRequest(int userId) {
        this.userId = userId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
