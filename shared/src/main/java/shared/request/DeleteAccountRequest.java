package shared.request;

import shared.response.Response;

public class DeleteAccountRequest extends Request {

    private int userId;

    @Override
    public Response handle(RequestHandler requestHandler) {
        return requestHandler.deleteUser(this);
    }

    public DeleteAccountRequest(int userId) {
        this.userId = userId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
