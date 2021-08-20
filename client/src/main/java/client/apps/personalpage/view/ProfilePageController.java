package client.apps.personalpage.view;

import client.apps.mainpage.view.MainPageController;
import client.core.DoppioApp;
import javafx.application.Platform;
import javafx.scene.Parent;
import shared.response.GetProfileResponse;

public class ProfilePageController extends MainPageController {

    ProfilePageRootController profilePageRootController;
    int userId;

    public ProfilePageController(int userId) {
        this.userId = userId;
    }

    public void setProfilePageRoot(Parent root, ProfilePageRootController profilePageRootController) {
        setCenter(root);
        this.profilePageRootController = profilePageRootController;
        this.addToChildControllers(profilePageRootController);
        this.profilePageRootController.setUserId(userId);
        this.profilePageRootController.init();
    }

    @Override
    public Runnable getRequestAction() {
        return () -> {
            runChildControllerRequest();
        };
    }

    public void updateProfile(GetProfileResponse getProfileResponse) {
        if (userId != getProfileResponse.getUserId() &&
        !DoppioApp.getSessionModelController().getSession().getUsername().equals(getProfileResponse.getUsername()))
            return;
        Platform.runLater(() -> profilePageRootController.updateProfile(getProfileResponse));
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
