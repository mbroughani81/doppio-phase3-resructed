package client.apps.messenger.view;

import client.apps.mainpage.view.MainPageController;
import client.datatype.BasicController;
import client.dbcontroller.MessageDataModelController;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.layout.VBox;
import shared.loop.Loop;
import shared.request.GetMessageDataModelRequest;
import shared.response.OpenNewGroupAlert;

import java.net.URL;
import java.util.ResourceBundle;

public class MessengerController extends MainPageController {

    MessengerRootController messengerRootController;

    public void setMessengerRoot(Parent root, MessengerRootController messengerRootController) {
        setCenter(root);
        this.messengerRootController = messengerRootController;
        this.messengerRootController.setListener(getListener());
    }

    public void openNewGroupAlert(OpenNewGroupAlert openNewGroupAlert) {
        Platform.runLater(() -> messengerRootController.openNewGroupAlert(openNewGroupAlert.getAvailableUsers()));
    }

    @Override
    public Runnable getUpdateAction() {
        return () -> {
            Platform.runLater(
                    messengerRootController.getUpdateAction()
            );
        };
    }

    @Override
    public Runnable getRequestAction() {
        return () -> {
            getListener().listen(new GetMessageDataModelRequest());
        };
    }
}
