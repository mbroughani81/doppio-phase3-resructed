package client.apps.personalpage.view;

import client.apps.mainpage.view.MainPageController;
import javafx.application.Platform;
import javafx.scene.Parent;
import shared.request.GetNotificationPageRequest;
import shared.response.GetNotificationPageResponse;

public class NotificationPageController extends MainPageController {

    NotificationPageRootController notificationPageRootController;

    public void setNotificationPageRoot(
            Parent root,
            NotificationPageRootController notificationPageRootController
    ) {
        setCenter(root);
        this.notificationPageRootController = notificationPageRootController;
        this.notificationPageRootController.setListener(getListener());
    }

    public void updatePage(GetNotificationPageResponse getNotificationPageResponse) {
        Platform.runLater( () ->
                notificationPageRootController.updatePage(
                        getNotificationPageResponse.getFollowNotifications(),
                        getNotificationPageResponse.getSystemNotifications()
                )
        );
    }

    @Override
    public Runnable getRequestAction() {
        return () -> getListener().listen(new GetNotificationPageRequest());
    }
}
