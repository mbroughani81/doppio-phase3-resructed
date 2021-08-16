package client.apps.mainpage.view;

import client.config.apps.mainpage.MainPageConfig;
import client.core.DoppioApp;
import client.core.ViewSwitcher;
import client.datatype.BasicController;
import client.datatype.Page;
import client.datatype.View;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;

import java.net.URL;
import java.util.ResourceBundle;

public class MainPageController extends BasicController implements Initializable {

    @FXML
    private BorderPane mainPane;

    @FXML
    private Button backButton;

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
    void backButtonClicked(ActionEvent event) {

    }

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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        MainPageConfig mainPageConfig = new MainPageConfig();
        backButton.setText(mainPageConfig.getBackButtonText());
        mainPageButton.setText(mainPageConfig.getMainpageButtonText());
        personalPageButton.setText(mainPageConfig.getPersonalpageButtonText());
        timelineButton.setText(mainPageConfig.getTimelineButtonText());
        messengerButton.setText(mainPageConfig.getMessengerButtonText());
        settingButton.setText(mainPageConfig.getSettingButtonText());
    }

    public void setCenter(Parent root) {
        mainPane.setCenter(root);
    }

    public void showDisconnectionMessage() {
        MainPageConfig mainPageConfig = new MainPageConfig();
        connectionVerdict.setFill(Paint.valueOf(mainPageConfig.getConnectionVerdictDisconnectedColor()));
        reconnectButton.setVisible(true);
    }

    public void showConnectionMessage() {
        MainPageConfig mainPageConfig = new MainPageConfig();
        connectionVerdict.setFill(Paint.valueOf(mainPageConfig.getConnectionVerdictConnectedColor()));
        reconnectButton.setVisible(false);
    }
}
