package client.apps.personalpage.view;

import client.apps.mainpage.view.MainPageController;
import javafx.scene.Parent;

public class EditProfilePageController extends MainPageController {

    EditProfilePageRootController editProfilePageRootController;

    public void setEditProfilePageRoot(Parent root, EditProfilePageRootController editProfilePageRootController) {
        setCenter(root);
        this.editProfilePageRootController = editProfilePageRootController;
        this.editProfilePageRootController.setListener(getListener());
    }
}
