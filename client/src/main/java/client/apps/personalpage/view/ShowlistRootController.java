package client.apps.personalpage.view;

import client.apps.chat.view.comp.SinglePmLabelController;
import client.apps.personalpage.view.comp.SingleUserLabelController;
import client.datatype.BasicController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.VBox;
import shared.model.SinglePm;
import shared.model.SingleUser;

import java.io.IOException;
import java.util.LinkedList;

public class ShowlistRootController extends BasicController {

    @FXML
    private VBox followingHolder;

    @FXML
    private VBox followerHolder;

    @FXML
    private VBox blacklistHolder;

    public void updateList(LinkedList<SingleUser> followings,
                           LinkedList<SingleUser> followers,
                           LinkedList<SingleUser> blacklist) {
        followingHolder.getChildren().clear();
        followerHolder.getChildren().clear();
        blacklistHolder.getChildren().clear();
        for (SingleUser user : followings) {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(SingleUserLabelController.class.getResource("singleuserlabel.fxml"));
            SingleUserLabelController singleUserLabelController = new SingleUserLabelController(
                    user.getUserId(),
                    "following"
            );
            singleUserLabelController.setListener(getListener());
            fxmlLoader.setController(singleUserLabelController);
            try {
                followingHolder.getChildren().add(fxmlLoader.load());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        for (SingleUser user : followers) {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(SingleUserLabelController.class.getResource("singleuserlabel.fxml"));
            SingleUserLabelController singleUserLabelController = new SingleUserLabelController(
                    user.getUserId(),
                    "follower"
            );
            singleUserLabelController.setListener(getListener());
            fxmlLoader.setController(singleUserLabelController);
            try {
                followerHolder.getChildren().add(fxmlLoader.load());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        for (SingleUser user : blacklist) {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(SingleUserLabelController.class.getResource("singleuserlabel.fxml"));
            SingleUserLabelController singleUserLabelController = new SingleUserLabelController(
                    user.getUserId(),
                    "blacklist"
            );
            singleUserLabelController.setListener(getListener());
            fxmlLoader.setController(singleUserLabelController);
            try {
                blacklistHolder.getChildren().add(fxmlLoader.load());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
