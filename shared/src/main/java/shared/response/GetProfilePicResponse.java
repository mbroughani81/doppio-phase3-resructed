package shared.response;

public class GetProfilePicResponse implements Response {

    private int userId;
    private String imageString;

    @Override
    public void handle(ResponseHandler responseHandler) {
        responseHandler.updateProfilePic(this);
    }

    public GetProfilePicResponse(int userId, String imageString) {
        this.userId = userId;
        this.imageString = imageString;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getImageString() {
        return imageString;
    }

    public void setImageString(String imageString) {
        this.imageString = imageString;
    }
}
