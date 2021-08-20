package client.apps.personalpage.view;

import client.apps.personalpage.view.comp.FollowNotificationLabelController;
import client.apps.personalpage.view.comp.SystemNotificationLabelController;
import client.datatype.BasicController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.VBox;
import shared.model.SingleFollowNotification;
import shared.model.SingleSystemNotification;

import java.io.IOException;
import java.util.LinkedList;

public class NotificationPageRootController extends BasicController {

    @FXML
    private VBox follownotificationHolder;

    @FXML
    private VBox systemnotificationHolder;

    public void updatePage(LinkedList<SingleFollowNotification> followNotifications,
                           LinkedList<SingleSystemNotification> systemNotifications) {

        follownotificationHolder.getChildren().clear();
        systemnotificationHolder.getChildren().clear();
//        System.out.println(followNotifications.size() + " is size");
        for(SingleFollowNotification followNotification : followNotifications) {
            FollowNotificationLabelController controller = new FollowNotificationLabelController(followNotification);
            this.addToChildControllers(controller);
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(FollowNotificationLabelController.class.getResource("follownotificationlabel.fxml"));
            fxmlLoader.setController(controller);
            try {
                follownotificationHolder.getChildren().add(fxmlLoader.load());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        for(SingleSystemNotification systemNotification : systemNotifications) {
            SystemNotificationLabelController controller = new SystemNotificationLabelController(systemNotification);
            this.addToChildControllers(controller);
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(FollowNotificationLabelController.class.getResource("systemnotificationlabel.fxml"));
            fxmlLoader.setController(controller);
            try {
                systemnotificationHolder.getChildren().add(fxmlLoader.load());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
