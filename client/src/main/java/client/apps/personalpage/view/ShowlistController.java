package client.apps.personalpage.view;

import client.apps.mainpage.view.MainPageController;
import javafx.application.Platform;
import javafx.scene.Parent;
import shared.request.GetShowlistRequest;
import shared.response.GetShowlistResponse;

public class ShowlistController extends MainPageController {

    ShowlistRootController showlistRootController;

    public void setShowlistRoot(Parent root, ShowlistRootController showlistRootController) {
        setCenter(root);
        this.showlistRootController = showlistRootController;
        this.showlistRootController.setListener(getListener());
    }

    public void updateShowlistPage(GetShowlistResponse getShowlistResponse) {
        Platform.runLater(() -> showlistRootController.updateList(
                getShowlistResponse.getFollowings(),
                getShowlistResponse.getFollowers(),
                getShowlistResponse.getBlacklist()
        ));
    }

    @Override
    public Runnable getRequestAction() {
        return () -> getListener().listen(new GetShowlistRequest());
    }
}
