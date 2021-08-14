package client.apps.personalpage.view;

import client.apps.mainpage.view.MainPageController;
import javafx.scene.Parent;
import shared.request.GetNotificationPageRequest;

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

    @Override
    public Runnable getRequestAction() {
        return () -> getListener().listen(new GetNotificationPageRequest());
    }
}
