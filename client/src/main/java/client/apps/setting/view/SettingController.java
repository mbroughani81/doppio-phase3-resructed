package client.apps.setting.view;

import client.apps.mainpage.view.MainPageController;
import client.datatype.BasicController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.ListView;

import java.net.URL;
import java.util.ResourceBundle;

public class SettingController extends MainPageController {

    SettingRootController settingRootController;

    public void setSettingRoot(Parent root, SettingRootController settingRootController) {
        setCenter(root);
        this.settingRootController = settingRootController;
    }

    @Override
    public Runnable getUpdateAction() {
        return super.getUpdateAction();
    }

    @Override
    public Runnable getRequestAction() {
        return super.getRequestAction();
    }
}
