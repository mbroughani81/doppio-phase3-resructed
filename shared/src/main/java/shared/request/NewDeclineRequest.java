package shared.request;

import shared.model.SingleFollowNotification;
import shared.response.Response;

public class NewDeclineRequest extends Request {

    private SingleFollowNotification singleFollowNotification;

    @Override
    public Response handle(RequestHandler requestHandler) {
        return requestHandler.newDeclineRequest(this);
    }

    public NewDeclineRequest(SingleFollowNotification singleFollowNotification) {
        this.singleFollowNotification = singleFollowNotification;
    }

    public SingleFollowNotification getSingleFollowNotification() {
        return singleFollowNotification;
    }

    public void setSingleFollowNotification(SingleFollowNotification singleFollowNotification) {
        this.singleFollowNotification = singleFollowNotification;
    }
}
