package shared.request;

import shared.response.Response;

public class ChangeBioRequest extends Request {

    private String bio;

    @Override
    public Response handle(RequestHandler requestHandler) {
        return requestHandler.changeBio(this);
    }

    public ChangeBioRequest(String bio) {
        this.bio = bio;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }
}
