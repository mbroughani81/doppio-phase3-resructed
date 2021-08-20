package shared.request;

import shared.model.SingleFollowNotification;
import shared.response.Response;

public class NewAcceptRequest extends Request {

    private SingleFollowNotification singleFollowNotification;

    @Override
    public Response handle(RequestHandler requestHandler) {
        return requestHandler.newAccpetRequest(this);
    }

    public NewAcceptRequest(SingleFollowNotification singleFollowNotification) {
        this.singleFollowNotification = singleFollowNotification;
    }

    public SingleFollowNotification getSingleFollowNotification() {
        return singleFollowNotification;
    }

    public void setSingleFollowNotification(SingleFollowNotification singleFollowNotification) {
        this.singleFollowNotification = singleFollowNotification;
    }
}
