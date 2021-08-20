package shared.request;

import shared.response.Response;

public class ChangeActivityRequest extends Request {

    private boolean activity;

    @Override
    public Response handle(RequestHandler requestHandler) {
        return requestHandler.changeActivity(this);
    }

    public ChangeActivityRequest(boolean activity) {
        this.activity = activity;
    }

    public boolean isActivity() {
        return activity;
    }

    public void setActivity(boolean activity) {
        this.activity = activity;
    }
}
