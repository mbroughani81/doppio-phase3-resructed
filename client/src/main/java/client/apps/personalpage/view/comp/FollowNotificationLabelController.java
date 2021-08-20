package client.apps.personalpage.view.comp;

import client.datatype.BasicController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import shared.model.SingleFollowNotification;
import shared.request.NewAcceptRequest;
import shared.request.NewDeclineRequest;
import shared.request.NewSilentDeclineRequest;

import java.net.URL;
import java.util.ResourceBundle;

public class FollowNotificationLabelController extends BasicController implements Initializable {

    private SingleFollowNotification singleFollowNotification;

    @FXML
    private Label notificationText;

    @FXML
    private HBox buttonHolder;

    @FXML
    private Button yesButton;

    @FXML
    private Button noButton;

    @FXML
    private Button noSilentButton;

    @FXML
    void noButtonClicked(ActionEvent event) {
        getListener().listen(new NewDeclineRequest(singleFollowNotification));
    }

    @FXML
    void noSilentButtonClicked(ActionEvent event) {
        getListener().listen(new NewSilentDeclineRequest(singleFollowNotification));
    }

    @FXML
    void yesButtonClicked(ActionEvent event) {
        getListener().listen(new NewAcceptRequest(singleFollowNotification));
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        notificationText.setText(singleFollowNotification.getFollowerUsername() + " requested " +
                singleFollowNotification.getFollowedUsername());

        if (singleFollowNotification.isCanResponse())
            buttonHolder.setVisible(true);
        else
            buttonHolder.setVisible(false);
    }

    public FollowNotificationLabelController(SingleFollowNotification singleFollowNotification) {
        this.singleFollowNotification = singleFollowNotification;
    }

    public SingleFollowNotification getSingleFollowNotification() {
        return singleFollowNotification;
    }

    public void setSingleFollowNotification(SingleFollowNotification singleFollowNotification) {
        this.singleFollowNotification = singleFollowNotification;
    }
}
