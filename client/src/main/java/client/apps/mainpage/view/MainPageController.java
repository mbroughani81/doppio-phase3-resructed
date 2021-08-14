package client.apps.mainpage.view;

import client.core.DoppioApp;
import client.core.ViewSwitcher;
import client.datatype.BasicController;
import client.datatype.Page;
import client.datatype.View;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;

public class MainPageController extends BasicController {

    @FXML
    private BorderPane mainPane;

    @FXML
    private Button mainPageButton;

    @FXML
    private Circle connectionVerdict;

    @FXML
    private Button reconnectButton;

    @FXML
    private Button personalPageButton;

    @FXML
    private Button timelineButton;

    @FXML
    private Button explorerButton;

    @FXML
    private Button messengerButton;

    @FXML
    private Button settingButton;

    @FXML
    void explorerButtonClicked(ActionEvent event) {
        ViewSwitcher.getInstance().switchTo(new Page(View.EXPLORER, -1));
    }

    @FXML
    void mainPageButtonClicked(ActionEvent event) {
        ViewSwitcher.getInstance().switchTo(new Page(View.MAINPAGE, -1));
    }

    @FXML
    void messengerButtonClicked(ActionEvent event) {
        ViewSwitcher.getInstance().switchTo(new Page(View.MESSENGER, -1));
    }

    @FXML
    void personalPageButtonClicked(ActionEvent event) {
        ViewSwitcher.getInstance().switchTo(new Page(View.PERSONALPAGE, -1));
    }

    @FXML
    void reconnectButtonClicked(ActionEvent event) {
        DoppioApp.askNewSocket();
    }

    @FXML
    void settingButtonClicked(ActionEvent event) {
        ViewSwitcher.getInstance().switchTo(new Page(View.SETTING, -1));
    }

    @FXML
    void timelineButtonClicked(ActionEvent event) {
        ViewSwitcher.getInstance().switchTo(new Page(View.TIMELINE, -1));
    }

    public void setCenter(Parent root) {
        mainPane.setCenter(root);
    }

    public void showDisconnectionMessage() {
        connectionVerdict.setFill(Color.rgb(237, 0, 4));
        reconnectButton.setVisible(true);
    }

    public void showConnectionMessage() {
        connectionVerdict.setFill(Color.rgb(24, 237, 0));
        reconnectButton.setVisible(false);
    }
}
