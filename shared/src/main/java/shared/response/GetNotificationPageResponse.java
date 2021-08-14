package shared.response;

import shared.model.SingleFollowNotification;
import shared.model.SingleSystemNotification;

import java.util.LinkedList;

public class GetNotificationPageResponse implements Response {

    private LinkedList<SingleFollowNotification> followNotifications;
    private LinkedList<SingleSystemNotification> systemNotifications;

    @Override
    public void handle(ResponseHandler responseHandler) {
        responseHandler.updateNotificationPage(this);
    }

    public GetNotificationPageResponse(LinkedList<SingleFollowNotification> followNotifications, LinkedList<SingleSystemNotification> systemNotifications) {
        this.followNotifications = followNotifications;
        this.systemNotifications = systemNotifications;
    }

    public LinkedList<SingleFollowNotification> getFollowNotifications() {
        return followNotifications;
    }

    public void setFollowNotifications(LinkedList<SingleFollowNotification> followNotifications) {
        this.followNotifications = followNotifications;
    }

    public LinkedList<SingleSystemNotification> getSystemNotifications() {
        return systemNotifications;
    }

    public void setSystemNotifications(LinkedList<SingleSystemNotification> systemNotifications) {
        this.systemNotifications = systemNotifications;
    }
}
