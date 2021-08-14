package client.apps.personalpage.view;

import client.apps.mainpage.view.MainPageController;
import client.datatype.BasicController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.ListView;

import java.net.URL;
import java.util.ResourceBundle;

public class PersonalPageController extends MainPageController {

    PersonalPageRootController personalPageRootController;

    public void setPersonalPageRoot(Parent root, PersonalPageRootController personalPageRootController) {
        setCenter(root);
        this.personalPageRootController = personalPageRootController;
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
