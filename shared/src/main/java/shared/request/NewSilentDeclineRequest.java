package shared.request;

import shared.model.SingleFollowNotification;
import shared.response.Response;

public class NewSilentDeclineRequest extends Request {

    private SingleFollowNotification singleFollowNotification;

    @Override
    public Response handle(RequestHandler requestHandler) {
        return requestHandler.newSilentDeclineRequest(this);
    }

    public NewSilentDeclineRequest(SingleFollowNotification singleFollowNotification) {
        this.singleFollowNotification = singleFollowNotification;
    }

    public SingleFollowNotification getSingleFollowNotification() {
        return singleFollowNotification;
    }

    public void setSingleFollowNotification(SingleFollowNotification singleFollowNotification) {
        this.singleFollowNotification = singleFollowNotification;
    }
}
