package shared.response;

public class CheckProfile implements Response {

    private int userId;

    @Override
    public void handle(ResponseHandler responseHandler) {
        responseHandler.checkProfile(this);
    }

    public CheckProfile(int userId) {
        this.userId = userId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
