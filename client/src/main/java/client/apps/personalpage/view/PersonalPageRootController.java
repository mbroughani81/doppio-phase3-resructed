package client.apps.personalpage.view;

import client.core.DoppioApp;
import client.core.ViewSwitcher;
import client.datatype.BasicController;
import client.datatype.Page;
import client.datatype.View;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import shared.request.ExplorerSearchIdRequest;

public class PersonalPageRootController extends BasicController {

    @FXML
    private Button newTweetButton;

    @FXML
    private Button showTweetButton;

    @FXML
    private Button editprofileButton;

    @FXML
    private Button showlistsButton;

    @FXML
    private Button infoButton;

    @FXML
    private Button notificationsButton;

    @FXML
    void editprofileButtonClicked(ActionEvent event) {
        ViewSwitcher.getInstance().switchTo(new Page(View.EDITPROFILEPAGE, -1));
    }

    @FXML
    void infoButtonClicked(ActionEvent event) {
//        ViewSwitcher.getInstance().switchTo(new Page(View.PROFILEPAGE,
//                DoppioApp.getSessionModelController()));
        getListener().listen(new ExplorerSearchIdRequest(-1));
    }

    @FXML
    void newTweetButtonClicked(ActionEvent event) {
        ViewSwitcher.getInstance().switchTo(new Page(View.NEWTWEET, -1));
    }

    @FXML
    void notificationsButtonClicked(ActionEvent event) {
        ViewSwitcher.getInstance().switchTo(new Page(View.NOTIFICATIONPAGE, -1));
    }

    @FXML
    void showTweetButtonClicked(ActionEvent event) {
        ViewSwitcher.getInstance().switchTo(new Page(View.SHOWTWEETS, -1));
    }

    @FXML
    void showlistsButtonClicked(ActionEvent event) {
        ViewSwitcher.getInstance().switchTo(new Page(View.SHOWLIST, -1));
    }

}
