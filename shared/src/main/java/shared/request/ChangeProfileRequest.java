package shared.request;

import shared.response.Response;

public class ChangeProfileRequest extends Request {

    private String imageString;

    @Override
    public Response handle(RequestHandler requestHandler) {
        return requestHandler.changeProfile(this);
    }

    public ChangeProfileRequest(String imageString) {
        this.imageString = imageString;
    }

    public String getImageString() {
        return imageString;
    }

    public void setImageString(String imageString) {
        this.imageString = imageString;
    }
}
