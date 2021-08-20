package client.apps.personalpage.view.comp;

import client.datatype.BasicController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import shared.model.SingleSystemNotification;

import java.net.URL;
import java.util.ResourceBundle;

public class SystemNotificationLabelController extends BasicController implements Initializable {

    private SingleSystemNotification notification;

    @FXML
    private Label labelText;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        labelText.setText(notification.getText());
    }

    public SystemNotificationLabelController(SingleSystemNotification notification) {
        this.notification = notification;
    }
}
